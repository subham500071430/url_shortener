package com.app.user.service;

import com.app.user.dto.JwtToken;
import com.app.user.dto.LoginRequest;
import com.app.user.dto.SignupRequest;
import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import com.app.user.util.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    JwtUtil jwtUtil;

    @Autowired
    UserService(UserRepository userRepository, ModelMapper modelMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public JwtToken loginUser(LoginRequest loginRequest) {

        User user = modelMapper.map(loginRequest, User.class);
        Optional<User> result = userRepository.findById(user.getEmail());

        if (result.isEmpty() || !result.get().getPassword().equals(user.getPassword())) {
            return null;
        }

        return new JwtToken(jwtUtil.generateToken(loginRequest.getEmail()));
    }

    @Transactional
    public JwtToken signupUser(SignupRequest signupRequest) {

        User user = modelMapper.map(signupRequest, User.class);

        if (userRepository.findById(user.getEmail()).isPresent()) {
            return null;
        }

        userRepository.save(user);
        return new JwtToken(jwtUtil.generateToken(signupRequest.getEmail()));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userRepository.findById(username).get(), UserDetails.class);
    }
}
