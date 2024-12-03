import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class cal extends JFrame implements ActionListener{
    static JFrame f;
    static JTextField t1,t2,t3;
    public static void main(String args[]){
        f = new JFrame("My Frame");

        t1 = new JTextField(16);
        t2 = new JTextField(16);
        t3 = new JTextField(16);

        cal c = new cal();

        JButton b1,b2,b3,b4;
        b1 = new JButton("+");
        b2 = new JButton("-");
        b3 = new JButton("*");
        b4 = new JButton("/");

        b1.addActionListener(c);
        b2.addActionListener(c);
        b3.addActionListener(c);
        b4.addActionListener(c);

        JPanel p = new JPanel();
        p.add(t1);
        p.add(t2);
        p.add(b1);  p.add(b2);  p.add(b3);  p.add(b4);
        p.add(t3);

        f.add(p);

        f.setSize(200,200);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        int num1,num2,num3;
        num1 = Integer.parseInt(t1.getText());
        num2 = Integer.parseInt(t2.getText());
        if(s.charAt(0) == '+'){
            num3 = num1 + num2;
            t3.setText("" + num3);
        }
        else if(s.charAt(0) == '-'){
            num3 = num1 - num2;
            t3.setText("" + num3);
        }
        else if(s.charAt(0) == '*'){
            num3 = num1 * num2;
            t3.setText("" + num3);
        }
        else{
            num3 = num1 / num2;
            t3.setText("" + num3);
        }           
    }
}