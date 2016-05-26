package city_population;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cityState_coordinates.CityState_Coordinates;
import cityState_coordinates.Conect;
import cityState_coordinates.Coordinates;

public class City_Population {
	private static Connection con;
	public int code=0;
	public String message=new String();
	public String city=new String();
	public String state=new String();
	public int population=0;
	public boolean preCheck(String city,String state){
		if(city==null){
			code=4105;
			message="Completati toate campurile";
			return false;
		}
		if(con==null)
			con=Conect.conn();
		Statement stmt;
		try{
			stmt=con.createStatement();
			String cmd="select count(*) from locations where oras=lower('"+city+"')";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			int aux=rs.getInt(1);
			if(aux==0){
				code=4101;
				message="Locatia nu exista in baza de date";
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			code=4101;
			message="Server error on SQL";
	
		}
		return true;
	}
	public boolean check(){
		if(population>10000000||population<0){
			code=4106;
			message="greseala de calcul";
			return false;}
		return true;
	}
	public String calculeaza(String ob) {
		JSONObject ret = new JSONObject();
	    JSONObject retFinal = new JSONObject();
		if(!ob.contains("data")||!ob.contains("city")){
			retFinal.put("code",4104);
		    retFinal.put("message","Format JSON gresit!");
		    return retFinal.toString();
		}
		JSONParser parser = new JSONParser();
		Object obj = new Object();
		try {

			obj = parser.parse(ob);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			code=4103;
			message="Error on parser from json";
			retFinal.put("code",code);
		    retFinal.put("message",message);
		    return retFinal.toString();
		}
		JSONObject obj1 = (JSONObject)obj;
		JSONObject obj2 = (JSONObject) obj1.get("data");
		String city=(String) obj2.get("city");
		String state=(String) obj2.get("state");

		if(preCheck(city,state)==false){
			retFinal.put("code",code);
		    retFinal.put("message",message);
		    return retFinal.toString();
		};
		Statement stmt;
		String result=new String();
		try{
			stmt=con.createStatement();
			String cmd1="insert into citypopulationin values('"+obj.toString()+"')";
			ResultSet rs1=stmt.executeQuery(cmd1);
			stmt=con.createStatement();
			String cmd="select population from locations where oras=lower('"+city+"')";                  
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			population=rs.getInt(1);
			code=1101;
			message="corect";
			ret.put("population", population);
		    retFinal.put("code",code);
		    retFinal.put("message",message);
		    retFinal.put("data",ret);
			result=retFinal.toString();
			Statement stmt1=con.createStatement();
			String cmd2="insert into citypopulationout values('"+retFinal.toString()+"')";
			ResultSet rs2=stmt1.executeQuery(cmd2);
			if(!check()){
				retFinal.put("code",code);
			    retFinal.put("message",message);
			    return retFinal.toString();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			code=4102;
			message="Server error on SQL";
			retFinal.put("code",code);
		    retFinal.put("message",message);
		    return retFinal.toString();
		}
		
		return result;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int x=0;
		City_Population A = new City_Population();
		String sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"city\":\"Iasi\"}}";
		System.out.println("1101");
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4101");
		sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"city\":\"ba\"}}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4104");
		sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"cit\":\"iasi\"}}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		JSONObject ret = new JSONObject();
		ret.put("city",null );
		JSONObject retFinal = new JSONObject();
		retFinal.put("code",100);
		retFinal.put("data",ret );
		System.out.println("4105");
		System.out.println(retFinal.toJSONString());
		System.out.println(A.calculeaza(retFinal.toJSONString()));
		System.out.println();
		
	}
}
