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

import org.graphstream.stream.sync.SourceTime;

public class ServiceThread extends Thread{
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
        
	
	public ServiceThread(Socket conn, BazaDate bd, Algoritm algoritm, Admin admin){
            this.bd = bd;
            this.algoritm = algoritm;
            this.admin = admin;
            this.dirijor = new DirijorServer(this.bd, this.admin);
            
            connection = conn;
            OutputStream out = null;
	    InputStream in = null;
	}
	
	public void run(){
	    try {
	    	System.out.println("M-am conectat.");
	    	//System.out.println("New connection:"+ connection.getInetAddress().getHostName());
	    	out = connection.getOutputStream();
	    	in = connection.getInputStream();
	        while (true ){
	        	this.errorFlag = false;
	        	message = null;
	        	message = new String();
                    do{ 
                        flag = in.read();
                        if((char)flag!='\n')
                            message += (char)flag;
                    }while((char)flag!='\n');
   
                    System.out.println("**"+message+" " + message.length());
                    if(checkJSON(message)== false){
                        this.sendMessage("BADJSON\n");
                        continue;
                    }
                    
                    
                    decodificare = new Decodificare(message);
                    codInput = decodificare.return_codinput();
                    
                    codOutput = decodificare.return_codoutput();
                    System.out.println(codInput + " * " + codOutput);
         
                    if(codInput == 0)
                        break;
                   
                    String dataAplicatie = "A venit un nou client";

                    //obtinem secventa de calculatoare care trebuie apelate
                    try{
                    	ArrayList<Integer> secventaCalculatoare =  new ArrayList<Integer>(this.algoritm.getListaCalculatoare(codInput, codOutput));
                        this.dirijor.receiveDataList(secventaCalculatoare, message);
                    }catch(InvalidTypesException e){
                    	this.errorFlag = true;
                    	this.errorMessage = ("{\"errorcode\" :\"500\", \"message\":\"Codul de input sau output este gresit\",\"data\":{\"sadas\"}}");
                    	this.sendMessage(errorMessage);
                    }
                    //diriijor
                    
                    if(this.errorFlag == false){
                    	raspuns = this.dirijor.sendData();
                    	System.out.println(raspuns);
                    	raspuns += '\n';
                    }
                                
                    //trimitem raspunsul primit de la dirijor catre aplicatie
                    if(this.errorFlag == false)
                    	this.sendMessage(raspuns);
                    message = "";
                    decodificare = null;
                }  
                out.close();
	        in.close();
	        connection.close();
	    	}catch (IOException e) {
                System.err.println("Server: socket error");
     		System.exit(1);
            }
	}
	
	public String byteToString(byte buffer[]){
    	String message;
		try {
			message = new String (buffer,"UTF-8");
	    	return message;
		} catch (UnsupportedEncodingException e) {
			System.out.println("[SERVER]Eroare la conversie!");
			e.printStackTrace();
		}
		return null;
	}
        
        public void sendMessage(String raspuns) throws IOException{
            //se trimite datele aplicatiei
            out.write(raspuns.getBytes(),0,raspuns.length());
        }
        
        public boolean checkJSON(String msg){
            if(msg.contains("codeinput") && msg.contains("codeoutput") && msg.contains("data"))
                return true;
            return false;
        }
}
