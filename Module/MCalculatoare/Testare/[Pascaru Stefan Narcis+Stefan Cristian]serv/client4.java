package serv;

import java.io.*; 
import java.net.*; 
public class client4 {  
	public void test(String ob) throws Exception  {   
		String sentence;   
		String modifiedSentence;   
		BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));   
		Socket clientSocket = new Socket("192.168.0.139", 6792);   
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
		//sentence = inFromUser.readLine();
		sentence=ob;
		
		outToServer.writeBytes(sentence + '\n'); 		
		/*modifiedSentence = inFromServer.readLine();   
		System.out.println("FROM SERVER: " + modifiedSentence);  */
		String obj1= new String();
		obj1=inFromServer.readLine();
		System.out.println(obj1);
		clientSocket.close(); 
		} 

	}
