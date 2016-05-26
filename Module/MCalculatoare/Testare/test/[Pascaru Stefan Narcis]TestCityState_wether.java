package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import cityState_wether.Conect;
import serv.*;

public class TestCityState_wether {
	public static Connection con;
	public static void main(String[] args) {
		client3 c30=new client3();
		if(con==null)
			con=Conect.conn();
		Statement st;
		String oras=new String();
		String judet=new String();
		
		String[] args1 =new String[1];
		
		@SuppressWarnings("resource")
		Scanner scan=new Scanner(System.in);
		System.out.println("Cate verificari vrei sa faci? Introdu un numar:");
		int count=scan.nextInt();
		for(int i=0;i<count;i++){
			try {
				st = con.createStatement();
				String cmd="select oras,stat from (select * from locations order by dbms_random.value()) where rownum=1";
				ResultSet rs=st.executeQuery(cmd);
				rs.next();
				oras=rs.getString(1);
				judet=rs.getString(2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				args1[0]="{\"code\":\"12\",\"message\":\"sgdsfd\",\"data\":{\"city\":\""+
						oras+"\",\"state\":\""+judet+"\"}}";
		//{"code":12,"message":"sgdsfd","data":{"city":"iasi","state":"iasi"}}
				c30.main(args1);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		//testare pentru orase care nu exista
		Random rand = new Random();
		for(int i=0;i<count;i++){
			oras="oras"+rand.nextInt(50) + 1;
			try {
				args1[0]="{\"code\":\"12\",\"message\":\"sgdsfd\",\"data\":{\"city\":\""+
						oras+"\",\"state\":\""+judet+"\"}}";
		//{"code":12,"message":"sgdsfd","data":{"city":"iasi","state":"iasi"}}
				c30.main(args1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
