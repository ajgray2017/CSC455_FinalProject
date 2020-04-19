/**
 * @author Adam Gray
 * @author Alex Czaus
 *
 */

import java.sql.*;
import java.io.Console;
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
  
    private static void use_database(Connection conn) {
    	//Use the specified connection object to interact with the database
        try {
        // Do something with the Connection
            Statement stmt = conn.createStatement();

            //Execute a query - which will return a result set
            System.out.println("\n********Before Update*******\n");
            ResultSet rset = stmt.executeQuery("SELECT * from instructor");

            //Iterate over the result set and process each tuple
            while (rset.next()) {
                    System.out.println(rset.getString(2) + "\t" + rset.getString("salary"));
            }

            //Execute an update - nothing returned
            stmt.executeUpdate("update instructor set salary = salary*1.05;");
            System.out.println("\n********Salaries updated*******\n");
            
            //Execute a query - which will return a result set
            rset = stmt.executeQuery("SELECT * from instructor");

            //Iterate over the result set and process each tuple
            while (rset.next()) {
                    System.out.println(rset.getString(2) + "\t" + rset.getString("salary"));
            }
            
            // Clean up
            rset.close();
            stmt.close();
            // Normally, you would close the connection when done
            // The connection is being left open in this example 
            // so that it does not have to re-established 
            //conn.close(); 

        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
    }
    private static void sql_inject(Connection conn) {
        try {
            // Create a statement object
            Statement stmt = conn.createStatement();
            Console c = System.console();
            
            System.out.println("SQL injection demo...");
            
            System.out.println("Enter instructor name for salary update: ");
            String name = c.readLine();
            
            //What happens if user enters this:  Einstein' or 'Y' = 'Y  ,exactly as shown
            
            String queryString = "update instructor set salary = salary*1.10 where name = '" + name + "'";
            System.out.println("Query is " + queryString);
            
            stmt.executeUpdate(queryString);
            
            System.out.println("Instructors and their salaries after update");
            //Execute a query using the user input - which will return a result set
            ResultSet rset = stmt.executeQuery("select * from instructor");
            
            //Iterate over the result set and process each tuple
            while (rset.next()) {
                    System.out.println(rset.getString(2) + "\t" + rset.getString("salary"));
                }
            }
        catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState:     " + e.getSQLState());
                System.out.println("VendorError:  " + e.getErrorCode());
            }
    }
    private static void prepared_statement(Connection conn) {
    	//Method demonstrates calling a stored procedure
        //Note: The stored procedure can also be called at the mysql prompt
        // using call CountProducts(4, @value), which returns (in @value) number of 
        // products with price > 4. The @ syntax defines a variable.
        // You can then select @value to show the result
        try {
            Console c = System.console();
            
            System.out.println("Prepared statement demo...");
            
            System.out.println("Enter instructor name: ");
            String name = c.readLine();
            
            //Say user enters this:  Einstein' or 'Y' = 'Y  ,exactly as shown
            
            PreparedStatement pStmt = conn.prepareStatement(
            "select * from instructor where name = ?");
            
            //Assign name to prepared statement placeholder 
            pStmt.setString(1, name); 
            System.out.println("Prepared statement is " + pStmt);
            
            //Execute a query using the user input - which will return a result set
            ResultSet rset = pStmt.executeQuery();
                        
            //Iterate over the result set and process each tuple
            while (rset.next()) {
                    System.out.println(rset.getString(2) + "\t" + rset.getString("salary"));
                }
            }
        catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState:     " + e.getSQLState());
                System.out.println("VendorError:  " + e.getErrorCode());
            }
    }

    private static void call_stored_procedure(Connection conn) {
        try {
            Console c = System.console();
            
            System.out.println("Stored procedure demo..");

            System.out.println("Enter price threshold: ");
            int threshold = Integer.parseInt(c.readLine());
            System.out.println("Threshold = "+threshold);
            
            CallableStatement cs = conn.prepareCall("{call CountProducts(?, ?)}");
            
            cs.setInt(1, threshold); //note that threshold is declared as an int
            cs.registerOutParameter(2, Types.INTEGER);
            
            //Execute the query associated with the callable statement
            cs.executeQuery();
            
            //Retrieve the result which is associated with the second 
            //parameter (the out parameter)
            int productCount = cs.getInt(2);
                        
            System.out.println("Number of products with price > "+threshold
              +" = "+productCount);
        }
        catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState:     " + e.getSQLState());
                System.out.println("VendorError:  " + e.getErrorCode());
            }
    }
    private static int get_option() {
        System.out.println("Choose option 1..5");
        System.out.println("\t1. Use database");
        System.out.println("\t2. sql inject");
        System.out.println("\t3. Prepared statement");
        System.out.println("\t4. Stored Procedure");
        System.out.println("\t5. Exit");
        
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
  
    public static void main(String[] args) {
        String database_name, username,password;
        
        database_name = "ajg8669";
        username = "ajg8669";
        password = "xjB6cq2I1";

        Connection conn = establish_connection(database_name, username, password);
        Scanner s = new Scanner(System.in);
        System.out.println("Enter in EID: ");
        String eid = s.nextLine();
        
        try {
        	Statement stmt = conn.createStatement();
        	ResultSet rset = stmt.executeQuery("select Position from employee where EID ='"+eid+"'");
        	while (rset.next()) {
                System.out.println(rset.getString(2));
        	}
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
        }
        
        
//        while (true) {
//            int option = get_option();
//            switch (option) {
//                case 1:
//                    use_database(conn);
//                    break;
//                case 2:
//                    sql_inject(conn);
//                    break;
//                case 3:
//                    prepared_statement(conn);
//                    break;
//                case 4:
//                    call_stored_procedure(conn);
//                    break;
//                case 5:
//                    System.exit(0);
//                    break;
//            }
//        }
    }
}

