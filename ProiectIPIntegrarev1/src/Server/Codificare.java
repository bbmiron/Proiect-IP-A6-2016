package Server;

import org.json.*;
public class Codificare {
	JSONObject jsonObj = new JSONObject();
	public void add(String cheie,String valoare){
		jsonObj.put(cheie, valoare);
	}
	public  String get_output(){
		return jsonObj.toString();
	}
	public String get_json(){
		return this.jsonObj.toString();
	}
}
