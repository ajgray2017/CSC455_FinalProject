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

		System.out.println("\t1. 1. Place an order");
		System.out.println("\t1. 2. See and edit your current orders");
		System.out.println("\t1. 3. Get reciept");
		System.out.println("\t1. 4. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static void placeOrder(Connection conn) {
		// TODO Auto-generated method stub

	}

	private void ServerView(Connection conn) {
		// this method will allow the server to view the tables that have their EID
		Statement stmt = null;
		String query = "select * from tables where eid = " + eid;
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState:     " + e.getSQLState());
            System.out.println("VendorError:  " + e.getErrorCode());
		}

	}

	private static void getReciept(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void logout(Connection conn) {
		// TODO Auto-generated method stub

	}

	public static void start(Connection conn) {

		while (true) {
			int option = get_option();
			switch (option) {
			case 1:
				placeOrder(conn);
				break;
			case 2:
				ServerView(conn);
				break;
			case 3:
				getReciept(conn);
				break;
			case 4:
				logout(conn);
				break;

			}
		}
	}
}
