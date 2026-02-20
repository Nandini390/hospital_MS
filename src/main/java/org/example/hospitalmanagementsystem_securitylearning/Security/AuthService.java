package org.example.hospitalmanagementsystem_securitylearning.Security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginRequestDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.SignUpResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Entity.Type.AuthProviderType;
import org.example.hospitalmanagementsystem_securitylearning.Entity.User;
import org.example.hospitalmanagementsystem_securitylearning.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws Exception{
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
        User user = (User)authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(LoginRequestDto signUpRequestDto, AuthProviderType authProviderType, String providerId) throws Exception{
        User user=userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);
        if(user!=null) throw new IllegalAccessException("user already exist");

        user = User.builder()
                .username(signUpRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .build();
        if(authProviderType==AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
        }
        return userRepository.save(user);
    }

    //login controller
    public SignUpResponseDto signup(LoginRequestDto signUpRequestDto) throws Exception{
        User user = signUpInternal(signUpRequestDto,AuthProviderType.EMAIL,null);
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }

    @Transactional
    public ResponseEntity<LoginResponseDto> handleOAuthLoginRequest(OAuth2User oAuth2User,String registrationId) throws Exception {
        //fetch the providerType and providerId
        //save the providerType and providerId info with user:- so that it won't create account again with different providerType
        //if the user have an account: directly login
        //otherwise, first signUp and then Login

        AuthProviderType providerType=authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);

        User user = userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User emailUser = userRepository.findByUsername(email).orElse(null);

        if(user==null && emailUser==null){
            //signUp flow:
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            user = signUpInternal(new LoginRequestDto(username,null),providerType,providerId);
        }else if(user != null){
            if(email!=null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("This email is already registered with provider: "+emailUser.getProviderType());
        }
        LoginResponseDto loginResponseDto= new LoginResponseDto(authUtil.generateAccessToken(user),user.getId());
        return ResponseEntity.ok(loginResponseDto);
    }
}
