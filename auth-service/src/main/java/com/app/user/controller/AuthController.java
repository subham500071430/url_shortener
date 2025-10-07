package com.app.user.controller;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.dto.SignupRequest;
import com.app.user.dto.SignupResponse;
import com.app.user.entity.PublicKeyStore;
import com.app.user.entity.RefreshToken;
import com.app.user.repository.PublicKeyRepository;
import com.app.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthService authService;
    private final PublicKeyRepository repository;

    @Autowired
    public AuthController(AuthService authService, PublicKeyRepository repository) {
        this.authService = authService;
        this.repository = repository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.loginUser(loginRequest);
        return response == null ?
                ResponseEntity.status(401).body(new LoginResponse(null, null, 0)) :
                ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        SignupResponse response = authService.signupUser(signupRequest);

        return response == null ?
                ResponseEntity.status(401).body(new LoginResponse(null, null, 0)) :
                ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshToken refreshToken) {
        boolean isValid = authService.validateRefreshToken(refreshToken.getToken(), refreshToken.getEmail());
        if (!isValid) return ResponseEntity.status(401).body(null);
        return ResponseEntity.ok().body(authService.generateAccessToken(refreshToken.getEmail(), "user"));
    }

    @GetMapping("/public-key")
    public ResponseEntity<?> getPublicKey() throws Exception {
        List<PublicKeyStore> keys = repository.findAll();
        return ResponseEntity.ok(keys);
    }
}