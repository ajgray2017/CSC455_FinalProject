import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Server {
	String eid;
	Connection conn;

	public Server(String eid, Connection conn) {
		this.eid = eid;
		this.conn = conn;
	}

	private static int get_option() {

		System.out.println("1. Place an order");
		System.out.println("2. See current orders");
		System.out.println("3. Get reciept");
		System.out.println("4. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private void createOrder() throws SQLException {
		Scanner s = new Scanner(System.in);
		Boolean inStock = false;
		Integer newID = null;
		s.useDelimiter(System.lineSeparator());

		System.out.println("Which Table?");
		String tableNum = s.nextLine();
		System.out.println("What item would you like to ring in? Type 'done' to finish ordering.");
		String itemName = s.nextLine();
		System.out.println("How many?");
		Integer qty = s.nextInt();
		s.nextLine();

		// todo add in trans here?
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select max(orderID) as newID from custOrder");
			if (rset.next()) {
				newID = rset.getInt("newID") + 1;
				stmt.executeUpdate(
						"insert into custOrder values (" + (newID) + ", " + eid + ", null, " + tableNum + ")");
			}
			rset.close();

			while (true) {
				if (itemName.startsWith("d")) {
					break;
				} else {
					for (int i = 0; i < qty; i++) {
						// bool instock if checkStock returns true, doesn't rollback changes
						inStock = checkStock(itemName);
					}
				}
				if (inStock) {
					stmt = conn.createStatement();
					rset = stmt.executeQuery("select menuID from menu where itemName = \"" + itemName + "\"");
					if (rset.next()) {
						stmt.executeUpdate("insert into orderContains values(" + rset.getInt("menuID") + ", " + newID
								+ ", " + qty + ")");
					}
				}

				System.out.println("Next item? Type 'done' to finish ordering.");
				itemName = s.nextLine();
				if (itemName.equals("done")) {
					break;
				} else {
					System.out.println("How many?");
					qty = s.nextInt();
					s.nextLine();
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}
	}

	private Boolean checkStock(String itemName) throws SQLException {
		// will place an order only if the item is in stock rollback if not
		try {
			conn.setAutoCommit(false);
			// nested query
			PreparedStatement pstmt = conn.prepareStatement(
					"select r1.itemID, r3.amount, r3.ingredientName from takesFrom as r1, menu as r2, stock as r3 where r1.menuID = r2.menuID and r1.menuID = (select menuID from menu where itemName = ?)");

			Statement stmt = conn.createStatement();

			pstmt.setString(1, itemName);

			ResultSet rset = pstmt.executeQuery();

			while (rset.next()) {
				stmt.executeUpdate("update stock set amount =" + (rset.getInt("amount") - 1) + " where itemID = "
						+ rset.getInt("itemID"));
			}
			conn.commit();
			rset.close();
		} catch (SQLException e) {
			System.out.println("Item out of stock: rolling back....");
			conn.rollback();
			conn.setAutoCommit(true);
			return false;
		} finally {
			conn.setAutoCommit(true);
		}
		return true;
	}

	private void ServerView() throws IOException {
		// this method will allow the server to view the tables that have their EID

		try {
			Statement stmt = conn.createStatement();

			// attempt to make a new view for this server, if already exists catch and move
			// on. Will automatically make views for every individual server.
			try {
				stmt.executeUpdate("create view server_" + eid
						+ "_view as select c.orderID, c.tableNumber, o.menuID, o.qty from custOrder as c, orderContains as o where c.orderID = o.orderID and c.orderTakenEid = "
						+ eid);
			} catch (SQLException e) {
			}
			ResultSet rset = stmt
					.executeQuery("select orderID, tableNumber from server_" + eid + "_view group by orderID");
			while (rset.next()) {
				System.out
						.println("\torder: " + rset.getInt("orderID") + "\t" + "table: " + rset.getInt("tableNumber"));
			}
			System.out.println("");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}
	}

	private void getReciept() throws IOException {

		Scanner s = new Scanner(System.in);

		System.out.println("\tWhich order is the reciept for?");
		// forces a view to be made for the user
		ServerView();

		String order = s.nextLine();
		float totalCost = 0;

		try {
			Statement stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery(
					"select t.menuID, t.itemName, t.qty, t.totalItem, t.totalCost from (select m.menuID, m.itemName, v.qty, v.qty * m.price as totalItem, sum(x.totalItemCost) as totalCost from (select v.qty * m.price as totalItemCost from menu as m, server_"
							+ eid + "_view as v where v.menuID = m.menuID and v.orderID = " + order
							+ ") as x, menu as m, server_" + eid
							+ "_view as v where v.menuID = m.menuID and v.orderID = " + order
							+ " group by m.menuID) as t group by t.menuID");

			System.out.println("\tReciept for " + order + ":");
			while (rset.next()) {
				System.out.println("\t\t" + rset.getInt("qty") + " " + rset.getString("itemName") + "....."
						+ rset.getFloat("totalItem"));
				totalCost = rset.getFloat("totalCost");
			}
			System.out.println("\t\tTotal....." + totalCost);

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
		}

	}

	public void start(Connection conn) throws IOException, SQLException {
		boolean working = true;

		while (working) {
			int option = get_option();
			switch (option) {
			case 1:
				createOrder();
				break;
			case 2:
				ServerView();
				break;
			case 3:
				getReciept();
				break;
			case 4:
				working = false;
				String[] args = null;
				JDBC.main(args);
				break;

			}
		}
	}
}
