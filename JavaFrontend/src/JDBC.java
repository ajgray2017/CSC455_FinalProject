
/**
 * @author Adam Gray
 * @author Alex Czaus
 *
 */

import java.io.IOException;
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
    
    public static String[] get_position(Connection conn) {
    	String[] rslt = new String[3];
    	try {
    	// do queries here that will be protected by a prepared statement
    	Scanner s = new Scanner(System.in);
    	System.out.print("Enter EID: ");
    	String eid = s.nextLine();
    	
    	rslt[0] = eid;
    	
    	CallableStatement cs = conn.prepareCall("{call greeting("+eid+")}");
    	ResultSet rset = cs.executeQuery();
    	
    	while (rset.next()) {
    		rslt[1] = rset.getString("position");
    		rslt[2] = rset.getString("employeeName");
    	}
    	
    	} catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
    	
    	return rslt;
    }
    
    public void helper() {
    	
    }
  
    public static void main(String[] args) throws IOException, SQLException {
        String database_name, username,password;
        
        database_name = "ajg8669";
        username = "ajg8669";
        password = "xjB6cq2I1";

        Connection conn = establish_connection(database_name, username, password);
        
        String[] pos = get_position(conn);
        
        System.out.println("Welcome ("+pos[1]+"), "+pos[2]+"!");
        
        if (pos[1].startsWith("C")) {
        	Cook cook = new Cook(pos[0], conn);
        	cook.start();
        }
        
        else if (pos[1].startsWith("S")) {
        	Server server = new Server(pos[0], conn);
        	server.start(conn);
        }
    }
}
