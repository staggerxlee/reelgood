package com.reelgood.service;

import com.reelgood.model.UserModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.reelgood.config.DbConfig;

public class RegisterService {
    
    // Simple validation methods
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public boolean isValidPhone(String phone) {
        return phone != null && phone.length() >= 10 && phone.matches("\\d+");
    }
    
    // Check if email already exists
    public boolean isEmailExists(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;
        
        try {
            conn = DbConfig.getDbConnection();
            // Make table name lowercase for consistency
            String sql = "SELECT COUNT(*) FROM user WHERE Email=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            
            System.out.println("Checking if email exists: " + email);
            rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
                System.out.println("Email already exists: " + email);
            } else {
                System.out.println("Email does not exist: " + email);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            throw new SQLException("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error checking email: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        
        return exists;
    }
    
    // Add user method
    public Boolean addUser(UserModel userModel) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;
        
        try {
            conn = DbConfig.getDbConnection();
            
            // First check if email exists
            if (isEmailExists(userModel.getEmail())) {
                return false;
            }
            
            String insertQuery = "INSERT INTO user (Username, Password, Email, Phone, Address, Gender, Role) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            stmt = conn.prepareStatement(insertQuery);
            stmt.setString(1, userModel.getUsername());
            stmt.setString(2, userModel.getPassword());
            stmt.setString(3, userModel.getEmail());
            stmt.setString(4, userModel.getPhone());
            stmt.setString(5, userModel.getAddress());
            stmt.setString(6, userModel.getGender());
            stmt.setString(7, userModel.getRole());
            
            System.out.println("Attempting to add user: " + userModel.getEmail());
            int result = stmt.executeUpdate();
            success = result > 0;
            System.out.println("User registration result: " + (success ? "Success" : "Failed"));
            
            return success;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.out.println("SQL Error during registration: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}