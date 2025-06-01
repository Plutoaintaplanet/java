package memlog2;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.SpinnerDateModel;

public class MemoEntryWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField titleField;
    private JTextArea contentArea;
    private JSpinner dateSpinner;
    private Memo2Repository repository;
    private MemLog2Editor editor;

    public MemoEntryWindow(MemLog2Editor editor) {
        this.editor = editor;
        this.repository = new Memo2Repository();

        setTitle("New Memo");
        setSize(400, 300);
        setLocationRelativeTo(null);

        titleField = new JTextField(20);
        contentArea = new JTextArea(5, 20);
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMemo());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Content:"));
        panel.add(new JScrollPane(contentArea));
        panel.add(new JLabel("Date:"));
        panel.add(dateSpinner);
        panel.add(saveButton);

        add(panel);
        setVisible(true);
    }

    private void saveMemo() {
        String title = titleField.getText();
        String content = contentArea.getText();
        Date date = (Date) dateSpinner.getValue();
        LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        Memo2 memo = new Memo2(title, content, localDate);  // Using the LocalDate version
        repository.saveMemo(memo);
        editor.refreshMemoList();
        dispose();
    }

}
