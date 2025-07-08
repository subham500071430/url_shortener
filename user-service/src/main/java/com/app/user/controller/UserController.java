package com.app.user.controller;

import com.app.user.dto.LoginRequest;
import com.app.user.entity.User;
import com.app.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

       UserService userService;

       @Autowired
       UserController(UserService userService){
           this.userService = userService;
       }

       @GetMapping(value = "/login")
       public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
              return ResponseEntity.ok(userService.loginUser(loginRequest));
       }

       @GetMapping(value = "/getAllUsers")
       public ResponseEntity<?> getAllUsers(){
              return ResponseEntity.ok("subham,prakash,aman");
       }
}
