package com.reelgood.controller;

import com.reelgood.model.UserModel;
import com.reelgood.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;
    private static final int COOKIE_MAX_AGE = 30 * 24 * 60 * 60;

    public LoginController() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String role = (String) session.getAttribute("role");
            if ("admin".equals(role)) {
                request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
            }
            return;
        }

        // Display login page
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String errorMessage = null;

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            errorMessage = "Email and password are required";
        } else {
            try {
                UserModel user = userService.loginUser(email, password);
                if (user != null) {
                    // Create session
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("userID", user.getUserID());
                    session.setAttribute("role", user.getRole());
                    
                    // Create a session cookie that expires after 30 days
                    Cookie sessionCookie = new Cookie("reelgood_session", session.getId());
                    sessionCookie.setMaxAge(COOKIE_MAX_AGE);
                    sessionCookie.setPath("/");
                    response.addCookie(sessionCookie);

                    // Forward to appropriate page based on role
                    if ("admin".equals(user.getRole())) {
                        request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
                    }
                    return;
                } else {
                    errorMessage = "Invalid email or password";
                }
            } catch (SQLException e) {
                errorMessage = "Database error: " + e.getMessage();
                e.printStackTrace();
            }
        }

        request.setAttribute("error", errorMessage);
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}