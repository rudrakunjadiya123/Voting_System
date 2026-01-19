
package vote_system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


class Admin extends func{
    
    protected void add_region() throws ClassNotFoundException, SQLException{
       Scanner sc=new Scanner(System.in);
        System.out.print("Enter Region:");
        String region=sc.next();
        
        func obj=new func();
        if(!obj.region_check(region)){
            String sql = "INSERT INTO region(Region_Area) VALUES (?)";
            connection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, region);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Region Inserted Successfully!!");
            }
            sql = "INSERT INTO party_candidates(Party_Name,Can_Name,Can_mobile,Can_DOB,Region_Area,Votes) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, "NOTA");           
            preparedStatement.setString(2, "_"); 
            preparedStatement.setLong(3, 0);
            preparedStatement.setString(4, "_");
            preparedStatement.setString(5, region);
            preparedStatement.setInt(6, 0);
            
            preparedStatement.executeUpdate();
            
        }else{
            System.out.println("Region is already exist");
            return;
        }
        conn.close();
    }
    
    protected void remove_region() throws ClassNotFoundException, SQLException{
       connection();
       Scanner sc=new Scanner(System.in);
       System.out.print("Enter Region:");
       String region=sc.next();
        
        func obj=new func();
        if(obj.region_check(region)){
            String sql = "DELETE FROM region WHERE Region_Area=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1, region);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(region+"Region deleted successfully");
            }
        }else{
            System.out.println(region+"Region is not exist");
            return;
        }
        conn.close();
    }
    
    protected boolean start_end_election(boolean state){
        Scanner sc=new Scanner(System.in);
        
        if(state){
            System.out.println("To End elction(yes/no): ");
            String s=sc.next();
            if(s.equals("yes")){state=false; System.out.println("Election ended!");}
            else{System.out.println("Invalid input..");}
        }else{
            System.out.println("To Start elction(yes/no): ");
            String s=sc.next();
            if(s.equals("yes")){state=true; System.out.println("Election started!");}
            else{System.out.println("Invalid input..");}
        }
        
        return state;
    }
    
    protected void Region_wise_result() throws ClassNotFoundException, SQLException{
        connection();
        Statement statement = conn.createStatement();
        String sql = "SELECT Region_Area FROM region";
        ResultSet rs = statement.executeQuery(sql);
        

            while (rs.next()) {
                String r=rs.getString("Region_Area");
                String sq = "SELECT Party_Name,Can_Name,Votes FROM party_candidates WHERE Region_Area=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sq);

                preparedStatement.setString(1, r);
                ResultSet rslt = preparedStatement.executeQuery();
                
                System.out.println("\nRegion:- "+r);
                
                while(rslt.next()){
                    String party=rslt.getString("Party_Name");
                    String canname=rslt.getString("Can_Name");
                    String votes=rslt.getString("Votes");
                    
                    System.out.println(canname+"  "+party+"  "+votes);
                    
                    
                }
                
                String max_query="SELECT Party_Name FROM party_candidates WHERE Votes = (SELECT MAX(Votes) FROM party_candidates WHERE Region_Area = ?) AND Region_Area=?";
                
                PreparedStatement prestate = conn.prepareStatement(max_query);
                prestate.setString(1, r);
                prestate.setString(2, r);
                ResultSet resultset=prestate.executeQuery();
                    
                    if(resultset.next()){
                        String p=resultset.getString("Party_Name");
                        System.out.println("Won: "+p);
                        String seat_query="UPDATE party SET Seats = Seats+1 WHERE Party_Name = ?";
                        PreparedStatement preparedStat = conn.prepareStatement(seat_query);
                        preparedStat.setString(1, p);
                        preparedStat.executeUpdate();
                        
                    }
            }
            conn.close();
    }
    
    protected void all_result() throws ClassNotFoundException, SQLException{
        connection();
        Statement statement = conn.createStatement();
        String sql = "SELECT Party_Name,Seats FROM party";
        ResultSet rs = statement.executeQuery(sql);
        
        while(rs.next()){
            String party=rs.getString("Party_Name");
            String seat=rs.getString("Seats");
            System.out.println(party+"  "+seat);
            
        }
        
        String max_query="SELECT Party_Name FROM party WHERE Seats = (SELECT MAX(Seats) FROM party)";
        PreparedStatement prestate = conn.prepareStatement(max_query);
        ResultSet resultset=prestate.executeQuery();
        if(resultset.next()){
            System.out.println("Won: "+resultset.getString("Party_Name"));
        }
        conn.close();
    }
    
    protected void reset() throws ClassNotFoundException, SQLException{
        connection();
        String sql = "DELETE FROM region";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        
        sql = "DELETE FROM party_candidates";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
        
        sql = "UPDATE party SET Seats = ?";
        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, 0);
        preparedStatement.executeUpdate();
        
        sql = "UPDATE user SET Voting = ?";
        preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, "NO");
        preparedStatement.executeUpdate();
        
        System.out.println("Reset Successfully!!");
        conn.close();
    }
    
    protected void percent_voting() throws ClassNotFoundException, SQLException{
        connection();
        Statement statement = conn.createStatement();
        String sql = "SELECT Region_Area FROM region";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            String r=rs.getString("Region_Area");
            
            String count_query="SELECT COUNT(*) AS count FROM user WHERE RegionArea=? ";
            PreparedStatement preparedSt = conn.prepareStatement(count_query);

            preparedSt.setString(1, r);
            ResultSet count_set=preparedSt.executeQuery();
            int count=0;
            if(count_set.next()){
                count=count_set.getInt("count");
            }
            
            String yes_query="SELECT COUNT(*) AS count FROM user WHERE RegionArea=? AND Voting=? ";
            PreparedStatement pSt = conn.prepareStatement(yes_query);

            pSt.setString(1, r);
            pSt.setString(2, "YES");
            
            ResultSet vote_done_set=pSt.executeQuery();
            int vote_done_count=0;
            if(vote_done_set.next()){
                vote_done_count=vote_done_set.getInt("count");
            }
            
            if(count==0){ System.out.println(r+"  ->  "+"0 user"); }
            else{
                double p=(vote_done_count*100)/count;
                System.out.println(r+"  ->  "+p+"%");
            }
        }
        conn.close();
    }
}
