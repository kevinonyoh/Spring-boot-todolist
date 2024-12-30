package com.todolist.todolist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.todolist.exception.CustomException;
import com.todolist.todolist.dto.UpdateTodoDto;
import com.todolist.todolist.model.TodolistModel;
import com.todolist.todolist.repositories.TodolistRepository;
import com.todolist.user.CustomUserDetails;
import com.todolist.user.UserModel;
import com.todolist.user.UserRepository;
import com.todolist.user.UserService;


@Service
public class TodolistService {
    
     private final TodolistRepository todolistRepository;
     private final UserRepository userRepository;

 
     @Autowired
     public TodolistService(
          TodolistRepository todolistRepository,
          UserRepository userRepository
     ){

         this.todolistRepository = todolistRepository;
         this.userRepository = userRepository;
         
     }

     public TodolistModel createTodolist(TodolistModel data){
         
          try {

               CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

               UserModel user = userRepository.findByEmail(userDetails.getUsername());
               
                data.setUser(user);
                
                return todolistRepository.save(data);
                
          } catch (Exception e) {
             throw e;
          }
          
          
     }

     public Optional<TodolistModel> findTodo(UUID data){
          try {
               return todolistRepository.findById(data);
          } catch (Exception e) {
              throw e;
          }
         
     }

     public TodolistModel updateTodo(UUID id, UpdateTodoDto data){
          try {
               
              TodolistModel val = todolistRepository.findById(id).get();


              if (data.getTaskName() != null)  val.setTaskName(data.getTaskName());
          
           
             if (data.getStatus() != null)  val.setStatus(data.getStatus());
           
           
             if (data.getTaskDescription() != null)  val.setTaskDescription(data.getTaskDescription());
           
             TodolistModel resp = todolistRepository.save(val); 

             return resp;
          
          } catch (Exception e) {
               throw e;
          }
     }


     public List<TodolistModel> findTodoList(){
          
          try {

               CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

               UserModel user = userRepository.findByEmail(userDetails.getUsername());
     
               List<TodolistModel> todos = todolistRepository.findByUser(user);
     
               return todos;    

          } catch (Exception e) {
              throw e;
          }
     }



     public void deleteTodo(UUID id){
          try {
               
               todolistRepository.deleteById(id);

          } catch (Exception e) {
               throw e;
          }
     }



}
