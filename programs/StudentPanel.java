package studentmarksportal;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentPanel extends JPanel {
    private final String username;
    private final StudentPortalApp app;
    private final DBManager db;

    public StudentPanel(StudentPortalApp app, String username) {
        this.username = username;
        this.app = app;
        this.db = app.getDb();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Welcome, " + username, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        add(titleLabel, BorderLayout.NORTH);

        // Fetch courses and marks
        List<String> courses = db.getStudentCourses(username);

        if (courses.isEmpty()) {
            add(new JLabel("You are not enrolled in any courses."), BorderLayout.CENTER);
        } else {
            // Table Data
            String[] columnNames = {"Course", "Marks"};
            String[][] data = new String[courses.size()][2];

            for (int i = 0; i < courses.size(); i++) {
                String course = courses.get(i);
                int mark = db.getMark(username, course);
                String markText = (mark >= 0) ? String.valueOf(mark) : "N/A";

                data[i][0] = course;
                data[i][1] = markText;
            }

            JTable table = new JTable(data, columnNames);
            table.setFont(new Font("Arial", Font.PLAIN, 14));
            table.setRowHeight(25);
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        }

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(e -> app.switchToLogin());
        add(logoutButton, BorderLayout.SOUTH);
    }
}
