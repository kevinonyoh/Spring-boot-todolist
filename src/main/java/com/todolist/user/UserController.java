package com.todolist.user;


import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RequestMapping("/auth")
@RestController
public class UserController {

        private final UserService userService;
       
        
        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
           
        }
    
       
       
        @PostMapping("/sign-up")
        public ResponseEntity<UserDto> register(@Valid @RequestBody UserModel body) {
            
            
            UserDto resp = userService.CreateUser(body);
    
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
    
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> UserLogin(@Valid @RequestBody LoginDto body){

            LoginResponseDto resp = userService.LoginUser(body);

            return new ResponseEntity<>(resp, HttpStatus.OK);

        }

        @GetMapping("/user")
        public ResponseEntity<UserDto> userDetails(){
            
            UserDto user = userService.UserProfile();

            return new ResponseEntity<>(user, HttpStatus.OK);
           
        }
    
    
}



