package fa.training.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import fa.training.entities.Student;
import fa.training.utils.DBUtils;

public class StudentDao {

    public boolean addStudent(Student s) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "INSERT INTO students (student_id, full_name, dob, major, gpa, class_name) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, s.getStudentId());
            ps.setString(2, s.getFullName());
            ps.setDate(3, s.getDob());
            ps.setString(4, s.getMajor());
            ps.setDouble(5, s.getGpa());
            ps.setString(6, s.getClassName());
            int check = ps.executeUpdate();
            if (check > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return result;
    }

    public boolean deleteStudent(String id) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "DELETE FROM students WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            
            int check = ps.executeUpdate();
            if (check > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return result;
    }

    public boolean updateStudent(Student s) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean result = false;
        try {
            conn = DBUtils.getConnection();
            String sql = "UPDATE students SET full_name = ?, dob = ?, major = ?, gpa = ?, class_name = ? WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, s.getFullName());
            ps.setDate(2, s.getDob());
            ps.setString(3, s.getMajor());
            ps.setDouble(4, s.getGpa());
            ps.setString(5, s.getClassName());
            ps.setString(6, s.getStudentId());
            
            int check = ps.executeUpdate();
            if (check > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return result;
    }

    public List<Student> getStudents(String condition) {
        List<Student> list = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            String sql = "SELECT * FROM students " + condition;
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            while (rs.next()) {
                String id = rs.getString("student_id");
                String name = rs.getString("full_name");
                Date dob = rs.getDate("dob");
                String major = rs.getString("major");
                double gpa = rs.getDouble("gpa");
                String className = rs.getString("class_name");
                
                Student s = new Student(id, name, dob, major, gpa, className);
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(conn);
        }
        return list;
    }
}