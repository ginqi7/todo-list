/**
 * GenericResponse.java -- Generic Response for all controllers.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList.model;

import java.io.Serializable;

public class GenericResponse<T> implements Serializable {
    private boolean status;
    private String extraMsg;
    private T data;
    
    public boolean isStatus() {
	return status;
    }
    public void setStatus(boolean status) {
	this.status = status;
    }
    public String getExtraMsg() {
	return extraMsg;
    }
    public void setExtraMsg(String extraMsg) {
	this.extraMsg = extraMsg;
    }
    public T getData() {
	return data;
    }
    public void setData(T data) {
	this.data = data;
    }
}
