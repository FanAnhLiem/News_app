import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.FlowLayout;

public class FlowLayoutExample extends JFrame {
    public FlowLayoutExample() {
        this.setTitle("quandz");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);

        // Create an instance of java.awt.FlowLayout
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 50, 5);

        // Set the layout manager for the JFrame
        this.setLayout(flowLayout);

        // Create buttons
        JButton button = new JButton(" 1");
        JButton button1 = new JButton(" 2");
        JButton button2 = new JButton(" 3");

        // Add buttons to the JFrame
        this.add(button);
        this.add(button1);
        this.add(button2);

        // Set default close operation and make the frame visible
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        // Create an instance of FlowLayoutExample to display the GUI
        new FlowLayoutExample();
    }
}
