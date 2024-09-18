/**
 * AuthFilter.java -- Filter for auth.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

import com.qiqijin.todoList.constant.Constants;
import com.qiqijin.todoList.service.CookieService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends GenericFilterBean{
    
    private final CookieService cookieService;
    
    @Autowired
    public AuthFilter(CookieService cookieService) {
	this.cookieService = cookieService;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	HttpServletRequest httpRequest = (HttpServletRequest) request;
	HttpServletResponse httpResponse = (HttpServletResponse) response;

	if (!checkAuth(httpRequest)) {
	    httpResponse.sendRedirect(Constants.LOGIN_URI);
	}
	chain.doFilter(httpRequest, httpResponse);
    }
    
    private boolean checkAuth(HttpServletRequest httpRequest) {
	String requestURI = httpRequest.getRequestURI();
	if (requestURI.equals(Constants.LOGIN_URI) 
	    || requestURI.equals(Constants.TOKEN_URI)
	    || requestURI.endsWith(".js")
	    || requestURI.endsWith(".css")
	    || requestURI.endsWith(".json")
	) {
	    return true;
	}

	Cookie cookie = WebUtils.getCookie(httpRequest,Constants.TOKEN_COOKIE_KEY);
	return cookieService.checkTokenCookie(cookie);
    }

}
