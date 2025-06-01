package memlog2;

import javax.swing.SwingUtilities;

public class MemLog2Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MemLog2Editor());
    }
}
