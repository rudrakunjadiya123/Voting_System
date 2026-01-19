package vote_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.time.format.*;

class func {
    
    protected int countDigit(long n){
        return (int)Math.floor(Math.log10(n) + 1);
    }
    
    protected Connection conn;
    protected void connection() throws ClassNotFoundException, SQLException{
        
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/voting", "root", "Hello@123");
         
    }
   
    
    protected boolean DOB_check(String bd,boolean b){
        DateTimeFormatter pattern=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try{
            LocalDate dob=LocalDate.parse(bd,pattern);
            LocalDate today=LocalDate.now();
            Period age=Period.between(dob,today);
            if(age.getYears()>=18 && age.getDays()>0 && b) return true;
            if(age.getYears()>=25 && age.getDays()>0 && !b) return true;
        } 
        catch(Exception e){
            return false;
        }
        return false;
    }
    
    protected boolean region_check(String reg) throws ClassNotFoundException, SQLException{
        connection();
        Statement statement = conn.createStatement();
        
        String sql = "SELECT Region_Area FROM region";
        ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String r = rs.getString("Region_Area");
                
                if(r.equals(reg)){ return true; }
            }
            statement.close();
            conn.close();
            return false;
    }
    
    protected boolean party_check(String party,String region) throws SQLException, ClassNotFoundException{
            connection();
            
            String sql = "SELECT Party_Name FROM party_candidates WHERE Region_Area=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            
            preparedStatement.setString(1, region);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                String p=rs.getString("Party_Name");
                if(party.equals(p)){ return true; }
            }
            conn.close();
            return false;
    }
    
    protected int vote_count(String party,String region) throws SQLException, ClassNotFoundException{
            connection();
            String sql = "SELECT Votes FROM party_candidates WHERE Party_Name=? AND Region_Area=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
        
            preparedStatement.setString(1, party);
            preparedStatement.setString(2, region);

            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                int v=rs.getInt("Votes");
                return v;
            }
        conn.close();
        return 0;
    }
}