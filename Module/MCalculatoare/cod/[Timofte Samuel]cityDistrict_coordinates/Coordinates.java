package cityDistrict_coordinates;


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
	
	Coordinates(){

		long_grade=-200;
		long_minute=-200;
		long_secunde=-200;
		lat_grade=-200;
		lat_minute=-200;
		lat_secunde=-200;
		success=true;
		code=1101;
		message="corect";
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
	
	public void setMessage(String aux){
		message=aux;
	}
	public String getMessage(){
		return message;
	}
}
