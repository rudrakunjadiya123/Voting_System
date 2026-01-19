package vote_system;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import vote_system.func;
import java.sql.SQLException;
import java.util.Scanner;


public class Party_Panel extends func{
    
    private void add_can(String party) throws ClassNotFoundException, SQLException{
        connection();
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter Candidate Name:");
        String name=sc.next();
        System.out.print("Enter Mobile no:");
        long mobile=sc.nextLong();
        int c=countDigit(mobile);
        if(c!=10){
            System.out.print("Wrong Mobile no & Try Again");
            return;
        }
        
        System.out.print("Enter Date of Birth(DD-MM-YYYY):");
        String dob=sc.next();
        
        if(!DOB_check(dob,false)){
            System.out.println("You are not 25+ or you entered wrong DOB format. Try Again");
            return;
        }
        
        System.out.print("Enter your Region:");
        String region=sc.next();
        
        if(!region_check(region)){
            System.out.println("You Entered Wrong Region Try Again");
            return;
        }
        
       
        String sql = "SELECT Party_Name FROM party_candidates WHERE Region_Area=? AND Party_Name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
        preparedStatement.setString(1, region);
        preparedStatement.setString(2, party);
                
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            System.out.println("Candidate is already exist in "+region+" region for your party");
            return;
        }
        
        sql = "INSERT INTO party_candidates(Party_Name,Can_Name,Can_mobile,Can_DOB,Region_Area,Votes) VALUES (?, ?, ?, ?, ?, ?)";
        preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, party);           
            preparedStatement.setString(2, name); 
            preparedStatement.setLong(3, mobile);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, region);
            preparedStatement.setInt(6, 0);
            
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(name+" registered Successfully for "+region+" region with "+party);
            }
            conn.close();
    }
    private void remove_can(String party) throws ClassNotFoundException, SQLException{
        connection();
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter your Region:");
        String region=sc.next();
        
        if(!region_check(region)){
            System.out.println("You Entered Wrong Region Try Again");
            return;
        }
        
        String sql = "SELECT Party_Name FROM party_candidates WHERE Region_Area=? AND Party_Name=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
        preparedStatement.setString(1, region);
        preparedStatement.setString(2, party);
                
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            sql = "DELETE FROM party_candidates WHERE Region_Area=? AND Party_Name=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, region);
            preparedStatement.setString(2, party);
            
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Your party Candidate deleted successfully for "+region+" region !");
            }
        }else{
            System.out.println("Candidate is not exist in "+region+" region for your party");
            return;
        }
        conn.close();
        
    }
    
    public void party_panel(String party) throws ClassNotFoundException, SQLException{
        Scanner sc=new Scanner(System.in);
        int ch;
        while(true){
        System.out.println("\n1. Add Candidate");
        System.out.println("2. Remove Candidate");
        System.out.println("3. Log out");
        
        System.out.print("Enter Choice:");
        ch=sc.nextInt();
        switch(ch){
            case 1:
                add_can(party);
                break;
            case 2:
                remove_can(party);
                break;
            case 3:
                return;
        }
        }
    
    }
}