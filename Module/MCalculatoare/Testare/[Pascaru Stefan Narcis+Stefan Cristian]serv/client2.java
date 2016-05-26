package serv;

import java.io.*; 
import java.net.*;

import org.json.simple.JSONObject; 
import org.json.simple.*;
public class client2 {  
	public void test(String ob) throws Exception  {   
		String sentence;   
		String modifiedSentence;   
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));   
		Socket clientSocket = new Socket("192.168.100.2", 6789);   
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
		sentence=ob;
		outToServer.writeBytes(sentence + '\n'); 		  
	      String obj1 = new String();
	      obj1=inFromServer.readLine();
	      System.out.println(obj1);
	      clientSocket.close(); 

		} 

	}
