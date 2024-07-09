/**
 * LoginController.java -- A controller for login.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList.controller;

import jakarta.servlet.http.Cookie;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.qiqijin.todoList.constant.Constants;
import com.qiqijin.todoList.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    
    private final CookieService cookieService;
    
    @Autowired
    public LoginController(CookieService cookieService) {
	this.cookieService = cookieService;
    }
    
    @GetMapping("/token")
    public Cookie tokenLogin(HttpServletResponse response, HttpServletRequest request, @RequestParam String password) {
	cookieService.deleteCookie(response, WebUtils.getCookie(request, Constants.TOKEN_COOKIE_KEY));
	Cookie tokenCookie = cookieService.generateTokenCookie(password);
	response.addCookie(tokenCookie);
	return tokenCookie;
    }
}
