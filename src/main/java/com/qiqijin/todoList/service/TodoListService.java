package com.qiqijin.todoList.service;

import java.util.List;

import com.qiqijin.todoList.model.TodoDTO;

public interface TodoListService {

    List<TodoDTO> list(String page, String size);

    TodoDTO add(String todo);

    TodoDTO update(String todoId, String todo);

    TodoDTO done(String todoId);

    TodoDTO toggleStatus(String todoId);

    TodoDTO delete(String todoId);

}
