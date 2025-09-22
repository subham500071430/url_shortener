package com.app.user.controller;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.dto.SignupRequest;
import com.app.user.dto.SignupResponse;
import com.app.user.entity.RefreshToken;
import com.app.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    AuthService authService;

    @Autowired
    UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = authService.loginUser(loginRequest);

        return response == null ?
                ResponseEntity.status(401).body(new LoginResponse(null, null, 0)) :
                ResponseEntity.ok(response);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {

        SignupResponse response = authService.signupUser(signupRequest);

        return response == null ?
                ResponseEntity.status(401).body(new LoginResponse(null, null, 0)) :
                ResponseEntity.ok(response);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshToken refreshToken) {

        boolean isValid = authService.validateRefreshToken(refreshToken.getToken(), refreshToken.getEmail());

        if (!isValid) return ResponseEntity.status(401).body(null);

        return ResponseEntity.ok().body(authService.generateAccessToken(refreshToken.getEmail(), "user"));
    }

    // to-do
    // post/logout
}
