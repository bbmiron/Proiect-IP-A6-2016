/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dirijor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Admin.Admin;

/**
 *
 * @author Bogdan
 */

public class DirijorCalculatoare {
    
   
    public static int port; 
    public JSONObject inputJs;
    int remainComputers ; 
    String[] portAndAdress ;
    private boolean status = true; 
    
    private int nivel;
    private int nr;
    private String userId;
    private Admin admin;
      
    
    public DirijorCalculatoare() {};
    
    
    public DirijorCalculatoare(Admin admin, JSONObject input, String IndexComputer, int nivel, int nr, String userId) {
      
          this.inputJs = input; 
          
          this.portAndAdress=IndexComputer.split(":");
          //this.remainComputers = remain ;
          this.nivel = nivel;
          this.nr = nr;
          this.admin = admin;
          this.userId = userId;
              
    
}
  
    public JSONObject getOutput() throws IOException, JSONException{ 
        
        String result=""; 
        
        result = this.connectionAndOutput();
        
        JSONObject resultJs = null;
       try {
           resultJs = new JSONObject( result);
       } catch (JSONException ex) {
           Logger.getLogger(DirijorCalculatoare.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        return resultJs; 
        
    }
    
    private String connectionAndOutput() throws JSONException {
        
         String result="";
         JSONObject resultJs = null;
         String input = inputJs.toString(); // convert String
         
         ///setare default in caz de eroare ; 
        
        try {
            
            Socket socket = new Socket(this.portAndAdress[0],Integer.parseInt(this.portAndAdress[1]));
            PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
            String auxiliary;
            
            System.out.println("Sending data to computer");
            
            System.out.println(input);
            
            out.println(input);
            out.flush();
            
            System.out.println("Receive data from computer");
            
                  
            result =  in.readLine(); // se trimite string
            resultJs = new JSONObject(result); // convert JSON
            
 
 
        } catch (IOException ex) {
            Logger.getLogger(DirijorCalculatoare.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!resultJs.get("code").toString().equals("1101")){
            status = false; 
        }else{
        	//trimit admin
        	//nivel
        	//nr
        	admin.addEdge(userId, this.nivel, this.nr);
        }
        
         return result;
         
    }
    
    public boolean getStatus(){
        
         return status;
    }
    
    public int activeComputer(){  // PENTRU ADMIN   
         
        /* NUMARUL DE FUNCTII DE ACELASI TIP
           STATUSUL FUNCTIEI IN ACTIVITATE 
        */
        
  //      return this.port;
         return 1;
        
    }
    
}


