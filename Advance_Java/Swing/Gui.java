import javax.swing.*;
public class Gui {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button = new JButton("Press");
        frame.add(button);
        frame.setVisible(true);
    }
}
