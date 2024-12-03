import javax.swing.*;
import java.awt.*;
public class Calculator 
{
    Calculator()
    {
        JButton b0 = new JButton("0");
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
        JButton b6 = new JButton("6");
        JButton b7 = new JButton("7");
        JButton b8 = new JButton("8");
        JButton b9 = new JButton("9");
        JButton badd = new JButton("+");
        JButton bsub = new JButton("-");
        JButton bmul = new JButton("*");
        JButton bdiv = new JButton("/");
        JButton bdot = new JButton(".");
        JButton bequal = new JButton("=");

        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(4,4,10,10));
        frame.add(b1);  frame.add(b2);  frame.add(b3);  frame.add(badd);
        frame.add(b4);  frame.add(b5);  frame.add(b6);  frame.add(bsub);
        frame.add(b7);  frame.add(b8);  frame.add(b9);  frame.add(bmul);
        frame.add(bdot);  frame.add(b0);  frame.add(bequal);  frame.add(bdiv);

        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }    
    public static void main(String[] args) 
    {
        new Calculator();    
    }
}
