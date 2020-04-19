import java.sql.*;
import java.util.Scanner;

public class Cook {
	String eid;
	Connection conn;

	public Cook(String eid, Connection conn) {
		this.eid = eid;
		this.conn = conn;
	}

	private int get_option() {

		System.out.println("\t1. See all current orders");
		System.out.println("\t2. Complete an order");
		System.out.println("\t3. Logout");
		System.out.println("\t4. Help");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private String[] completeOrder() {
		String[] rslt = new String[2];
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();
			
			System.out.print("Enter OID to complete: ");
			String oid = s.nextLine();

			stmt.executeUpdate("update custOrder set orderPreparedEID = "+eid+" where orderID = "+oid+";");

			ResultSet rset = stmt.executeQuery("select * from custOrder where orderID = "+oid+";");

			while (rset.next()) {
				System.out.println(rset.next());
			}
			
		s.close();
		
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

		return rslt;

	}

	private void logout() {
		// TODO Auto-generated method stub

	}

	private void seeCurrentOrders() {
		// TODO Auto-generated method stub

	}

	public void start() {

		while (true) {
			int option = get_option();
			switch (option) {
			case 1:
				seeCurrentOrders();
			case 2:
				completeOrder();
			case 3:
				logout();
			case 4:
				break; // TODO add call to help method in jdbc

			}
		}
	}
}