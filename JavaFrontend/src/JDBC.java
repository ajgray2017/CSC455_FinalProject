/**
 * @author Adam Gray
 * @author Alex Czaus
 *
 */

import java.sql.*;
import java.util.Scanner;

public class JDBC
{
       
    public static Connection establish_connection(String database_name, String sql_username, String sql_passwd) {
    //Establish a connection with specified database. Return connection object
        Connection conn = null;
        try {
            
            System.out.println("Establishing connection with MySql server on satoshi..");
            conn = DriverManager.getConnection(
            "jdbc:mysql://152.20.12.152/"+database_name+"?noAccessToProcedureBodies=true"+"&useSSL=false"+"&user="+sql_username+"&password="+sql_passwd);
            
            System.out.println("Connection with MySql server on satoshi.cis.uncw.edu established.");
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
        
        return conn;
    }
    
    public static String get_position(Connection conn) {
    	String rslt = null;
    	try {
    	// do queries here that will be protected by a prepared statement
    	Scanner s = new Scanner(System.in);
    	System.out.print("Enter EID: ");
    	String eid = s.nextLine();
    	
    	PreparedStatement pStmt = conn.prepareStatement(
    			"select position from employee where eid = ?");
    	
    	pStmt.setString(1, eid);
    	ResultSet rset = pStmt.executeQuery();
    	
    	while (rset.next()) {
    		rslt = rset.getString(1);
    	}
    	
    	} catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
    	
    	return rslt;
    }
  
    public static void main(String[] args) {
        String database_name, username,password;
        
        database_name = "ajg8669";
        username = "ajg8669";
        password = "xjB6cq2I1";

        Connection conn = establish_connection(database_name, username, password);
        
        String pos = get_position(conn);
        
        System.out.println(pos);
    }
}

