package studentmarksportal;

import javax.swing.*;
import java.awt.*;

public class StudentPortalApp extends JFrame {
    private DBManager db;
    private LoginPanel loginPanel;
    private AdminPanel adminPanel;
    private StudentPanel studentPanel;

    public StudentPortalApp() {
        db = new DBManager();

        setTitle("Student Marks Portal");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new CardLayout());

        loginPanel = new LoginPanel(this);
        adminPanel = new AdminPanel(this);

        add(loginPanel, "login");
        add(adminPanel, "admin");

        switchToLogin();
    }

    public DBManager getDb() {
        return db;
    }

    public void switchToLogin() {
        loginPanel.resetFields();
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "login");
    }

    public void switchToAdmin() {
        adminPanel.refreshDropdowns();  // Make sure refreshDropdowns is public
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "admin");
    }

    public void switchToStudent(String username) {
        if (studentPanel != null) {
            remove(studentPanel);
        }
        studentPanel = new StudentPanel(this, username);
        add(studentPanel, "student");
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "student");
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentPortalApp app = new StudentPortalApp();
            app.setVisible(true);
        });
    }
}
