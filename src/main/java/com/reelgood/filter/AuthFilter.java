package com.reelgood.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*", "/admin/*"})
public class AuthFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        String requestPath = httpRequest.getRequestURI();
        
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdminURL = requestPath.startsWith(httpRequest.getContextPath() + "/admin/");
        boolean isUserURL = requestPath.startsWith(httpRequest.getContextPath() + "/user/");
        boolean isAdmin = isLoggedIn && "admin".equals(session.getAttribute("role"));
        
        // Check if user is logged in either through session or valid cookie
        if (!isLoggedIn) {
            // Check for session cookie
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("reelgood_session".equals(cookie.getName()) && session != null) {
                        // Cookie exists, but session is not valid
                        // Remove the cookie since it's not matching a valid session
                        cookie.setMaxAge(0);
                        cookie.setPath("/");
                        httpResponse.addCookie(cookie);
                        break;
                    }
                }
            }
        }
        
        if (isLoggedIn) {
            // User is logged in
            if (isAdminURL && !isAdmin) {
                // Non-admin trying to access admin area
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/dashboard");
            } else {
                // Allow logged-in user to continue
                chain.doFilter(request, response);
            }
        } else {
            // User is not logged in
            if (isUserURL || isAdminURL) {
                // Redirect to login page
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            } else {
                // Allow public resources to be accessed
                chain.doFilter(request, response);
            }
        }
    }
    
    @Override
    public void destroy() {
    }
}