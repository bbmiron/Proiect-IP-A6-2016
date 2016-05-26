package serv;

import java.io.*; 
import java.net.*; 
public class client3 {  
	public static void main(String argv[]) throws Exception  {   
		String sentence;   
		String modifiedSentence;   
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));   
		Socket clientSocket = new Socket("192.168.100.2", 6790);   
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
		//sentence = inFromUser.readLine();
		sentence=argv[0];
		//sentence="{\"code\":12,\"message\":\"sgdsfd\",\"data\":{\"city\":\"iasi\",\"state\":\"iasi\"}}";
		outToServer.writeBytes(sentence + '\n'); 		
		modifiedSentence = inFromServer.readLine();   
		System.out.println("FROM SERVER: " + modifiedSentence);   
		clientSocket.close(); 

		} 
	}
//{"code":12,"message":"sgdsfd","data":{"city":"iasi","state":"iasi"}}
