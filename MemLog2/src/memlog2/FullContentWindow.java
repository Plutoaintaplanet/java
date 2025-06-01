package memlog2;

import javax.swing.*;
import java.awt.*;

public class FullContentWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    public FullContentWindow(Memo2 memo) {
        setTitle(memo.getTitle());
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextArea contentArea = new JTextArea(memo.getContent());
        contentArea.setEditable(false);
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        setVisible(true);
    }
}
