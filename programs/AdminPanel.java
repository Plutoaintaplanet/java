package studentmarksportal;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JPanel {
    private JTextField usernameField, passwordField, courseField, markField;
    private JComboBox<String> studentList, courseList;
    private StudentPortalApp app;

    public AdminPanel(StudentPortalApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Add Student Section
        JPanel studentPanel = new JPanel(new GridBagLayout());
        studentPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));
        GridBagConstraints spGbc = new GridBagConstraints();
        spGbc.insets = new Insets(5, 5, 5, 5);
        spGbc.fill = GridBagConstraints.HORIZONTAL;
        spGbc.gridx = 0;
        spGbc.gridy = 0;

        studentPanel.add(new JLabel("Username:"), spGbc);
        spGbc.gridx = 1;
        usernameField = new JTextField(15);
        studentPanel.add(usernameField, spGbc);

        spGbc.gridy++;
        spGbc.gridx = 0;
        studentPanel.add(new JLabel("Password:"), spGbc);
        spGbc.gridx = 1;
        passwordField = new JTextField(15);
        studentPanel.add(passwordField, spGbc);

        spGbc.gridy++;
        spGbc.gridx = 0;
        spGbc.gridwidth = 2;
        JButton addStudentButton = new JButton("Add Student");
        studentPanel.add(addStudentButton, spGbc);

        addStudentButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (!username.isEmpty() && !password.isEmpty()) {
                app.getDb().addUser(username, password, false);
                JOptionPane.showMessageDialog(this, "Student added.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Both fields are required.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        gbc.gridy = row++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(studentPanel, gbc);

        // Add Course Section
        JPanel coursePanel = new JPanel(new GridBagLayout());
        coursePanel.setBorder(BorderFactory.createTitledBorder("Add New Course"));
        GridBagConstraints cpGbc = new GridBagConstraints();
        cpGbc.insets = new Insets(5, 5, 5, 5);
        cpGbc.fill = GridBagConstraints.HORIZONTAL;

        cpGbc.gridx = 0;
        cpGbc.gridy = 0;
        coursePanel.add(new JLabel("Course Name:"), cpGbc);

        cpGbc.gridx = 1;
        courseField = new JTextField(15);
        coursePanel.add(courseField, cpGbc);

        cpGbc.gridy++;
        cpGbc.gridx = 0;
        cpGbc.gridwidth = 2;
        JButton addCourseButton = new JButton("Add Course");
        coursePanel.add(addCourseButton, cpGbc);

        addCourseButton.addActionListener(e -> {
            String course = courseField.getText().trim();
            if (!course.isEmpty()) {
                app.getDb().addCourse(course);
                JOptionPane.showMessageDialog(this, "Course added.");
                refreshData();
            } else {
                JOptionPane.showMessageDialog(this, "Course name cannot be empty.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        gbc.gridy = row++;
        mainPanel.add(coursePanel, gbc);

        // Enrollment Section
        JPanel enrollPanel = new JPanel(new GridBagLayout());
        enrollPanel.setBorder(BorderFactory.createTitledBorder("Enroll Student in Course"));
        GridBagConstraints epGbc = new GridBagConstraints();
        epGbc.insets = new Insets(5, 5, 5, 5);
        epGbc.fill = GridBagConstraints.HORIZONTAL;

        epGbc.gridx = 0;
        epGbc.gridy = 0;
        enrollPanel.add(new JLabel("Student:"), epGbc);

        epGbc.gridx = 1;
        studentList = new JComboBox<>();
        enrollPanel.add(studentList, epGbc);

        epGbc.gridy++;
        epGbc.gridx = 0;
        enrollPanel.add(new JLabel("Course:"), epGbc);

        epGbc.gridx = 1;
        courseList = new JComboBox<>();
        enrollPanel.add(courseList, epGbc);

        epGbc.gridy++;
        epGbc.gridx = 0;
        epGbc.gridwidth = 2;
        JButton enrollButton = new JButton("Enroll");
        enrollPanel.add(enrollButton, epGbc);

        enrollButton.addActionListener(e -> {
            String student = (String) studentList.getSelectedItem();
            String course = (String) courseList.getSelectedItem();
            if (student != null && course != null) {
                app.getDb().enrollStudent(student, course);
                JOptionPane.showMessageDialog(this, "Student enrolled.");
                refreshData();
            }
        });

        gbc.gridy = row++;
        mainPanel.add(enrollPanel, gbc);

        // Assign Mark Section
        JPanel markPanel = new JPanel(new GridBagLayout());
        markPanel.setBorder(BorderFactory.createTitledBorder("Assign Marks"));
        GridBagConstraints mpGbc = new GridBagConstraints();
        mpGbc.insets = new Insets(5, 5, 5, 5);
        mpGbc.fill = GridBagConstraints.HORIZONTAL;

        mpGbc.gridx = 0;
        mpGbc.gridy = 0;
        markPanel.add(new JLabel("Mark:"), mpGbc);

        mpGbc.gridx = 1;
        markField = new JTextField(10);
        markPanel.add(markField, mpGbc);

        mpGbc.gridy++;
        mpGbc.gridx = 0;
        mpGbc.gridwidth = 2;
        JButton assignMarkButton = new JButton("Assign Mark");
        markPanel.add(assignMarkButton, mpGbc);

        assignMarkButton.addActionListener(e -> {
            String student = (String) studentList.getSelectedItem();
            String course = (String) courseList.getSelectedItem();
            try {
                int mark = Integer.parseInt(markField.getText().trim());
                if (student != null && course != null) {
                    app.getDb().setMark(student, course, mark);
                    JOptionPane.showMessageDialog(this, "Mark assigned.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer mark.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = row++;
        mainPanel.add(markPanel, gbc);

        // Logout Button
        JPanel logoutPanel = new JPanel();
        JButton logoutButton = new JButton("Logout");
        logoutPanel.add(logoutButton);
        logoutButton.addActionListener(e -> app.switchToLogin());

        gbc.gridy = row++;
        mainPanel.add(logoutPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Populate dropdowns
        refreshDropdowns();
    }

    public void refreshDropdowns() {
        studentList.removeAllItems();
        courseList.removeAllItems();

        List<String> students = app.getDb().getAllStudents();
        for (String s : students) studentList.addItem(s);

        List<String> courses = app.getDb().getAllCourses();
        for (String c : courses) courseList.addItem(c);
    }

    public void refreshData() {
        refreshDropdowns();
        usernameField.setText("");
        passwordField.setText("");
        courseField.setText("");
        markField.setText("");
    }
}
