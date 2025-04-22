<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Register.css"/>
</head>
<body>
    <div class="container-box">
        <form class="box-form" action="${pageContext.request.contextPath}/register" method="post">
            <h2>Register</h2>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="error-message"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <div class="input-group full">
                <label for="username">Username *</label>
                <input type="text" id="username" name="username" value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" required />
            </div>
            
            <div class="input-group full">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required />
            </div>
            
            <div class="input-group full">
                <label for="password">Password *</label>
                <input type="password" id="password" name="password" required />
            </div>
            
            <div class="input-group full">
                <label for="confirmPassword">Confirm Password *</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required />
            </div>
            
            <div class="input-group full">
                <label for="phone">Phone</label>
                <input type="tel" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>" />
            </div>
            
            <div class="input-group full">
                <label for="address">Address</label>
                <textarea id="address" name="address"><%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %></textarea>
            </div>
            
            <div class="input-group full">
                <label for="gender">Gender</label>
                <select id="gender" name="gender">
                    <option value="">Select Gender</option>
                    <option value="male" <%= "male".equals(request.getAttribute("gender")) ? "selected" : "" %>>Male</option>
                    <option value="female" <%= "female".equals(request.getAttribute("gender")) ? "selected" : "" %>>Female</option>
                    <option value="other" <%= "other".equals(request.getAttribute("gender")) ? "selected" : "" %>>Other</option>
                </select>
            </div>
            
            <input type="hidden" name="role" value="user" />
            
            <button type="submit" class="btn">Register</button>
            
            <p class="text-link">Already have an account? <a href="${pageContext.request.contextPath}/login">Login</a></p>
        </form>
    </div>
</body>
</html>