package com.todolist.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.todolist.decorator.Public;

@Component
public class PublicEndpointInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Public publicAnnotation = handlerMethod.getMethodAnnotation(Public.class);
            if (publicAnnotation != null) {
                
                return true;
            }
        }
       
        return true;
    }
}
