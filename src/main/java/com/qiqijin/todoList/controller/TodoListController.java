package com.qiqijin.todoList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qiqijin.todoList.model.TodoDTO;
import com.qiqijin.todoList.service.TodoListService;



@RestController
public class TodoListController {

    final TodoListService todoListService;
    
    @Autowired
    public TodoListController(TodoListService todoListService) {
	this.todoListService = todoListService;
    }
    
    @GetMapping("/list")
    public List<TodoDTO> list(@RequestParam(required = false) String page, @RequestParam(required = false) String size) {
	return todoListService.list(page, size);
    }
    
    @PostMapping("/add")
    public TodoDTO add(@RequestBody String todo) {
	return todoListService.add(todo);
    }
    
    @PostMapping("/update")
    public TodoDTO update(@RequestParam String todoId, @RequestBody String todo) {
	return todoListService.update(todoId, todo);
    }
    
    @DeleteMapping("/delete")
    public TodoDTO delete(@RequestParam String todoId) {
	return todoListService.delete(todoId);
    }
    
    @GetMapping("/done")
    public TodoDTO done(@RequestParam String todoId) {
	return todoListService.done(todoId);
    }
    
    @GetMapping("/toggleStatus")
    public TodoDTO toggleStatus(@RequestParam String todoId) {
	return todoListService.toggleStatus(todoId);
    }
}
