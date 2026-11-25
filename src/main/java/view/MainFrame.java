package view;

import java.awt.CardLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);

    public MainFrame() {
        super("Recipe Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(content);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    public void addView(String name, JComponent view) {
        content.add(view, name);
    }

    public void showView(String name) {
        cards.show(content, name);
    }
}
