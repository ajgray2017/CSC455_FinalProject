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
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("1. See all current orders");
		System.out.println("2. Complete an order");
		System.out.println("3. Logout");
		System.out.println("4. Help");
		
		return s.nextInt();
	}

	private String[] completeOrder() {
		String[] rslt = new String[2];
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();

			System.out.print("\tEnter OID to complete: ");
			String oid = s.nextLine();

			stmt.executeUpdate("update custOrder set orderPreparedEID = " + eid + " where orderID = " + oid + ";");

			ResultSet rset = stmt.executeQuery("select orderID as OID, orderPreparedEID as Cooks_EID from custOrder where orderID = " + oid + ";");

			while (rset.next()) {
				System.out.println("\tOID: "+rset.getInt("OID")+", EID: "+rset.getInt("Cooks_EID"));
			}

			s.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

		return rslt;

	}

	private String[] seeCurrentOrders() {
		String[] rslt = new String[2];
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery("select c.orderID, m.itemName from custOrder as c, menu as m, orderContains as o where o.menuID = m.menuID and c.orderID = o.orderID order by orderID;");
			
			System.out.println("\tOID: "+rset.getInt("orderID")); 

			while (rset.next()) {
				System.out.println("\t\tItem Name: "+rset.getString("itemName"));
			}

			s.close();

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

		return rslt;
	}

	public void start() {
		boolean working = true;

		while (working) {
			int option = get_option();
			switch (option) {
			case 1:
				seeCurrentOrders();
				break;
			case 2:
				completeOrder();
				break;
			case 3:
				working = false;
				break;
			case 4:
				break; // TODO add call to help method in jdbc

			}
		}
	}
}