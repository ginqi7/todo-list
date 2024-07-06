package com.qiqijin.todoList.service;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.qiqijin.todoList.dao.TodoListDao;
import com.qiqijin.todoList.model.Status;
import com.qiqijin.todoList.model.TodoDTO;

@Component
public class TodoListServiceImpl implements TodoListService {
    
    private final TodoListDao todoListDao;
    
    public TodoListServiceImpl(TodoListDao todoListDao) {
	this.todoListDao = todoListDao;
    }

    @Override
    public List<TodoDTO> list(String page, String size) {
	return todoListDao.list(page, size);
    }

    @Override
    public TodoDTO add(String todo) {
	TodoDTO todoDto = new TodoDTO();
	todoDto.setData(todo);
	todoDto.setId(UUID.randomUUID().toString());
	todoDto.setDate(LocalDateTime.now());
	todoDto.setStatus(Status.TODO);
	return todoListDao.add(todoDto);

    }

    @Override
    public TodoDTO update(String todoId, String todo) {
	TodoDTO todoDTO = todoListDao.get(todoId);
	todoDTO.setData(todo);
	return todoListDao.update(todoDTO);

    }

    @Override
    public TodoDTO done(String todoId) {
	TodoDTO todoDTO = todoListDao.get(todoId);
	todoDTO.setStatus(Status.DONE);
	return todoListDao.done(todoDTO);


    }

    @Override
    public TodoDTO toggleStatus(String todoId) {
	TodoDTO todoDTO = todoListDao.get(todoId);
	todoDTO.setStatus(todoDTO.getStatus().toggle());
	todoListDao.toggleStatus(todoDTO);
	return todoDTO;
    }

    @Override
    public TodoDTO delete(String todoId) {
	TodoDTO todoDTO = todoListDao.get(todoId);
	todoDTO.setStatus(Status.DELETE);
	todoListDao.toggleStatus(todoDTO);
	return todoDTO;
    }

}
