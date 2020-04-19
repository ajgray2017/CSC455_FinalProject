import java.sql.*;
import java.util.Scanner;

public class Server {

	private static int get_option() {

		System.out.println("\t1. 1. Place an order");
		System.out.println("\t1. 2. See and edit your current orders");
		System.out.println("\t1. 3. Get reciept");
		System.out.println("\t1. 4. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	public static void main(Connection conn) {

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

	private static void placeOrder(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void ServerView(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void getReciept(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void logout(Connection conn) {
		// TODO Auto-generated method stub

	}
}