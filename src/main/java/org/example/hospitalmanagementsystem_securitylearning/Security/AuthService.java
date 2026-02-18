package org.example.hospitalmanagementsystem_securitylearning.Security;

import lombok.RequiredArgsConstructor;

import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginRequestDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.SignUpResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Entity.User;
import org.example.hospitalmanagementsystem_securitylearning.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public SignUpResponseDto signup(LoginRequestDto signUpRequestDto) throws Exception{
        User user=userRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);
        if(user!=null){
            throw new IllegalAccessException("user already exist");
        }

        user = userRepository.save(User.builder()
                        .username(signUpRequestDto.getUsername())
                        .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                        .build());
        return new SignUpResponseDto(user.getId(), user.getUsername());
    }
}
