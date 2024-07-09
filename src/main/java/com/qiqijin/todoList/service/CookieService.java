/**
 * CookieService.java -- Service for Cookie.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    
    /**
     * Generate a Cookie.
     * @param user password
     * @return Cookie
     */
    Cookie generateTokenCookie(String password);
    
    
    /**
     * Check if a token Cookie valid.
     * @param token cookie
     * @return boolen
     */
    boolean checkTokenCookie(Cookie token);
    
    /**
     * Delete a cookie
     * @param response HttpServletResponse 
     * @param token cookie
     * @return boolen
     */
    void deleteCookie(HttpServletResponse response, Cookie token);
}
