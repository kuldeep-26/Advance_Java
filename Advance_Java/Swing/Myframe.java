import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Myframe extends JFrame implements ActionListener
{
    static JFrame frame;
    static JTextField t1,t2,t3,t4;
    public static void main(String[] args)
    {
        t1 = new JTextField(16);
        t2 = new JTextField(16);
        t3 = new JTextField(16);

        JButton badd = new JButton("+");
        JButton bsub = new JButton("-");
        JButton bmul = new JButton("*");
        JButton bdiv = new JButton("/");

        JFrame frame = new JFrame("My Frame");
        badd.addActionListener(frame);
    }
}
