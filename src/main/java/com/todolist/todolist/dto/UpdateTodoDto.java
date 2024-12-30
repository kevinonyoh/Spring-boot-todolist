package com.todolist.todolist.dto;

import com.todolist.todolist.model.Status;

import lombok.Data;


@Data
public class UpdateTodoDto {

    private String taskName;

   
    private String taskDescription;

    private  Status status;   
    
}