import java.sql.*;
import java.util.Scanner;

public class Cook {
	String eid;
	Connection conn;
	
	public Cook(String eid, Connection conn) {
		this.eid = eid;
		this.conn = conn;
	}

	private static int get_option() {

		System.out.println("\t1. 1. See all current orders");
		System.out.println("\t1. 2. Complete an order");
		System.out.println("\t1. 3. Logout");

		Scanner s = new Scanner(System.in);
		return s.nextInt();
	}

	private static void completeOrder(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void logout(Connection conn) {
		// TODO Auto-generated method stub

	}

	private static void seeCurrentOrders(Connection conn) {
		// TODO Auto-generated method stub

	}

	public static void main(Connection conn) {

		while (true) {
			int option = get_option();
			switch (option) {
			case 1:
				seeCurrentOrders(conn);
				break;
			case 2:
				completeOrder(conn);
				break;
			case 3:
				logout(conn);
				break;

			}
		}
	}
}