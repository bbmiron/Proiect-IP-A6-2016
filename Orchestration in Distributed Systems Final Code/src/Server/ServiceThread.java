package Server;

import Admin.Admin;
import CustomExceptions.*;
import AlgoritmSecventa.Algoritm;
import BazaDate.BazaDate;
import CustomExceptions.InvalidTypesException;
import Dirijor.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.graphstream.stream.sync.SourceTime;
import org.json.JSONObject;

public class ServiceThread extends Thread {
	private Socket connection;
	private OutputStream out;
	private InputStream in;
	private String message = "";
	private BazaDate bd;
	private Algoritm algoritm;
	private Admin admin;
	private DirijorServer dirijor;
	private int flag;
	private Decodificare decodificare;
	private int codInput;
	private int codOutput;
	private String errorMessage;
	private boolean errorFlag = false;
	private String raspuns;
	private String idUser;
	private JSONObject jsonObjCheck;

	public ServiceThread(Socket conn, BazaDate bd, Algoritm algoritm, Admin admin, String idUser) {

		this.idUser = idUser;
		this.bd = bd;
		this.algoritm = algoritm;
		this.admin = admin;
		this.dirijor = new DirijorServer(this.bd, this.admin, this.idUser);

		connection = conn;
		OutputStream out = null;
		InputStream in = null;
	}

	public void run() {
		try {
			System.out.println("M-am conectat.");
			// System.out.println("New connection:"+
			// connection.getInetAddress().getHostName());
			out = connection.getOutputStream();
			in = connection.getInputStream();
			while (true) {
				this.errorFlag = false;
				message = null;
				message = new String();
				do {
					flag = in.read();
					if ((char) flag != '\n')
						message += (char) flag;
				} while ((char) flag != '\n');

				System.out.print("[Input primit]" + message);
				if (checkJSON(message) == false) {
					this.sendMessage(
							"{\"code\" :\"4101\", \"message\":\"BADJSON\",\"data\":{\"eroare\":\"[CheckJson]nu exista\"}}");
					continue;
				}

				decodificare = new Decodificare(message);
				codInput = decodificare.return_codinput();

				codOutput = decodificare.return_codoutput();

				if (codInput == 0) {
					System.out.println("[ServiceThread]Un client a parasit aplicatia.");
					break;
				}

				String dataAplicatie = "A venit un nou client";

				// obtinem secventa de calculatoare care trebuie apelate
				try {
					ArrayList<Integer> secventaCalculatoare = new ArrayList<Integer>(
							this.algoritm.getListaCalculatoare(codInput, codOutput));
					this.dirijor.receiveDataList(secventaCalculatoare, message);
				} catch (InvalidTypesException e) {
					this.errorFlag = true;
					this.errorMessage = ("{\"code\" :\"4101\", \"message\":\"Codul de input sau output este gresit\",\"data\":{\"eroare\":\"nu exista\"}}");
					this.sendMessage(errorMessage);
				}
				// diriijor

				if (this.errorFlag == false) {
					raspuns = this.dirijor.sendData();
					System.out.println("[Mesaj primit]" + raspuns);
					raspuns += '\n';
				}

				// trimitem raspunsul primit de la dirijor catre aplicatie
				if (this.errorFlag == false)
					this.sendMessage(raspuns);
				message = "";
				decodificare = null;
			}
			out.close();
			in.close();
			connection.close();
		} catch (IOException e) {
			System.out.println("[ServiceThread]Am fost oprit fortat!");
		}
	}

	public String byteToString(byte buffer[]) {
		String message;
		try {
			message = new String(buffer, "UTF-8");
			return message;
		} catch (UnsupportedEncodingException e) {
			System.out.println("[SERVER]Eroare la conversie!");
			e.printStackTrace();
		}
		return null;
	}

	public void sendMessage(String raspuns) throws IOException {
		// se trimite datele aplicatiei
		out.write(raspuns.getBytes(), 0, raspuns.length());
	}

	public boolean checkJSON(String msg) {
		try {
			this.jsonObjCheck = new JSONObject(msg);
		} catch (Exception e) {
			return false;
		}
		if (msg.contains("\"codeinput\":") && msg.contains("\"codeoutput\":") && msg.contains("\"data\":"))
			return true;
		return false;
	}
}
