package com.qiqijin.todoList.model;

import java.time.LocalDateTime;

/**
 * DTO for todo item.
 */
public class TodoDTO {
    
    /**
     * identity for todo item
     */
    private String id;
    
    /**
     * todo item's content
     */
    private String data;
    
    /**
     * the create date or update date of todo item
     */
    private LocalDateTime date;
    
    /**
     * the status of todo item
     */
    private Status status;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public void setDate(LocalDateTime date) {
	this.date = date;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }
    
    
}
