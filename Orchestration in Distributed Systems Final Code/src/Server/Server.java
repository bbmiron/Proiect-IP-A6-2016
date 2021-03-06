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
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
	private Socket connSocket;
	private int port;
	private ServerSocket serverSocket;
	private ServiceThread newConnection;
	private BazaDate bd;
	private Algoritm algoritm;
	private Admin admin;
	private LinkedList<Integer> idLiber;
	private int maximID;

	public Server(Admin admin) throws SQLException {

		this.setBD(new BazaDate());
		this.setAlgoritm(new Algoritm(bd));
		this.setAdmin(admin);
		admin.bazaDate = bd;

		this.idLiber = new LinkedList<>();
		maximID = 0;

		connSocket = null;
		port = 3333;
		newConnection = null;
		System.out.println("SERVER IS ON");
		// startServer();
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("[SERVER]Eroare la setare port!");
			e.printStackTrace();
		}
		while (true) {

			System.out.println("accept");
			try {
				connSocket = serverSocket.accept();

			} catch (IOException e) {
				System.out.println("[SERVER]Eroare la accept.");
				e.printStackTrace();
			}
			
            Integer idNou = idLiber.poll();
            
            if(idNou == null){
            	idNou = maximID++;
            }

			newConnection = new ServiceThread(connSocket, this.getBD(), this.getAlgoritm(), this.getAdmin(),idNou.toString());
			newConnection.start();
		}
	}

	/*
	 * public static void main(String[] args) throws SQLException{ Server S =
	 * new Server(); }
	 */

	public void setBD(BazaDate bd) {
		this.bd = bd;
	}

	public void setAlgoritm(Algoritm algoritm) {
		this.algoritm = algoritm;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public BazaDate getBD() {
		return this.bd;
	}

	public Algoritm getAlgoritm() {
		return this.algoritm;
	}

	public Admin getAdmin() {
		return this.admin;
	}
}
