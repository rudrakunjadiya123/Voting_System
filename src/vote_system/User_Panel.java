package vote_system;
import vote_system.func;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.*;

public class User_Panel extends func {

    private void register() throws ClassNotFoundException, SQLException{
        
        connection();
        Scanner sc=new Scanner(System.in);
        long adhar;
        
        System.out.print("Enter Adhar ID:");
        adhar=sc.nextLong();
        int c=countDigit(adhar);
        
        if(c!=12){
            System.out.print("Wrong Adhar ID & Try Again");
            return;
        }
        
        Statement statement = conn.createStatement();
        String sql = "SELECT Adhar_ID FROM user";
        ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                long id = rs.getLong("Adhar_ID");
                if(id==c){
                    System.out.println("Adhar ID is Already exits");
                    return;
                }
            }
        
        System.out.print("Enter Name:");
        String name=sc.next();
        
        System.out.print("Enter Mobile no:");
        long mobile=sc.nextLong();
        c=countDigit(mobile);
        
        if(c!=10){
            System.out.println("Wrong Mobile No & Try agian");
            return;
        }
        
        System.out.print("Enter Date of Birth(DD-MM-YYYY):");
        String dob=sc.next();
        
        if(!DOB_check(dob,true)){
            System.out.println("You are not 18+ or you entered wrong DOB format. Try Again");
            return;
        }
        
        System.out.print("Enter your Region:");
        String region=sc.next();
        
        if(!region_check(region)){
            System.out.println("You Entered Wrong Region");
            return;
        }
        
        sql = "INSERT INTO user(Adhar_ID,Name,Mobile,DOB,RegionArea,Voting) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setLong(1, adhar);           
            preparedStatement.setString(2, name); 
            preparedStatement.setLong(3, mobile);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, region);
            preparedStatement.setString(6, "NO");
            
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("You are registered Successfully!!");
            }
            conn.close();
        
    }
    private void login(boolean state) throws ClassNotFoundException, SQLException{
        connection();
        if(state){
            Scanner sc=new Scanner(System.in);
   
            System.out.print("Enter Adhar ID:");
            long adhar=sc.nextLong();
            int c=countDigit(adhar);
        
            if(c!=12){
                System.out.print("Wrong Adhar ID & Try Again");
                return;
            }
           
            String double_vote = "SELECT Voting FROM user WHERE Adhar_ID=?";
            PreparedStatement ps = conn.prepareStatement(double_vote);
        
            ps.setLong(1, adhar);
            ResultSet rsltset = ps.executeQuery();
            
            if(rsltset.next()){
                String dup=rsltset.getString("Voting");
                if(dup.equals("YES")){
                    System.out.println("You already Voted");
                    return;
                }
            }
            
            String sql = "SELECT RegionArea FROM user WHERE Adhar_ID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
            preparedStatement.setLong(1, adhar);
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                String region=rs.getString("RegionArea");
                sql = "SELECT Party_Name,Can_Name FROM party_candidates WHERE Region_Area=?";
                preparedStatement = conn.prepareStatement(sql);
        
                preparedStatement.setString(1, region);
                
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next()){
                    System.out.println(resultset.getString("Party_Name")+"   "+resultset.getString("Can_Name"));
                    
                }
                System.out.print("Enter Party Name:");
                String party=sc.next();       
                if(party_check(party,region)){
                   
                    
                    int vote=vote_count(party,region);
                    
                    sql = "UPDATE party_candidates SET votes=? WHERE Party_Name=? AND Region_Area=?";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1, ++vote);
                    preparedStatement.setString(2, party);
                    preparedStatement.setString(3, region);
                    preparedStatement.executeUpdate();
                    
                    sql="UPDATE user SET Voting=? WHERE Adhar_ID=?";
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, "YES");
                    preparedStatement.setLong(2, adhar);
                    
                    int rowsUpdated=preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("You successfully voted for "+party+" party in "+region+" region");
                    } 
                }else{
                    System.out.println("Invalid Party Name");
                }
                
                
            }else{System.out.println("Adhar ID not exist");}
        }else{
            System.out.println("Election has not started");
        }
        conn.close();
    }
    
    public void user_panel(boolean state) throws ClassNotFoundException, SQLException{
        Scanner sc=new Scanner(System.in);
        int ch;
        
        while(true){
        System.out.println("\n1. Register");
        System.out.println("2. Login & Vote");
        System.out.println("3. Back");
        
        System.out.print("Enter Choice:");
        ch=sc.nextInt();
        switch(ch){
            case 1:
                register();
                break;
            case 2:
                login(state);
                break;
            case 3:
                return;
        }
        }
    
    }
}