
public class Cook {

	public Cook() {
		while (true) {
          int option = get_option();
          switch (option) {
              case 1:
                  use_database(conn);
                  break;
              case 2:
                  sql_inject(conn);
                  break;
              case 3:
                  prepared_statement(conn);
                  break;
              case 4:
                  call_stored_procedure(conn);
                  break;
              case 5:
                  System.exit(0);
                  break;
	}

}
