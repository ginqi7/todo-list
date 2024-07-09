/**
 * AppConfig.java -- Web Config for app.
 *
 * Author: Qiqi Jin
 * Created: Tuesday,  9 July 2024.
 */

package com.qiqijin.todoList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    
    @Autowired
    AuthFilter authFilter;
    
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
	return new FilterRegistrationBean<AuthFilter>(authFilter);
    }    

}
