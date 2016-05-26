package coordinates_cityState;

public class Coordinates {
	   
	private int long_grade;
	private int long_minute;
	private int long_secunde;
	private int lat_grade;
	private int lat_minute;
	private int lat_secunde;
	private boolean success;
	private int code;
	private String message;
	private String city;
	private String state;
	
	Coordinates(){

		long_grade=-200;
		long_minute=-200;
		long_secunde=-200;
		lat_grade=-200;
		lat_minute=-200;
		lat_secunde=-200;
		success=true;
		code=1100;
		message=new String();
		city=new String();
		state=new String();
	}
	

	
	public void setLongGr(int aux){
		long_grade=aux;
	}
	public int getLongGr(){
		return long_grade;
	}
	
	public void setLongMin(int aux){
		long_minute=aux;
	}
	public int getLongMin(){
		return long_minute;
	}
	
	public void setLongSec(int aux){
		long_secunde=aux;
	}
	public int getLongSec(){
		return long_secunde;
	}
	
	public void setLatGr(int aux){
		lat_grade=aux;
	}
	public int getLatGr(){
		return lat_grade;
	}
	
	public void setLatMin(int aux){
		lat_minute=aux;
	}
	public int getLatMin(){
		return lat_minute;
	}
	
	public void setLatSec(int aux){
		lat_secunde=aux;
	}
	public int getLatSec(){
		return lat_secunde;
	}
	
	public void setSuccess(){
		success=false;
	}
	public boolean getSuccess(){
		return success;
	}
	
	public void setCode(int aux){
		code=aux;
	}
	public int getCode(){
		return code;
	}
	public String getCity(){
		return city;
	}
	public void setCity(String c){
		this.city=c;
	}
	public String getState(){
		return state;
	}
	public void setState(String c){
		state=c;
	}
	
	public void setMessage(String aux){
		message=aux;
	}
	public String getMessage(){
		return message;
	}
}



