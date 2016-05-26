package cityDistrict_coordinates;



import java.io.*; 
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server1 {
	private static Connection con;
	public static void main(String[] args) throws IOException, SQLException  {
	//public void call() throws IOException{	
	String clientSentence;
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(6788); 
		CityDistrict_Coordinates obj = new CityDistrict_Coordinates();
		int count=0;
		con=Conect.conn();
		Statement stmt=con.createStatement();
		while(true)
			{
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			//String[] strings=new String[2];
			//strings=clientSentence.split("\\.",2);
			count++;
			String cmd="SELECT CURRENT_TIMESTAMP from dual";
			ResultSet rs=stmt.executeQuery(cmd);
			rs.next();
			System.out.println("apelul numarul:"+count);
			System.out.println(rs.getString(1));
			//System.out.println("FROM CLIENT:"+strings[0]+"."+strings[1]);
			//CityDistrict_Coordinates.calculeaza(strings[0], strings[1]);
			
			System.out.println("FROM CLIENT:"+clientSentence);
			String sd=obj.calculeaza(clientSentence);
			System.out.println("TO CLIENT:"+sd);
			System.out.println();
			outToClient.writeBytes(sd+'\n');
			}
		}
}
