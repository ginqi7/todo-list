/**
 * CustomExceptionHandler.java -- 
 *
    * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.qiqijin.todoList.model.GenericResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AppError.class)
    public ResponseEntity<GenericResponse<Object>> handleException(AppError ex) {
        GenericResponse<Object> errorResponse = new GenericResponse<Object>();
	errorResponse.setStatus(false);
	errorResponse.setExtraMsg(ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
    }
}
