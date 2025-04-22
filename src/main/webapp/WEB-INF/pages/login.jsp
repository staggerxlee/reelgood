<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LogIn.css"/>
</head>
<body>
    <div class="container-box">
        <form class="box-form" action="${pageContext.request.contextPath}/login" method="post">
            <h2>Log In</h2>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="error-message"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <% if("true".equals(request.getParameter("registered"))) { %>
                <div class="success-message">Registration successful! Please log in.</div>
            <% } %>
            
            <div class="group-input">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required>
            </div>
            
            <div class="group-input">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            
            <button type="submit" class="btn">Log In</button>
            
            <p class="text-link">Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
        </form>
    </div>
</body>
</html>