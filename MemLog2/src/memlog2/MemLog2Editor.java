package memlog2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MemLog2Editor extends JFrame {
    private static final long serialVersionUID = 1L;

    private JList<Memo2> memoList;
    private DefaultListModel<Memo2> memoListModel;
    private Memo2Repository repository;

    public MemLog2Editor() {
        setTitle("MemLog2");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        repository = new Memo2Repository();

        memoListModel = new DefaultListModel<>();
        memoList = new JList<>(memoListModel);
        memoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton addButton = new JButton("Add Memo");
        addButton.addActionListener(e -> new MemoEntryWindow(this));

        JButton viewButton = new JButton("View Memo");
        viewButton.addActionListener(e -> {
            Memo2 selected = memoList.getSelectedValue();
            if (selected != null) {
                new FullContentWindow(selected);
            }
        });

        JButton deleteButton = new JButton("Delete Memo");
        deleteButton.addActionListener(e -> {
            Memo2 selected = memoList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete this memo?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    repository.deleteMemo(selected.getId());
                    refreshMemoList();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        add(new JScrollPane(memoList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshMemoList();

        setVisible(true);
    }

    public void refreshMemoList() {
        memoListModel.clear();
        List<Memo2> memos = repository.getAllMemos();
        for (Memo2 memo : memos) {
            memoListModel.addElement(memo);
        }
    }
}
