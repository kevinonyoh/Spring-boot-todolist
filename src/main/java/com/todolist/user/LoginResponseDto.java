package com.todolist.user;

import lombok.Data;

@Data
public class LoginResponseDto {
    
         private String id;
    
         private String name;
    
         private String email;
    
         private String accessToken;
         
}
