import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class BorderLayoutExample extends JFrame {
    public BorderLayoutExample() {
        this.setTitle("quandz");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);

        // Create an instance of java.awt.FlowLayout
        BorderLayout borderLayout = new BorderLayout(15, 15);

        // Set the layout manager for the JFrame
        this.setLayout(borderLayout);

        // Create buttons
        JButton button = new JButton(" 1");
        JButton button1 = new JButton(" 2");
        JButton button2 = new JButton(" 3");
        JButton button3 = new JButton(" 4");
        JButton button4 = new JButton(" 5");

        // Add buttons to the JFrame
        this.add(button, BorderLayout.NORTH);
        this.add(button1, BorderLayout.SOUTH);
        this.add(button2, BorderLayout.WEST);
        this.add(button3, BorderLayout.EAST);
        this.add(button4, BorderLayout.CENTER);

        // Set default close operation and make the frame visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of FlowLayoutExample to display the GUI
        new BorderLayoutExample();
    }
}
