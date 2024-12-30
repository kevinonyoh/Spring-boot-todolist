package com.todolist.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

     private String id;

     private String name;

     private String email;
     
} 