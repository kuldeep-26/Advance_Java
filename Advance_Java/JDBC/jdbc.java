import java.sql.*;

public class jdbc{
    public static void main(String[] args){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stud","root","root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from student");
        while(rs.next()){
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
        }
        conn.close();
    }
    catch(Exception e){
        System.out.println("Error: " + e);
    }
    }
}