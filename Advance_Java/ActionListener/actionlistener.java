import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class myframe extends JFrame implements ActionListener{
    static JFrame f;
    static JTextField l;
    public static void main(String args[]){
        JButton b1,b2;
        b1 = new JButton("1");
        b2 = new JButton("2");

        l = new JTextField(16);
        f = new JFrame();
        JPanel p = new JPanel();
        myframe fr = new myframe();
        
        b1.addActionListener(fr);
        b2.addActionListener(fr);

        p.add(l);
        p.add(b1);
        p.add(b2);
        
        f.add(p);

        f.setSize(200,200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if(s.charAt(0)=='1'){
            l.setText("Hello");
        }
        else{
            l.setText("Namaste");
        }
    }
}