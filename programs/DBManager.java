package studentmarksportal;

import java.sql.*;
import java.util.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/student_portal";
    private static final String USER = "root";
    private static final String PASS = "root";

    public DBManager() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "username VARCHAR(50) PRIMARY KEY," +
                    "password VARCHAR(100) NOT NULL," +
                    "is_admin BOOLEAN DEFAULT FALSE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS courses (" +
                    "course_name VARCHAR(100) PRIMARY KEY)");

            stmt.execute("CREATE TABLE IF NOT EXISTS student_courses (" +
                    "username VARCHAR(50)," +
                    "course_name VARCHAR(100)," +
                    "PRIMARY KEY (username, course_name)," +
                    "FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE," +
                    "FOREIGN KEY (course_name) REFERENCES courses(course_name) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS marks (" +
                    "username VARCHAR(50)," +
                    "course_name VARCHAR(100)," +
                    "mark INT DEFAULT 0," +
                    "PRIMARY KEY (username, course_name)," +
                    "FOREIGN KEY (username, course_name) REFERENCES student_courses(username, course_name) ON DELETE CASCADE)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean validateLogin(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAdmin(String username) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT is_admin FROM users WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_admin");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUser(String username, String password, boolean isAdmin) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, is_admin) VALUES (?, ?, ?)")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setBoolean(3, isAdmin);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourse(String courseName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT IGNORE INTO courses (course_name) VALUES (?)")) {

            stmt.setString(1, courseName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void enrollStudent(String username, String courseName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO student_courses (username, course_name) VALUES (?, ?)")) {

            stmt.setString(1, username);
            stmt.setString(2, courseName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getStudentCourses(String username) {
        List<String> courses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT course_name FROM student_courses WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public void setMark(String username, String courseName, int mark) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("REPLACE INTO marks (username, course_name, mark) VALUES (?, ?, ?)")) {

            stmt.setString(1, username);
            stmt.setString(2, courseName);
            stmt.setInt(3, mark);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getMark(String username, String courseName) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT mark FROM marks WHERE username = ? AND course_name = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, courseName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("mark");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Not found
    }

    // ✅ NEW METHOD: Get all students
    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users WHERE is_admin = FALSE")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                students.add(rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // ✅ NEW METHOD: Get all courses
    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT course_name FROM courses");
            while (rs.next()) {
                courses.add(rs.getString("course_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
