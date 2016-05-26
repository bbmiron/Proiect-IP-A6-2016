package cityState_coordinates;

import java.sql.Connection;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class CityState_Coordinates {
	private static Connection con;
	public Coordinates coord = new Coordinates();
	
	public boolean preCheck(String city,String state){
		if(city==null||state==null){
			coord.setCode(4105);
			coord.setMessage("Completati toate campurile");
			coord.setSuccess();
			return false;
		}
		if(con==null)
			con=Conect.conn();
		Statement stmt;
		try{
			stmt=con.createStatement();
			String cmd="select count(*) from locations where oras=lower('"+city+"')"+"and stat=lower('"+state+"')";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			int aux=rs.getInt(1);
			if(aux==0){
				coord.setCode(4101);
				coord.setMessage("Locatia nu exista in baza de date");
				coord.setSuccess();
				return false;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			coord.setCode(4102);
			coord.setMessage("Server error on SQL");
			coord.setSuccess();
		}
		return true;
	}
	public boolean check(){
		if(coord.getLatGr()==-200||coord.getLongGr()==-200){
			coord.setCode(4101);
			coord.setMessage("Locatia nu exista in baza de date");
			coord.setSuccess();
			
			return false;
		}
		return true;
	}
	
	public String calculeaza(String ob) {
		JSONObject ret = new JSONObject();
	    JSONObject retFinal = new JSONObject();
		if(!ob.contains("data")||!ob.contains("city")||!ob.contains("state")){
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
			coord.setCode(4103);
			coord.setMessage("Error on parser from json");
			coord.setSuccess();
			retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    return retFinal.toString();
		}
		JSONObject obj1 = (JSONObject)obj;
		JSONObject obj2 = (JSONObject) obj1.get("data");
		String city=(String) obj2.get("city");
		String state=(String) obj2.get("state");

		if(preCheck(city,state)==false){
			retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    return retFinal.toString();
		};
		Statement stmt;
		String result=new String();
		try{
			stmt=con.createStatement();
			String cmd1="insert into citystatein values('"+obj.toString()+"')";
			ResultSet rs1=stmt.executeQuery(cmd1);
			stmt=con.createStatement();
			String cmd="select longitudine_grade,longitudine_minute,longitudine_secunde,latitudine_grade,latitudine_minute,latitudine_secunde from locations where oras=lower('"+city+"')"+"and stat=lower('"+state+"')";                  
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			coord.setLongGr(rs.getInt(1));
			coord.setLongMin(rs.getInt(2));
			coord.setLongSec(rs.getInt(3));
			coord.setLatGr(rs.getInt(4));
			coord.setLatMin(rs.getInt(5));
			coord.setLatSec(rs.getInt(6));
			ret.put("lat_gr",coord.getLatGr());
			ret.put("lat_min",coord.getLatMin());
			ret.put("lat_sec",coord.getLatSec());
			ret.put("long_gr",coord.getLongGr());
			ret.put("long_min",coord.getLongMin());
			ret.put("long_sec",coord.getLongSec());
			coord.setCode(1101);
			coord.setMessage("corect");
		    retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    retFinal.put("data",ret);
			result=retFinal.toString();
			Statement stmt1=con.createStatement();
			String cmd2="insert into citystateout values('"+retFinal.toString()+"')";
			ResultSet rs2=stmt1.executeQuery(cmd2);
			if(!check()){
				retFinal.put("code",coord.getCode());
			    retFinal.put("message",coord.getMessage());
			    return retFinal.toString();
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			coord.setCode(4102);
			coord.setMessage("Server error on SQL");
			retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    return retFinal.toString();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int x=0;
		CityState_Coordinates A = new CityState_Coordinates();
		String sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"city\":\"Iasi\",\"state\":\"iasi\"}}";
		System.out.println("1101");
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4101");
		sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"city\":\"ba\",\"state\":\"iasi\"}}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4104");
		sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"cit\":\"iasi\",\"state\":\"iasi\"}}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		JSONObject ret = new JSONObject();
		ret.put("city",null );
		ret.put("state","iasi" );
		JSONObject retFinal = new JSONObject();
		retFinal.put("code",100);
		retFinal.put("data",ret );
		System.out.println("4105");
		System.out.println(retFinal.toJSONString());
		System.out.println(A.calculeaza(retFinal.toJSONString()));
		System.out.println();
		
	}
	
}
