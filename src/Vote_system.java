import vote_system.User_Panel;
import vote_system.Admin_Panel;
import vote_system.Party_Panel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

class data{
    protected boolean state=false;
    protected String username,pass;
}

public class Vote_system extends data {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        Scanner sc=new Scanner(System.in);
        int ch;
        
        Vote_system v=new Vote_system();
        
        System.out.println("\t\t\t\t****Voting System****");
        while(true){
        System.out.println("\n\n1. User Panel");
        System.out.println("2. Party Panel");
        System.out.println("3. Admin Panel");
        System.out.println("4. Exit");
        
        System.out.print("Enter Choice:");
        ch=sc.nextInt();
        switch(ch){
            case 1:
                User_Panel obj=new User_Panel();                
                obj.user_panel(v.state);
                break;
                
            case 2:
                if(!v.state){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/voting", "root", "Hello@123");
                
                String sql = "SELECT PartyName FROM party_represent WHERE Username=? AND Pass=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                
                System.out.print("Enter Username: ");
                v.username=sc.next();
                System.out.print("Enter Password: ");
                v.pass=sc.next();
                
                
                preparedStatement.setString(1, v.username);
                preparedStatement.setString(2, v.pass);
                
                ResultSet rs = preparedStatement.executeQuery();

                if(rs.next()) {
                    String party = rs.getString("PartyName");
                    Party_Panel p=new Party_Panel();
                    p.party_panel(party);
                }else{ System.out.println("Wrong username or password"); }
                
                preparedStatement.close();
                conn.close();
                }else{
                    System.out.println("Elction on running So you can't login");
                }
                break;
               
                
            case 3:
                System.out.print("Enter Username: ");
                v.username=sc.next();
                System.out.print("Enter Password: ");
                v.pass=sc.next();
                String u=v.username;
                String p=v.pass;
                 if (u.equals("admin") && p.equals("admin123")) {
                    Admin_Panel a=new Admin_Panel();
                    
                    v.state=a.admin_panel(v.state);
                 }else{
                     System.out.println("Wrong Username or password.");
                 }
                 break;
                 
            case 4:
                 System.exit(0);
                 break;
            default: System.out.println("Wrong choice!!");
        }
        }
    
    }
}
