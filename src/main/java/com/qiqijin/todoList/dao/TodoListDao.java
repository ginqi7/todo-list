package com.qiqijin.todoList.dao;

import java.util.List;

import com.qiqijin.todoList.model.TodoDTO;

/**
 * Interface for access permanency todo list data
 */
public interface TodoListDao {
    
    /**
     * get a todo item.
     * @param todoId the todo identity
     * @return TodoDTO
     */
    TodoDTO get(String todoId);

    /** 
     * List some todo list.
     * @param page
     * @param size
     * @return
     */
    List<TodoDTO> list(String page, String size);

    /**
     * Add a new todo item.
     * @param todoDto
     * @return
     */
    TodoDTO add(TodoDTO todoDto);

    /**
     * Update a todo item's content
     * @param todo
     * @return
     */
    TodoDTO update(TodoDTO todo);

    /** 
     * update a todo item's status to DONE 
     * @param todo
     * @return
     */
    TodoDTO done(TodoDTO todo);

    /**
     * toggle a todo item's status between TODO and DONE
     * @param todo
     * @return
     */
    TodoDTO toggleStatus(TodoDTO todo);
    
}
