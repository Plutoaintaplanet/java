package studentmarksportal;

public class StudentCourse {
    private String studentUsername;
    private String courseName;

    public StudentCourse(String studentUsername, String courseName) {
        this.studentUsername = studentUsername;
        this.courseName = courseName;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getCourseName() {
        return courseName;
    }
}
