package studentmarksportal;

public class Marks {
    private String studentUsername;
    private String courseName;
    private int mark;

    public Marks(String studentUsername, String courseName, int mark) {
        this.studentUsername = studentUsername;
        this.courseName = courseName;
        this.mark = mark;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
