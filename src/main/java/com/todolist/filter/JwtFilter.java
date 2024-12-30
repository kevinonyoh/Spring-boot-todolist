package com.todolist.filter;


import com.todolist.exception.CustomException;
import com.todolist.user.CustomUserDetails;
import com.todolist.user.MyUserDetailsService;
import com.todolist.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Map;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ApplicationContext context;




    @Override
    protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                String authHeader = request.getHeader("Authorization");
                String id;
                String email;

                

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
        
                       
                        Map<String, String> claims = jwtUtil.validateTokenAndRetrieveSubject(token);
        
                        id = claims.get("id");
                       email  = claims.get("email");


                
                        if( id != null && SecurityContextHolder.getContext().getAuthentication() == null ){

                                                          
                          UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
        
                       
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);

                        }
                
                
                    }

        
                filterChain.doFilter(request, response);
    }
}
