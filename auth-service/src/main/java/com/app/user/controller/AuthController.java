package com.app.user.controller;

import com.app.user.entity.PublicKeyStore;
import com.app.user.repository.PublicKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("auth")
@RestController
public class AuthController {

    @Autowired
    PublicKeyRepository repository;

    @GetMapping("/public-key")
    ResponseEntity<?> getPublicKey() throws Exception {
        List<PublicKeyStore> keys = repository.findAll();
        return ResponseEntity.ok(keys);
    }
}
