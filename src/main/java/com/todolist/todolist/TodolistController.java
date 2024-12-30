package com.todolist.todolist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todolist.dto.UpdateTodoDto;
import com.todolist.todolist.model.TodolistModel;

import jakarta.validation.Valid;

@RequestMapping("/todolist")
@RestController
public class TodolistController {
    
    private final TodolistService todolistService;

    public TodolistController(
        TodolistService todolistService
    ){
       this.todolistService = todolistService;
    }

    @PostMapping("/create")
    public ResponseEntity<TodolistModel> createTodo(@Valid @RequestBody TodolistModel body){

       TodolistModel todo= todolistService.createTodolist(body);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todo")
    public ResponseEntity<Optional<TodolistModel>> findTodo(@RequestParam("id") UUID id ){
        
       Optional<TodolistModel> todo = todolistService.findTodo(id);

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/todo")
    public ResponseEntity<TodolistModel> updateTodo(@RequestParam("id") UUID id, @RequestBody UpdateTodoDto body){

        TodolistModel todo = todolistService.updateTodo(id, body);

        return new ResponseEntity<>(todo, HttpStatus.OK);

    }

    @GetMapping()
    public  ResponseEntity<List<TodolistModel>> findTodoList(){
        
        List<TodolistModel> resp = todolistService.findTodoList();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/todo")
    public ResponseEntity<String> deleteTodo(@RequestParam("id") UUID id){
         
        todolistService.deleteTodo(id);

        return ResponseEntity.noContent().build();
    }
    
    
}
