package com.todolist.user;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todolist.exception.CustomException;
import com.todolist.user.service.UserInterface;
import com.todolist.util.JwtUtil;



@Service
public class UserService implements UserInterface{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;
      
    @Autowired
    public UserService(
        UserRepository userRepository,
        BCryptPasswordEncoder bCryptPasswordEncoder,
        JwtUtil jwtUtil
        ){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
    }

    
    @Override
    public UserDto CreateUser(UserModel data) {

        try {
           
            if (userRepository.existsByEmail(data.getEmail())) {
                throw new CustomException("EMAIL_ALREADY_EXISTS", "Email " + data.getEmail() + " already exists.");
            }
    
            String encodedPassword = this.bCryptPasswordEncoder.encode(data.getPassword());
    
            data.setPassword(encodedPassword);
            
            UserModel user = userRepository.save(data);
    
            UserDto resp = modelMapper.map(user, UserDto.class);

            return resp;

        } catch (Exception e) {
            throw e;
        }
        
    }

     
    @Override
    public LoginResponseDto LoginUser(LoginDto data){

        if (!userRepository.existsByEmail(data.getEmail()))  throw new CustomException("EMAIL_ALREADY_EXISTS", "Email " + data.getEmail() + " already exists.");

        UserModel user = userRepository.findByEmail(data.getEmail());

        if(!this.bCryptPasswordEncoder.matches(data.getPassword(), user.getPassword())) throw new CustomException("INCORRECT_PASSWORD", "incorrect password. Ensure to put the correct password");

        LoginResponseDto resp = modelMapper.map(user, LoginResponseDto.class);

        resp.setAccessToken(this.jwtUtil.generateToken(resp.getId(), resp.getEmail()));

        return resp;

    }

    @Override 
    public UserDto UserProfile(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserModel user = userRepository.findByEmail(userDetails.getUsername());

        UserDto resp = modelMapper.map(user, UserDto.class);

        return resp;
    }

}