package com.todolist.user.service;



import com.todolist.user.LoginDto;
import com.todolist.user.LoginResponseDto;
import com.todolist.user.UserDto;
import com.todolist.user.UserModel;


public interface UserInterface{
   UserDto CreateUser(UserModel data);

   LoginResponseDto LoginUser(LoginDto data);

   UserDto UserProfile();
   
}