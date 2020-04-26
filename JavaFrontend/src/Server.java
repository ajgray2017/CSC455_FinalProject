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
		System.out.println("What item would you like to ring in? Type done when done: ");
		String menuID = s.nextLine();
		
		while(true) {
			if (menuID.equals("")) {
				break;
			}else{
				checkStock(menuID);
				menuID = s.nextLine();
			}
			
		}
	}

	private void checkStock(String menuID) throws SQLException {
		// will place an order only if the item is in stock rollback if not
		try {
			conn.setAutoCommit(false);
			// nested query
			PreparedStatement pstmt = conn.prepareStatement(
					"select r1.itemID, r3.amount, r3.ingredientName from takesFrom as r1, menu as r2, stock as r3 where r1.menuID = r2.menuID and r1.menuID = (select menuID from menu where itemName = ?)");

			Statement stmt = conn.createStatement();

			pstmt.setString(1, menuID);

			ResultSet rset = pstmt.executeQuery();

			while (rset.next()) {
				
				stmt.executeUpdate("update stock set amount =" + (rset.getInt("amount") - 1) + " where itemID = "
						+ rset.getInt("itemID"));
			}
			conn.commit();
		} catch (SQLException e) {
			System.out.println("Item out of stock: rolling back....");
			conn.rollback();
		} finally {
			conn.setAutoCommit(true);
		}

	}

	private void ServerView() throws IOException {
		// this method will allow the server to view the tables that have their EID		
		
		try {
			Statement stmt = conn.createStatement();
			
			//attempt to make a new view for this server, if already exists catch and move on. Will automatically make views for every individual server.
			try {
				stmt.executeUpdate("create view server_"+eid+"_view as select orderID, tableNumber from custOrder where orderTakenEid = " + eid);
			}catch(SQLException e) {
			}
			ResultSet rset = stmt.executeQuery("select * from server_"+eid+"_view");
			while (rset.next()) {
				int orderID = rset.getInt("orderID");
				int tableNumber = rset.getInt("tableNumber");
				System.out.println("\torder: " + orderID + "\t" + "table: " + tableNumber);
			}
			System.out.println("");
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
				break;

			}
		}
	}
}
