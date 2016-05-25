package Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.Server;

public class Main {
	public static void main(String[] args) {
		Admin x = new Admin(500);

		try {
			Server server = new Server(x);
			Thread t = new Thread(server);
			t.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		x.run();
	}
}
