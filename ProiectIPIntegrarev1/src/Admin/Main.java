package Admin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Server.Server;

public class Main {

    public void setState() {

    }

	public static void main(String[] args) {

	    FctState p1 = new FctState(1, "1", "6790", "cityState", "weather", true);
        FctState p2 = new FctState(1, "2", "6791", "cityState", "weather", true);
        FctState p3 = new FctState(1, "3", "6794", "cityState", "weather", true);
        FctState p4 = new FctState(1, "4", "6797", "cityState", "weather", true);
        FctState p5 = new FctState(1, "5", "6800", "cityState", "weather", true);
        FctState p6 = new FctState(2, "1", "6787", "coordinates", "cityState", true);
        FctState p7 = new FctState(2, "2", "6792", "coordinates", "cityState", true);
        FctState p8 = new FctState(2, "3", "6795", "coordinates", "cityState", true);
        FctState p9 = new FctState(2, "4", "6798", "coordinates", "cityState", true);
        FctState p10 = new FctState(3, "1", "6788", "cityDistrict", "coordinates", true);
        FctState p11 = new FctState(3, "3", "6796", "cityDistrict", "coordinates", true);
        FctState p12 = new FctState(3, "5", "6801", "cityDistrict", "coordinates", true);
        FctState p13 = new FctState(4, "1", "6789", "cityState", "coordinates", true);
        FctState p14 = new FctState(4, "4", "6799", "cityState", "coordinates", true);

		List<FctState> fct1 =  new ArrayList<>();
		List<FctState> fct2 =  new ArrayList<>();
		List<FctState> fct3 =  new ArrayList<>();
		List<FctState> fct4 =  new ArrayList<>();
		fct1.add(p1);
		fct1.add(p2);
		fct1.add(p3);
		fct1.add(p4);
		fct1.add(p5);
		fct2.add(p6);
		fct2.add(p7);
		fct2.add(p8);
		fct2.add(p9);
		fct3.add(p10);
		fct3.add(p11);
		fct3.add(p12);
		fct4.add(p13);
		fct4.add(p14);
		Map<Integer, List<FctState>> list1 = new HashMap<>();
		Map<Integer, List<FctState>> list2 = new HashMap<>();
		Map<Integer, List<FctState>> list3 = new HashMap<>();
		list1.put(2, fct1);
		list1.put(0, fct3);
		list1.put(1, fct2);
		list2.put(0, fct4);
		list3.put(1, fct2);
		list3.put(0, fct3);
	    User gigi = new User(list1.size(), "101");
	    User ioana = new User(list2.size(), "103");
	    User andrei = new User(list3.size(), "207");
		Admin x = new Admin(500);
		
		try {
			Server server = new Server(x);
			Thread t = new Thread(server);
			t.start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		x.run();
		
		x.addInMap(gigi, list1);
		x.addInMap(ioana, list2);
		x.addInMap(andrei, list3);
	}

}
