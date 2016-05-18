package Server;

import Admin.Admin;
import AlgoritmSecventa.Algoritm;
import BazaDate.BazaDate;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Server {
	private Socket connSocket;
	private int port;
	private ServerSocket  serverSocket;
	private ServiceThread newConnection;
	private BazaDate bd;
        private Algoritm algoritm;
        private Admin admin;
        
	Server() throws SQLException{
            
                this.setBD(new BazaDate());
                this.setAlgoritm(new Algoritm(bd));
                this.setAdmin(new Admin());
                
            
		connSocket = null;
                port = 3333;
		newConnection = null;
		System.out.println("SERVER IS ON");
		startServer();
	}
	
	protected void startServer(){
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[SERVER]Eroare la setare port!");
			e.printStackTrace();
		}
		while(true){

                        System.out.println("accept");
			try {
				connSocket =  serverSocket.accept();
                                System.out.printf("Am facut accept!");
                                
			} catch (IOException e) {
				System.out.println("[SERVER]Eroare la accept.");
				e.printStackTrace();
			}
                        System.out.println("accept");
      			newConnection =  new ServiceThread(connSocket, this.getBD(), this.getAlgoritm(), this.getAdmin());
			newConnection.start();
		}
	}
	
	public static void main(String[] args) throws SQLException{
		Server S =  new Server();
	}
        
        public void setBD(BazaDate bd){
            this.bd = bd;
        }
        
        public void setAlgoritm(Algoritm algoritm){
            this.algoritm = algoritm;
        }
        
        public void setAdmin(Admin admin){
            this.admin = admin;
        }
        
        public BazaDate getBD(){
            return this.bd;
        }
        
        public Algoritm getAlgoritm(){
            return this.algoritm;
        }
        
        public Admin getAdmin(){
            return this.admin;
        }
}
