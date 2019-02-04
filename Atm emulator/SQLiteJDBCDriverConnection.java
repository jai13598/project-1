import java.sql.*;
import java.io.*;
import java.util.Scanner;
public class SQLiteJDBCDriverConnection {
     
     // Connect to a sample database
     
    public static void connect() {
        Scanner sc=new Scanner(System.in);
        Connection conn = null;
        Statement stmt = null;int flag=0;int cash1;
        try {
            // db parameters
            String url = "jdbc:sqlite:atm.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            String sql="";
            PreparedStatement pstmt  = conn.prepareStatement("SELECT * FROM AccountDetails where AccNo= ?");
           
            stmt = conn.createStatement();
            

            System.out.println("         HELLO \n\n PLEASE ENTER YOUR ACCOUNT NO.");
            int accno=sc.nextInt();
            System.out.println("ENTER YOUR PIN");
            int pin=sc.nextInt();
            pstmt.setInt(1,accno);
            ResultSet rs = pstmt.executeQuery();
            
            
         int id = rs.getInt("PIN");
                if(id==pin)
               {
                 flag=1;
                  String name=rs.getString("Name");
                  System.out.println("WELCOME MR."+name);
                  System.out.println("ENTER WITHDRAW AMOUNT: ");
                  int wa=sc.nextInt();
                  int Amount=rs.getInt("Balance");
              ResultSet rs2 = stmt.executeQuery( "SELECT cash from atmcash where id=1;" );
            
         cash1 = rs2.getInt("cash");
               if(cash1<wa){
                      System.out.println("NOT ENOUGH CASH IN ATM");
                    }
        


                 if(wa%50==0 && wa<=cash1)
                 {
                     if(wa>Amount)
                    {
                        System.out.println("INSUFFICIENT BALANCE");
                        
                    }
                    else{                    

                    int twoth=wa/2000;
                    int fiveh=(wa-(twoth*2000))/500;
                    int hun=(wa-(twoth*2000 + fiveh*500))/100;
                    int fifty=(wa-(twoth*2000 + fiveh*500 +hun*100))/50;
                    System.out.println("PRESS 1 TO PRINT RECEPIT OR 0 EXIT");
                    int ch1=sc.nextInt();
                    if(ch1==1)
                     {
                      System.out.println("2000 x"+twoth+"= "+(2000*twoth));
                      System.out.println("500 x"+fiveh+" = "+(fiveh*500));
                      System.out.println("100 x"+hun+"   = "+(100*hun));
                      System.out.println("50 x"+fifty+"= "+(50*fifty));
                     }
                     int remaining=Amount-wa;
                     System.out.println("TOTAL AMOUNT = "+wa);
                     System.out.println("REMAINING BALANCE= "+remaining);
                     System.out.println("THANK YOU FOR USING RCPL BANK");
                     
                    PreparedStatement pstmt1  = conn.prepareStatement("Update AccountDetails set Balance = ? where AccNo= ?");
                      pstmt1.setInt(1,remaining);
                      pstmt1.setInt(2,accno);
                      pstmt1.executeUpdate();

                      int cashinatm=cash1-wa;
                      PreparedStatement pstmt2  = conn.prepareStatement("Update atmcash set cash = ? where id= 1");
                      pstmt2.setInt(1,cashinatm);
                     
                      pstmt2.executeUpdate();
                      
                      }                        
                }
                else{

                      System.out.println("ENTER VALID AMOUNT");
                     }


}
      
         System.out.println();
      
      if(flag==0)
      System.out.println("WRONG CREDENTIALS");
      rs.close();
      stmt.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            
        }
    }
    
    public static void main(String[] args) {
     System.out.println("\n\n----WELCOME TO RCPL BANK ----\n");
     
        connect();

    }
}