/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dirijor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import Admin.Admin;
import BazaDate.BazaDate;

/**
 *
 * @author Gabor
 */
public class DirijorServer {
    
    private String input;
    private JSONObject output;
    private ArrayList<Integer> cList;
    
    private BazaDate bd; // clasa baza de date
    private Admin admin;  // clasa administrator
    
    public DirijorServer(BazaDate bd, Admin admin){
        this.bd = bd;
        this.admin = admin;
    }
    
    
    //apelata de server
    //cList - tipul calculatoarelor
    public void receiveDataList(ArrayList<Integer> cList, String input){
        
        this.input=input;
        this.cList=cList;
        
    }
    
    private void buildOutput() {
        
        IndexComputer calculate = new IndexComputer(this.bd, this.admin);
        calculate.setInfoFromDServer(cList, input);
        try {
            output=calculate.getFinalOutput();
        } catch (IOException ex) {
            Logger.getLogger(DirijorServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DirijorServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // apelare pentru output final
    public String sendData(){
        
        this.buildOutput();
        return this.output.toString();
        
    }
}
