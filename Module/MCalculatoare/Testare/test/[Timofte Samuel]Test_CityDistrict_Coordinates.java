package test;
import serv.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Scanner;
import serv.*;
import cityDistrict_coordinates.*;

public class Test_CityDistrict_Coordinates {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		client1 obj=new client1();
		int i=0;
		Connection con;
		con=Conect.conn();
		Statement stmt;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		int myint = keyboard.nextInt();
		
		while(i<myint){
			stmt=con.createStatement();
			String cmd="select oras,districtName from (select * from locations order by dbms_random.value()) where rownum=1";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			String city=rs.getString(1);
			String district=rs.getString(2);
			String send="{\"code\":1101,\"mesage\":\"corect\",\"data\":{\"city\":\""+city+"\",\"district\":\""+district+"\"}}";
			obj.test(send);
			i++;
		}
		keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		myint = keyboard.nextInt();
		i=0;
		while(i<myint){
			i++;
			SecureRandom random = new SecureRandom();
			String city=new BigInteger(130, random).toString(32);
			String district=new BigInteger(130, random).toString(32);
			String send="{\"code\":\"1101\",\"mesage\":\"corect\",\"data\":{\"city\":\""+city+"\",\"district\":\""+district+"\"}}";
			obj.test(send);
		}
		
	}

}
