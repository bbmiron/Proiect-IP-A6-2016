package test;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.security.SecureRandom;
import java.math.BigInteger;
import serv.*;
import coordinates_cityState.*;
public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		client4 obj=new client4();
		int i=0;
		Connection con;
		con=Conect.conn();
		Statement stmt;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		int myint = keyboard.nextInt();
		while(i<myint){
			stmt=con.createStatement();
			String cmd="select longitudine_grade,longitudine_minute,longitudine_secunde,latitudine_grade,latitudine_minute,latitudine_secunde from (select * from locations order by dbms_random.value()) where rownum=1";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			int longGr = rs.getInt(1);
			int longMin=rs.getInt(2);
			int longSec=rs.getInt(3);
			int latGr=rs.getInt(4);
			int latMin=rs.getInt(5);
			int latSec=rs.getInt(6);
			String send="{\"code\":1101,\"mesage\":\"corect\",\"data\":{\"long_gr\":"+longGr+",\"long_min\":"+longMin+",\"long_sec\":"+longSec+",\"lat_gr\":"+latGr+",\"lat_min\":"+latMin+",\"lat_sec\":"+latSec+"}}";
			//System.out.println(send);
			obj.test(send);
			i++;
		}
		keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		myint = keyboard.nextInt();
		i=0;
		while(i<myint){
			i++;
			Random random = new Random();
			Random rand = new Random();
			int longGr=rand.nextInt(100);
			int longMin=rand.nextInt(100);
			int longSec=rand.nextInt(100);
			int latGr=rand.nextInt(100);
			int latMin=rand.nextInt(100);
			int latSec=rand.nextInt(100);
			String send="{\"code\":1101,\"mesage\":\"corect\",\"data\":{\"long_gr\":"+longGr+",\"long_min\":"+longMin+",\"long_sec\":"+longSec+",\"lat_gr\":"+latGr+",\"lat_min\":"+latMin+",\"lat_sec\":"+latSec+"}}";
			obj.test(send);
		}
		
	}

}