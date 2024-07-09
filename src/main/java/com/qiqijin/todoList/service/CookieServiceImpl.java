/**
 * CookieServiceImpl.java -- Implementation for CookieService.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.qiqijin.todoList.AppError;
import com.qiqijin.todoList.constant.Constants;
import com.qiqijin.todoList.util.Md5Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieServiceImpl implements CookieService {
    
    private String password;
    
    private Cookie cookie;
    
    public CookieServiceImpl(@Value("${password}") String password) {
	this.password = password;
    }

    @Override
    public Cookie generateTokenCookie(String passwordMd5) {
	if (!Objects.equals(passwordMd5, Md5Util.getMD5(this.password))) {
	    this.cookie = null;
	    throw new AppError("Password Error.");
	}
	cookie = new Cookie(Constants.TOKEN_COOKIE_KEY, UUID.randomUUID().toString());
	cookie.setMaxAge(3600);
	return cookie;
    }

    @Override
    public boolean checkTokenCookie(Cookie cookie) {
	if (this.cookie == null 
	    || cookie == null
	    || !Objects.equals(this.cookie.getName(), cookie.getName())
	    || !Objects.equals(this.cookie.getValue(), cookie.getValue())
	) {
	    return false;
	}
	return true;
    }

    @Override
    public void deleteCookie(HttpServletResponse response, Cookie token) {
	if (token != null) {
	    token.setMaxAge(0);
	    response.addCookie(token);
	}
    }
}
