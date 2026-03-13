package fa.training.main;

import java.util.*;
import java.text.SimpleDateFormat;
import fa.training.entities.Student;
import fa.training.dao.StudentDao;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static StudentDao dao = new StudentDao();
    
    public static String standardizeName(String name) {
        if (name == null || name.isEmpty()) return "";
        name = name.trim();
        while (name.contains("  ")) {
            name = name.replace("  ", " ");
        }
        String[] words = name.split(" ");
        String result = "";
        for (String w : words) {
            String first = w.substring(0, 1).toUpperCase();
            String rest = w.substring(1).toLowerCase();
            result += first + rest + " ";
        }
        return result.trim();
    }

    public static boolean isValidId(String id, String major) {
        if (id.length() != 10) return false;
        String prefix = major.equalsIgnoreCase("CNTT") ? "455105" : "455109";
        if (!id.startsWith(prefix)) return false;
        
        for (int i = 6; i < 10; i++) {
            if (!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }
    
    public static boolean isValidAge(java.util.Date dob) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(dob);
        
        Calendar now = Calendar.getInstance();
        
        int age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (age >= 15 && age <= 110) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm mới | 2. Xóa | 3. Sửa | 4. Xem tất cả");
            System.out.println("5. Lọc theo lớp | 6. Lọc theo ngành | 7. Sắp xếp GPA | 8. SV theo tháng sinh");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của em: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
                
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: deleteStudent(); break;
                    case 3: System.out.println("Tính năng chưa yêu cầu!"); break;
                    case 4: printList(""); break;
                    case 5: filterByClass(); break;
                    case 6: filterByMajor(); break;
                    case 7: printList("ORDER BY gpa DESC"); break;
                    case 8: filterByMonth(); break;
                    case 0: 
                        System.out.println("Đang thoát..."); 
                        break;
                    default: 
                        System.out.println("Vui lòng chọn từ 0 đến 8!");
                        }
            }
                 catch (Exception e) {
                System.out.println("Có lỗi: " + e.getMessage());
            }
        }
    }
    
    private static void addStudent() throws Exception {
        System.out.print("Ngành (CNTT/KTPM): ");
        String major = sc.nextLine().toUpperCase();
        System.out.print("Mã SV: ");
        String id = sc.nextLine();
        if (!isValidId(id, major)) throw new Exception("Mã không đúng định dạng");

        System.out.print("Họ tên: ");
        String name = standardizeName(sc.nextLine());

        System.out.print("Ngày sinh (dd/MM/yyyy): ");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d = sdf.parse(sc.nextLine());
        java.sql.Date sqlDate = new java.sql.Date(d.getTime());
        if (!isValidAge(d)) throw new Exception("Sai độ tuổi quy định");

        System.out.print("GPA: ");
        double gpa = Double.parseDouble(sc.nextLine());
        System.out.print("Lớp: ");
        String className = sc.nextLine().toUpperCase();

        Student s = new Student(id, name, sqlDate, major, gpa, className);
        if (dao.addStudent(s)) System.out.println("Thêm thành công!");
    }

    private static void updateStudent() throws Exception {
        System.out.print("Nhập mã SV cần sửa: ");
        String id = sc.nextLine();
        
        List<Student> list = dao.getStudents("WHERE student_id = '" + id + "'");
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy sinh viên!");
            return;
        }
        
        
    }
    
    private static void printList(String condition) {
        List<Student> list = dao.getStudents(condition);
        System.out.println("------------------------------------------------------------");
        for (Student s : list) {
            System.out.println(s.getStudentId() + " | " + s.getMajor() + " | " + s.getFullName() + " | " + s.getDob() + " | " + s.getGpa() + " | " + s.getClassName());
        }
    }

    private static void filterByMonth() {
        System.out.print("Nhập tháng: ");
        String month = sc.nextLine();
        printList("WHERE MONTH(dob) = " + month);
    }
    
    private static void filterByClass() {
        System.out.print("Nhập tên lớp: ");
        printList("WHERE class_name = '" + sc.nextLine() + "'");
    }
    
    private static void filterByMajor() {
        System.out.print("Nhập ngành: ");
        printList("WHERE major = '" + sc.nextLine().toUpperCase() + "'");
    }

    private static void deleteStudent() {
        System.out.print("Mã SV cần xóa: ");
        if (dao.deleteStudent(sc.nextLine())) System.out.println("Xóa thành công!");
        else System.out.println("Không tìm thấy!");
    }
}