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

		System.out.println("\t1. Place an order");
		System.out.println("\t2. See and edit your current orders");
		System.out.println("\t3. Get reciept");
		System.out.println("\t4. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static void placeOrder(Connection conn) throws SQLException {
		// will place an order only if the item is in stock rollback if not
		try {
			conn.setAutoCommit(false);
			Scanner s = new Scanner(System.in);
			System.out.println("What item would you like to ring in? ");
			PreparedStatement pstmt = conn.prepareStatement("");
			
			
			
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
		} finally {
			conn.setAutoCommit(true);
		}

	}

	private void ServerView(Connection conn) throws IOException {
		// this method will allow the server to view the tables that have their EID
		Statement stmt = null;
		String query = "select orderID, tableNumber from custOrder where orderTakenEid = " + eid;
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				int orderID = rset.getInt("orderID");
				int tableNumber = rset.getInt("tableNumber");
				System.out.println("order: " + orderID + "\t" +
						"table: " + tableNumber);
				System.in.read();
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
		}
	}

	private static void getReciept() {
		// TODO Auto-generated method stub

	}
	
	public void start(Connection conn) throws IOException, SQLException {
		boolean working = true;

		while (working) {
			int option = get_option();
			switch (option) {
			case 1:
				placeOrder(conn);
				break;
			case 2:
				ServerView(conn);
				break;
			case 3:
				getReciept();
				break;
			case 4:
				working = false;
				break;

			}
		}
	}
}
