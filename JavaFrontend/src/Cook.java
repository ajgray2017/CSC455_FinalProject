import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Cook {
	String eid;
	Connection conn;

	public Cook(String eid, Connection conn) {
		this.eid = eid;
		this.conn = conn;
	}

	private int getOption() {
		/* used to print out the menu, and receive the users input */

		Scanner s = new Scanner(System.in);

		System.out.println("1. See all current orders");
		System.out.println("2. Complete an order");
		System.out.println("3. Logout");

		return s.nextInt();
	}

	private void completeOrder() {
		/**/
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();

			System.out.print("\tEnter OID to complete: ");
			String oid = s.nextLine();

			stmt.executeUpdate("update custOrder set orderPreparedEID = " + eid + " where orderID = " + oid + ";");

			ResultSet rset = stmt.executeQuery(
					"select orderID as OID, orderPreparedEID as Cooks_EID from custOrder where orderID = " + oid + ";");

			while (rset.next()) {
				System.out.println("\tOID: " + rset.getInt("OID") + ", EID: " + rset.getInt("Cooks_EID") + "\n");
			}

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

	}

	private void seeCurrentOrders() {
		/*
		 * Used to complete an order, searches the DB for a null value in the
		 * OrderPreparedEID in the custOrder table, denoting that the order has not yet
		 * been prepared
		 */
		try {
			Scanner s = new Scanner(System.in);
			Statement stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery(
					"select c.orderID, m.itemName, o.qty from custOrder as c, menu as m, orderContains as o "
							+ "where c.orderPreparedEID is null and o.menuID = m.menuID and c.orderID = o.orderID order by orderID;");

			if (rset.next()) {

				while (rset.next()) {
					System.out.println("\tOID: " + rset.getInt("orderID") + " ... Item Name: " + rset.getString("itemName") + " ... QTY: " + rset.getString("qty") + "\n");
				}
			} else {
				System.out.println("\tNo Current Orders\n");
			}

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

	}

	public void start() throws IOException, SQLException {
		boolean working = true;

		while (working) {
			int option = getOption();
			switch (option) {
			case 1:
				seeCurrentOrders();
				break;
			case 2:
				completeOrder();
				break;
			case 3:
				working = false;
				String[] args = null;
				JDBC.main(args);
				break;

			}
		}
	}
}