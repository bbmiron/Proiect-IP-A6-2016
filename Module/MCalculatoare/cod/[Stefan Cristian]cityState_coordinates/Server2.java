package cityState_coordinates;
import org.json.simple.JSONObject;



import java.io.*; 
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import coordinates_cityState.Conect;
import org.json.simple.*;

public class Server2 {
	private static Connection con;
	public static void main(String[] args) throws IOException, SQLException  {
	//public void call() throws IOException{	
	String clientSentence;
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(6789); 
		CityState_Coordinates obj=new CityState_Coordinates();
		int count=0;
		con=Conect.conn();
		Statement stmt=con.createStatement();
		while(true)
			{
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			count++;
			String cmd="SELECT CURRENT_TIMESTAMP from dual";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			System.out.println("apelul numarul:"+count);
			System.out.println(rs.getString(1));
			System.out.println("FROM CLIENT:"+clientSentence);
			String sd=obj.calculeaza(clientSentence);
			System.out.println("TO CLIENT:"+sd);
			System.out.println();
			outToClient.writeBytes(sd+'\n');
			}
		}
}
