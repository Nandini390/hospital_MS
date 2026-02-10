package org.example.hospitalmanagementsystem_securitylearning.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginRequestDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.LoginResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Dto.SignUpResponseDto;
import org.example.hospitalmanagementsystem_securitylearning.Security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody LoginRequestDto loginRequestDto) throws Exception{
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup (@RequestBody LoginRequestDto signupRequestDto) throws Exception{
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

}