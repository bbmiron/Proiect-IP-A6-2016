package cityState_wether;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cityState_coordinates.CityState_Coordinates;



public class CityState_Weather {
	private static String state;
    private static String city;
    private static Connection con;
    private static Weather weather=new Weather();
    private static boolean succes=false;
    private static int code;						//cod err
    private static String message;					//msg err
	public static String getState() {
		return state;
	}
	public static void setState(String state) {
		CityState_Weather.state = state;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		CityState_Weather.city = city;
	}
	public Connection getCon() {
		return con;
	}
	public static void setCon(Connection con) {
		CityState_Weather.con = con;
	}
	
	public CityState_Weather(){
		if(con==null)
			setCon(Conect.conn());
	}
	
	@SuppressWarnings("unchecked")
	public static String calculeaza(String jsonString){
		JSONObject ret = new JSONObject();
	    JSONObject retFinal = new JSONObject();
		if(!jsonString.contains("data")||!jsonString.contains("city")||!jsonString.contains("state")){
			retFinal.put("code",4104);
		    retFinal.put("message","Format JSON gresit!");
		    return retFinal.toString();
		}
		JSONParser parser = new JSONParser();
		Object obj = new Object();
		try {
			obj = parser.parse(jsonString);
		} catch (ParseException e1) {
			e1.printStackTrace();
			code=4103;
			message="err on parsing from json (city_weather)";
		}
		JSONObject JSONobj = (JSONObject)obj;
		JSONObject JSONobj2 = (JSONObject) JSONobj.get("data");
		city=(String) JSONobj2.get("city");
		state=(String) JSONobj2.get("state");
		
		JSONObject result=new JSONObject();
		if(!precheck(state,city)){
			result.put("code", code);
			result.put("message", message);
			return result.toString();
		}else{
			
		}
				
		setCity(city);
		setState(state);
		
		JSONObject weather=ApiWeather.getWeather(city); 
		result.put("code", code);
		result.put("message", message);
		result.put("data",weather);
		
		
		System.out.println("calculeaza"+weather);
		setSucces(true);
		
		return result.toString();
	}
	
	public static boolean precheck(String state, String city){
		if(city==null||state==null){
			code=4103;
			message="parametru null";
			return false;
		}else{
			code=1101;
			message="ok";
		}
		if(con==null)
			setCon(Conect.conn());
		Statement st;
		try {
			
			st = con.createStatement();
			String cmd="select count(*) from locations where stat=lower('"+state+"') and oras=lower('"+city+"')";
			ResultSet rs=st.executeQuery(cmd);
			rs.next();
			if(rs.getInt(1)==0){
				code=4101;
				message="Locatia nu exista in baza de date";
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			code=4102;
			message="Server error on sql";
		}
		return true;
	}
	
	public static String afiseaza_vremea(){
		//return "In "+getCity()+" sunt "+weather.getTemp()+" grade.";
		return "In "+getCity()+" sunt "+weather.getTemp()+"\u00B0"+"C"+
		".temperatura maxima anuntata "+weather.getTemp_max()+"\u00B0"+"C"+
		".temperatura minim anuntata "+weather.getTemp_min()+"\u00B0"+"C"+
		".umiditate "+weather.getHumidity()+"%"+
		".precipitatii "+weather.getPrecip()+"%"+
		".vizibilitate "+weather.getVisibility()+"Km"+
		".starea cerului "+weather.getSky_state();
	}
	public static boolean isSucces() {
		return succes;
	}
	public static void setSucces(boolean succes) {
		CityState_Weather.succes = succes;
	}
	public static int getCode() {
		return code;
	}
	public static void setCode(int code) {
		CityState_Weather.code = code;
	}
	public static String getMessage() {
		return message;
	}
	public static void setMessage(String message) {
		CityState_Weather.message = message;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int x=0;
		CityState_Weather A = new CityState_Weather();
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
