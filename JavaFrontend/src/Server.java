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

	private static void placeOrder() {
		// TODO Auto-generated method stub

	}

	private static void ServerView() {
		// TODO Auto-generated method stub

	}

	private static void getReciept() {
		// TODO Auto-generated method stub

	}

	private static void logout() {
		// TODO Auto-generated method stub

	}

	public void start() {

		while (true) {
			int option = get_option();
			switch (option) {
			case 1:
				placeOrder();
				break;
			case 2:
				ServerView();
				break;
			case 3:
				getReciept();
				break;
			case 4:
				logout();
				break;

			}
		}
	}
}
