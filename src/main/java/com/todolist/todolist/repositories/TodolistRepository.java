package com.todolist.todolist.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolist.todolist.model.TodolistModel;
import com.todolist.user.UserModel;

public interface TodolistRepository extends JpaRepository<TodolistModel, UUID> {

    TodolistModel save(TodolistModel user);
    
    List<TodolistModel> findByUser(UserModel user);



}
