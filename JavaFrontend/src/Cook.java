import java.sql.*;
import java.util.Scanner;

public class Cook {
	static String eid;
	static Connection conn;

	public Cook(String eid, Connection conn) {
		Cook.eid = eid;
		Cook.conn = conn;
	}

	private static int get_option() {

		System.out.println("\t0. Type 'help' for assistance");

		System.out.println("\t1. 1. See all current orders");
		System.out.println("\t2. 2. Complete an order");
		System.out.println("\t3. 3. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static String[] completeOrder() {
		String[] rslt = new String[2];
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();
			
			System.out.print("Enter OID to complete: ");
			String oid = s.nextLine();

			stmt.executeUpdate("update custOrder set orderPreparedEID = "+eid+" where orderID = "+oid+";");

			ResultSet rset = stmt.executeQuery("SELECT * from instructor");

			// Iterate over the result set and process each tuple
			while (rset.next()) {
				System.out.println(rset.getString(2) + "\t" + rset.getString("salary"));
			}
			
		s.close();
		
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

		return rslt;

	}

	private static void logout() {
		// TODO Auto-generated method stub

	}

	private static void seeCurrentOrders() {
		// TODO Auto-generated method stub

	}

	public void start() {

		while (true) {
			int option = get_option();
			switch (option) {
			case 0:
				break; // TODO add call to help method in jdbc
			case 1:
				seeCurrentOrders();
			case 2:
				completeOrder();
			case 3:
				logout();

			}
		}
	}
}