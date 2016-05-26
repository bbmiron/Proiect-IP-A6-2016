package cityState_wether;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ApiWeather {
	private String city;
	private static String api=new String();

	public ApiWeather(){
		
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getWeather(String oras){
		JSONObject JSONobj = new JSONObject();
		
		
		api="http://api.openweathermap.org/data/2.5/weather?q="+oras
				+ "&mode=xml&units=metric&appid=e265104d0f2918462930219887ebaac5";
    try {
        URL weatherXml = new URL(api);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();    
        Document doc = db.parse(weatherXml.openStream());
        doc.getDocumentElement ().normalize ();
        NodeList node = doc.getElementsByTagName("temperature");
        
        String temper=node.item(0).getAttributes().getNamedItem("value").getNodeValue();
		int temperatura= (int) Math.round(Double.parseDouble(temper));
        JSONobj.put("temperature", temperatura);
		//System.out.println(temperatura);
        
        temper=node.item(0).getAttributes().getNamedItem("min").getNodeValue();
        int temp_min=(int) Math.round(Double.parseDouble(temper));
        JSONobj.put("tMin", temp_min);
        //System.out.println(temp_min);
        temper=node.item(0).getAttributes().getNamedItem("max").getNodeValue();
        int temp_max=(int) Math.round(Double.parseDouble(temper));
        JSONobj.put("tMax", temp_max);
        //System.out.println(temp_max);
        node=doc.getElementsByTagName("humidity");
        String humi=node.item(0).getAttributes().getNamedItem("value").getNodeValue();
        JSONobj.put("humidity", humi);
        //System.out.println(humi+"%");
        node=doc.getElementsByTagName("pressure");
        String press=node.item(0).getAttributes().getNamedItem("value").getNodeValue();
        JSONobj.put("pressure", press);
        //System.out.println(press+"hPa");
        node=doc.getElementsByTagName("direction");
        String wind_dir=node.item(0).getAttributes().getNamedItem("name").getNodeValue();
        JSONobj.put("windDirection", wind_dir);
        //System.out.println(wind_dir);
        node=doc.getElementsByTagName("precipitation");
        String precip=node.item(0).getAttributes().getNamedItem("mode").getNodeValue();
        JSONobj.put("precipitation", precip);
        //System.out.println(precip);
        node=doc.getElementsByTagName("clouds");
        String sky_state=node.item(0).getAttributes().getNamedItem("name").getNodeValue();
        JSONobj.put("skyState", sky_state);
        //System.out.println(sky_state);
        System.out.println(JSONobj.toString());
        return JSONobj;
        
    }catch(Exception e){
    	e.printStackTrace();
    }
    return JSONobj;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
