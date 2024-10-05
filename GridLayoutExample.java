import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class GridLayoutExample extends JFrame {
    public GridLayoutExample() {
        this.setTitle("quandz");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);

        // Create an instance of java.awt.FlowLayout
        GridLayout gridLayout = new GridLayout(4, 4, 25, 25);

        // Set the layout manager for the JFrame
        this.setLayout(gridLayout);
        for (int i = 0; i < 16; i++) {
            JButton button = new JButton(i + "");
            this.add(button);
        }
        // // Create buttons
        // JButton button = new JButton(" 1");
        // JButton button1 = new JButton(" 2");
        // JButton button2 = new JButton(" 3");

        // // Add buttons to the JFrame
        // this.add(button);
        // this.add(button1);
        // this.add(button2);

        // Set default close operation and make the frame visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of FlowLayoutExample to display the GUI
        new GridLayoutExample();
    }
}
