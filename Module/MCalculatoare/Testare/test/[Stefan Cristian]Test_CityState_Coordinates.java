package test;
import serv.*;
import java.sql.*;
import java.util.Scanner;
import java.security.SecureRandom;
import java.math.BigInteger;

import cityDistrict_coordinates.*;

public class Test_CityState_Coordinates {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		client2 obj=new client2();
		int i=0;
		Connection con;
		con=Conect.conn();
		Statement stmt;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter an integer");
		int myint = keyboard.nextInt();
		while(i<myint){
			stmt=con.createStatement();
			String cmd="select oras,stat from (select * from locations order by dbms_random.value()) where rownum=1";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			String city=rs.getString(1);
			String state=rs.getString(2);
			String send="{\"code\":1101,\"mesage\":\"corect\",\"data\":{\"city\":\""+city+"\",\"state\":\""+state+"\"}}";
			
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
			SecureRandom random = new SecureRandom();
			String city=new BigInteger(130, random).toString(32);
			String state=new BigInteger(130, random).toString(32);
			String send="{\"code\":\"1101\",\"mesage\":\"corect\",\"data\":{\"city\":\""+city+"\",\"state\":\""+state+"\"}}";
			obj.test(send);
		}
		
	}

}
