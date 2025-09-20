package com.app.user.service;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.dto.SignupRequest;
import com.app.user.dto.SignupResponse;
import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import com.app.user.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    JwtUtil jwtUtil;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    public AuthService(UserRepository userRepository, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public LoginResponse loginUser(LoginRequest loginRequest) {

        User user = modelMapper.map(loginRequest, User.class);
        Optional<User> result = userRepository.findById(user.getEmail());

        if (result.isEmpty() || !result.get().getPassword().equals(user.getPassword())) {
            return null;
        }

        return new LoginResponse(generateAccessToken(user.getEmail(), "user"),
                generateRefreshToken(user.getEmail(), "user"), 864000000);
    }

    @Transactional
    public SignupResponse signupUser(SignupRequest signupRequest) {

        User user = modelMapper.map(signupRequest, User.class);

        if (userRepository.findById(user.getEmail()).isPresent()) {
            return null;
        }

        userRepository.save(user);

        return new SignupResponse(generateAccessToken(user.getEmail(), "user"),
                generateRefreshToken(user.getEmail(), "user"), 864000000);
    }

    public boolean validateRefreshToken(String refreshToken, String userId) {

        String stored_token = redisTemplate.opsForValue().get(userId);

        return refreshToken.equals(stored_token);
    }

    public String generateAccessToken(String userId, String role) {

        return jwtUtil.generateToken(userId, role, 36000);
    }

    public String generateRefreshToken(String userId, String role) {

        String token = jwtUtil.generateToken(userId, role, 864000000);

        redisTemplate.opsForValue().set(userId, token, 10, TimeUnit.DAYS);

        return token;
    }
}
