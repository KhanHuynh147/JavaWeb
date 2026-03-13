package fa.training.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtils {
    
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentManagement", "root", "123456");
            
        } catch (Exception e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi đóng kết nối!");
        }
    }
}