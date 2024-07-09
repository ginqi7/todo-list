/**
 * CustomResponseBodyAdvice.java -- Custom Response Body Advice.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList;


import java.io.IOException;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.qiqijin.todoList.model.GenericResponse;

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
	return true;
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
	ServerHttpResponse response) {
	if (body instanceof GenericResponse) {
	    return body;
	} else {
	    GenericResponse<Object> genericResponse = new GenericResponse<>();
            genericResponse.setStatus(true);
	    genericResponse.setData(body);
            return genericResponse;
	}

    }
}
