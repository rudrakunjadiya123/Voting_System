package vote_system;

import java.sql.SQLException;
import java.util.Scanner;


public class Admin_Panel extends Admin{
    
    public boolean admin_panel(boolean s) throws ClassNotFoundException, SQLException{
        Scanner sc=new Scanner(System.in);
        int ch;
        
        while(true){
        System.out.println("\n1. All Result");
        System.out.println("2. percentage voting");
        System.out.println("3. Region wise result");
        System.out.println("4. Start-end Election");
        System.out.println("5. Add region");
        System.out.println("6. Remove region");
        System.out.println("7. Reset");
        System.out.println("8. Back");
        
        System.out.print("Enter Choice:");
        ch=sc.nextInt();
        switch(ch){
            case 1:
                all_result();
                break;
            case 2:
                percent_voting();
                break;
            case 3:
                Region_wise_result();
                break;
            case 4:
                boolean state=start_end_election(s);
                return state;
            case 5:
                add_region();
                break;
            case 6:
                remove_region();
                break;
            case 7:
                reset();
                break;
            case 8:
                return s;
        }
        }
    
    }   
}