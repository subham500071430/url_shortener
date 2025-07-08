package com.app.user.service;

import com.app.user.dto.LoginRequest;
import com.app.user.dto.LoginResponse;
import com.app.user.entity.User;
import com.app.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

       UserRepository userRepository;
       ModelMapper modelMapper;

       @Autowired
       UserService(UserRepository userRepository,ModelMapper modelMapper){
           this.userRepository = userRepository;
           this.modelMapper = modelMapper;
       }

       public LoginResponse loginUser(LoginRequest loginRequest){
              User user = modelMapper.map(loginRequest , User.class);
              Optional<User> result = userRepository.findById(user.getEmail());

              if(result.isEmpty() || ! result.get().getPassword().equals(user.getPassword())){
                  return new LoginResponse("Invalid Login Details");
              }

              return new LoginResponse("User details validated");
       }


}
