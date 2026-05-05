package studentmarksportal;

import java.util.*;

public class DBManager {
    private static Map<String, User> users = new HashMap<>();
    private static Set<String> courses = new HashSet<>();
    private static Map<String, Set<String>> studentCourses = new HashMap<>();
    private static Map<String, Map<String, Integer>> marks = new HashMap<>();

    private static class User {
        String username;
        String password;
        boolean isAdmin;

        User(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }

    public DBManager() {
        // Initialize with sample data
        addUser("admin", "admin123", true);
        addUser("student1", "pass123", false);
        addUser("student2", "pass123", false);
        
        addCourse("Math");
        addCourse("Science");
        addCourse("English");
        
        enrollStudent("student1", "Math");
        enrollStudent("student1", "Science");
        enrollStudent("student2", "English");
        enrollStudent("student2", "Science");
        
        setMark("student1", "Math", 85);
        setMark("student1", "Science", 90);
        setMark("student2", "English", 88);
        setMark("student2", "Science", 92);
    }

    public boolean validateLogin(String username, String password) {
        User user = users.get(username);
        return user != null && user.password.equals(password);
    }

    public boolean isAdmin(String username) {
        User user = users.get(username);
        return user != null && user.isAdmin;
    }

    public void addUser(String username, String password, boolean isAdmin) {
        if (!users.containsKey(username)) {
            users.put(username, new User(username, password, isAdmin));
        }
    }

    public void addCourse(String courseName) {
        courses.add(courseName);
    }

    public void enrollStudent(String username, String courseName) {
        if (users.containsKey(username) && courses.contains(courseName)) {
            studentCourses.computeIfAbsent(username, k -> new HashSet<>()).add(courseName);
        }
    }

    public List<String> getStudentCourses(String username) {
        Set<String> courseSet = studentCourses.getOrDefault(username, new HashSet<>());
        return new ArrayList<>(courseSet);
    }

    public void setMark(String username, String courseName, int mark) {
        if (users.containsKey(username) && courses.contains(courseName)) {
            marks.computeIfAbsent(username, k -> new HashMap<>()).put(courseName, mark);
        }
    }

    public int getMark(String username, String courseName) {
        Map<String, Integer> userMarks = marks.getOrDefault(username, new HashMap<>());
        return userMarks.getOrDefault(courseName, -1);
    }

    public List<String> getAllStudents() {
        List<String> students = new ArrayList<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            if (!entry.getValue().isAdmin) {
                students.add(entry.getKey());
            }
        }
        return students;
    }

    public List<String> getAllCourses() {
        return new ArrayList<>(courses);
    }
}
