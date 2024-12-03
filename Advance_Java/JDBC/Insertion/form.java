import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;


public class form extends JFrame implements ActionListener
{
    static JFrame f;
    static JTextField t1;
    static JTextField t2;
    static JTextField t3;
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    static PreparedStatement psmt;
    public static void main(String args[])throws Exception
    {
        f = new JFrame("my frame");
        t1 = new JTextField(16);
        t2 = new JTextField(16);
        t3 = new JTextField(16);
        form fo=new form();
        JButton b1,b2,b3;
        b1 = new JButton("prev");
        b2 = new JButton("add");
        b3 = new JButton("next");
        JPanel p = new JPanel();
        b1.addActionListener(fo);
        b2.addActionListener(fo);
        b3.addActionListener(fo);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        p.add(b1);
        p.add(b3);
        p.add(b2);
        f.add(p);
        f.setSize(200,220);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Class.forName("com.mysql.cj.jdbc.Driver");//.newInstance();
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/stud","root","root");
        stmt = con.createStatement();
        rs = stmt.executeQuery("select * from student");
        t1.setText(""+rs.getInt(1));
        t2.setText(""+rs.getString(2));
        t3.setText(""+rs.getString(3));
        con.close();
    }
    public void actionPerformed(ActionEvent e)
    {
        try{
        String s = e.getActionCommand();
        int id;
        String n,a;
        
        if (s.charAt(0)=='n'){
            rs.next();
            t1.setText(""+rs.getInt(1));
            t2.setText(""+rs.getString(2));
            t3.setText(""+rs.getString(3));
        }
        else if(s.charAt(0)=='a'){
            id = Integer.parseInt(t1.getText());
            n = t2.getText();
            a = t3.getText();
            psmt = con.prepareStatement("insert into emp values(?,?,?)");
            psmt.setInt(1,id);
            psmt.setString(2,n);
            psmt.setString(3,a);
            psmt.execute();
        }
        else{
            rs.previous();
            t1.setText(""+rs.getInt(1));
            t2.setText(""+rs.getString(2));
            t3.setText(""+rs.getString(3));
        }
    }catch(Exception ae) {}
    }
}