package com.app.user.controller;

import com.app.user.dto.*;
import com.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        JwtToken token = userService.loginUser(loginRequest);
        if (token == null) {
            return ResponseEntity.badRequest().body(new LoginResponse("user id/pass is incorrect"));
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        JwtToken token = userService.signupUser(signupRequest);
        if (token == null) {
            return ResponseEntity.badRequest().body(new SignupResponse("user already taken"));
        }
        return ResponseEntity.ok(token);
    }

    // to-do
    // add jwt authentication
}
