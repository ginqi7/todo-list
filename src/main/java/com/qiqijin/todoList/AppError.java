/**
 * AppError.java -- Erros for app.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList;

public class AppError extends RuntimeException {
    public AppError(String errorMsg) {
	super(errorMsg);
    }
}
