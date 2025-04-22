package com.reelgood.service;

import com.reelgood.model.UserModel;
import com.reelgood.config.DbConfig;
import com.reelgood.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {
    
    // Check if email already exists
    public boolean isEmailExists(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            conn = DbConfig.getDbConnection();
            String sql = "SELECT COUNT(*) FROM User WHERE Email = ?";  // Note the exact case of "User"
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        
        return exists;
    }
    
    // Register a new user
    public int registerUser(UserModel user) throws SQLException {
        if (isEmailExists(user.getEmail())) {
            return -1; // Email already exists
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int userId = -3; // Default error status
        
        try {
            conn = DbConfig.getDbConnection();
            String sql = "INSERT INTO User (Username, Password, Email, Phone, Address, Gender, Role) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, PasswordUtil.hashPassword(user.getPassword()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getGender());
            stmt.setInt(7, user.getRole() == null ? 1 : Integer.parseInt(user.getRole())); // Default role is 1 (user)
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Error registering user: " + e.getMessage(), e);
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        
        return userId;
    }
    
    
    public Boolean addUser(UserModel user) throws SQLException {
        int result = registerUser(user);
        return result > 0;
    }
    
    // Login user
    public UserModel loginUser(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserModel user = null;
        
        try {
            conn = DbConfig.getDbConnection();
            String sql = "SELECT * FROM User WHERE Email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("Password");
                
                if (PasswordUtil.verifyPassword(password, hashedPassword)) {
                    user = new UserModel();
                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhone(rs.getString("Phone"));
                    user.setAddress(rs.getString("Address"));
                    user.setGender(rs.getString("Gender"));
                    user.setRole(String.valueOf(rs.getInt("Role"))); // Convert Role int to String
                }
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found: " + e.getMessage());
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        
        return user;
    }
    
    // Validation methods from RegisterService
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public boolean isValidPhone(String phone) {
        return phone != null && phone.length() >= 10 && phone.matches("\\d+");
    }
}