package cityState_wether;


import java.io.*; 
import java.net.*;
import java.util.Date;

public class Server3 {

	public static void main(String[] args) throws IOException {
		//public void call() throws IOException{
		String clientSentence;
		        
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(6790);
		int count=0;
		while(true)
			{
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			
			count++;
			Date data=new Date();
			System.out.println(data);
			System.out.println("apelul numarul:"+count);
			System.out.println("FROM CLIENT: " + clientSentence);
			//String[] strings=new String[2];
			//strings=clientSentence.split("\\.",2);
			
			String sd=CityState_Weather.calculeaza(clientSentence);
			System.out.println("TO CLIENT: "+sd+"\n");
			outToClient.writeBytes(sd+'\n');
			
			}
			
	}

}
