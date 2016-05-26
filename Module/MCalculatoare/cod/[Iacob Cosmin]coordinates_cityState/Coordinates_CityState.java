package coordinates_cityState;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cityState_coordinates.CityState_Coordinates;
public class Coordinates_CityState {
	private static Connection con;
	public Coordinates coord = new Coordinates();
       
	public boolean preCheck(int longGr, int longMin,int longSec,int latGr,int latMin,int latSec){
		if(longGr<0||longGr>90 ||latGr<0||latGr>90){
			coord.setCode(4103);
			coord.setMessage("Parameter out of bounds!");
			coord.setSuccess();
			return false;
		}
		if(con==null)
			con=Conect.conn();
		Statement stmt;
		try{
			stmt=con.createStatement();
			String cmd="select count(*) from locations where longitudine_grade="+longGr+" AND latitudine_grade="+latGr;
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
		    
			coord.setCode(4102);
			coord.setMessage("Server error on SQL");
			coord.setSuccess();
		}
		return true;
	}
	
	public String calculeaza(String ob){
		JSONObject ret = new JSONObject();
	    JSONObject retFinal = new JSONObject();
		if(!ob.contains("data")||!ob.contains("long_gr")||!ob.contains("long_min")||!ob.contains("long_sec")||!ob.contains("lat_gr")||!ob.contains("lat_min")||!ob.contains("lat_sec")){
			retFinal.put("code",4104);
		    retFinal.put("message","Format JSON gresit!");
		    return retFinal.toString();
		}
		JSONParser parser = new JSONParser();
		Object obj = new Object();
		try {
			obj = parser.parse(ob);
		} catch (ParseException e1) {
			e1.printStackTrace();
			coord.setCode(4103);
			coord.setMessage("Error on parser from json");
			coord.setSuccess();
			retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    return retFinal.toString();
		}
		JSONObject obj1 = (JSONObject) obj;
		JSONObject obj2 = (JSONObject) obj1.get("data");
		int longGr=(int)(long)obj2.get("long_gr");
		int longMin=(int)(long) obj2.get("long_min");
		int longSec=(int)(long) obj2.get("long_sec");
		int latGr=(int)(long)obj2.get("lat_gr");
		int latMin=(int)(long) obj2.get("lat_min");
		int latSec=(int)(long) obj2.get("lat_sec");
		//String c=(String) obj1.get("cod");
		if(preCheck(longGr,longMin,longSec,latGr,latMin,latSec)==false) {
			retFinal.put("code",coord.getCode());
	    retFinal.put("message",coord.getMessage());

	    return retFinal.toString();}
		Statement stmt;
                String state=new String();
                String city=new String();
                String locatie=new String();
		try{    
			stmt=con.createStatement();
			String cmd1="insert into coordinatescitystatein values('"+obj.toString()+"')";
			ResultSet rs1=stmt.executeQuery(cmd1);            
			stmt=con.createStatement();
			String cmd="select oras,stat from locations where longitudine_grade="+longGr+" and longitudine_minute="+longMin+" and longitudine_secunde="+longSec+" and latitudine_grade="+latGr+" and latitudine_minute="+latMin+" and latitudine_secunde="+latSec+
					" or longitudine_grade="+longGr+" and longitudine_minute="+longMin+" and longitudine_secunde="+longSec+" and latitudine_grade="+latGr+" and latitudine_minute="+latMin+
					" or longitudine_grade="+longGr+" and longitudine_minute="+longMin+" and latitudine_grade="+latGr+" and latitudine_minute="+latMin+" and latitudine_secunde="+latSec+
					" or longitudine_grade="+longGr+" and longitudine_minute="+longMin+" and latitudine_grade="+latGr+" and latitudine_minute="+latMin+
					" or longitudine_grade="+longGr+" and latitudine_grade="+latGr+" and latitudine_minute="+latMin+
					" or longitudine_grade="+longGr+" and longitudine_minute="+longMin+" and latitudine_grade="+latGr+
					" or longitudine_grade="+longGr+" and latitudine_grade="+latGr;
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			city=rs.getString(1);
            state=rs.getString(2);
            ret.put("city",city);
            ret.put("state", state);
			coord.setCode(1101);
			coord.setMessage("corect");
			coord.setCity(city);
			coord.setState(state);
            retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    retFinal.put("data",ret);
			locatie=retFinal.toString();
			Statement stmt1=con.createStatement();
			String cmd2="insert into coordinatescitystateout values('"+retFinal.toString()+"')";
			ResultSet rs2=stmt1.executeQuery(cmd2);
			if(!check()){
				retFinal.put("code",coord.getCode());
			    retFinal.put("message",coord.getMessage());
			    return retFinal.toString();
			}
		}
		catch(SQLException e){
			coord.setCode(4102);
			coord.setMessage("Server error on SQL");
			retFinal.put("code",coord.getCode());
		    retFinal.put("message",coord.getMessage());
		    return retFinal.toString();
		}
                return locatie;
        }

	public boolean check(){
		if(coord.getCity()==null||coord.getState()==null){
			coord.setCode(4103);
			coord.setMessage("Locatia nu exista in baza de date");
		
		return false;}
		return true;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int x=0;
		Coordinates_CityState A = new Coordinates_CityState();
		String sentence="{\"code\":1101,\"data\":{\"long_min\":9,\"lat_gr\":27,\"lat_min\":35,\"lat_sec\":20,\"long_gr\":47,\"long_sec\":44},\"message\":\"corect\"}";
		System.out.println("1101");
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4101");
		sentence="{\"code\":1101,\"data\":{\"long_min\":89,\"lat_gr\":89,\"lat_min\":35,\"lat_sec\":20,\"long_gr\":47,\"long_sec\":44},\"message\":\"corect\"}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		System.out.println("4104");
		sentence="{\"code\":1101,\"message\":\"sgdsfd\",\"data\":{\"cit\":\"iasi\",\"state\":\"iasi\"}}";
		System.out.println(sentence);
		System.out.println(A.calculeaza(sentence));
		System.out.println();
		
		/*JSONObject ret = new JSONObject();
		ret.put("city",null );
		ret.put("state","iasi" );
		JSONObject retFinal = new JSONObject();
		retFinal.put("code",100);
		retFinal.put("data",ret );
		System.out.println("4105");
		System.out.println(retFinal.toJSONString());
		System.out.println(A.calculeaza(retFinal.toJSONString()));
		System.out.println();*/
		
	}


}

