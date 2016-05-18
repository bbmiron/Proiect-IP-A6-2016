/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Geo
 */
public class TestServer {
    private Socket connection;
    private OutputStream out;
    private InputStream in;
    private byte buffer[];
    private String msg;
    private String fromServer;
    private int flag;
    
    public TestServer() {
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //


    public String conexiune(String msg) throws IOException, InterruptedException {
        connection = new Socket("localhost", 7777);
        out = connection.getOutputStream();
        in = connection.getInputStream();
        out.write(msg.getBytes(), 0, msg.length());
        System.out.print("Am trimis");
        msg += "";
        do {
            flag = in.read();
            if ((char) flag != '\n') {
                msg += (char) flag;
            }
        } while ((char) flag != '\n');
                   System.out.println(msg+"**");
            String stop = "{\"codeinput\":\"0\",\"codeoutput\":\"13\",\"data\":{}}\n";
            out.write(stop.getBytes(), 0, stop.length());
            Thread.sleep(1000);
            connection.close();
            System.out.println(msg);
         return msg;
    }
    
    public int testClose(String msg) throws IOException, InterruptedException {
        connection = new Socket("localhost", 7777);
        out = connection.getOutputStream();
        in = connection.getInputStream();
        out.write(msg.getBytes(), 0, msg.length());
        System.out.print("Am trimis");
         flag = in.read();
         System.out.println(flag);
         return flag;
    }
    
    
    public String conexiune2(String msg) throws IOException, InterruptedException {
        Random rand = new Random();

        int  n = rand.nextInt(3000) + 1000;
        Thread.sleep(n);
        connection = new Socket("localhost", 7777);
        out = connection.getOutputStream();
        in = connection.getInputStream();
        out.write(msg.getBytes(), 0, msg.length());
        System.out.print("Am trimis");
        msg += "";
        do {
            flag = in.read();
            if ((char) flag != '\n') {
                msg += (char) flag;
            }
        } while ((char) flag != '\n');
                   System.out.println(msg+"**");
            String stop = "{\"codeinput\":\"0\",\"codeoutput\":\"13\",\"data\":{}}\n";
            out.write(stop.getBytes(), 0, stop.length());
            Thread.sleep(1000);
            connection.close();
            System.out.println(msg);
         return msg;
    }
    
    @Test
    public void testConnection1() throws IOException, InterruptedException {
        String rezultat;
        rezultat = conexiune("{\"codeinput\":\"11\",\"codeoutput\":\"13\"}\n");
        Assert.assertTrue(rezultat.length()>0);
    }
    
    //testatre daca mesajul ajunge la dirijor
     @Test
    public void testConnection2() throws IOException, InterruptedException {
        String rezultat;
        rezultat = conexiune("{\"codeinput\":\"11\",\"codeoutput\":\"12\",\"data\":{}}\n");
        Assert.assertTrue(rezultat.contains("*"));
    }
    
    //close connection
   @Test
    public void testConnection3() throws IOException, InterruptedException {
        int rezultat;
        rezultat = testClose("{\"codeinput\":\"0\",\"codeoutput\":\"13\",\"data\":{}}\n");
        Assert.assertTrue(rezultat == -1);
    }
    
       //JSON gresit
    @Test
    public void testConnection4() throws IOException, InterruptedException {
        String rezultat;
        rezultat = conexiune("{\"codeoutput\":\"13\"}\n");
        Assert.assertTrue(rezultat.contains("BADJSON"));
    }
    
    @Test
    public void testConnection5() throws IOException, InterruptedException {
        
        for(int i = 0 ; i < 5 ; i++){
            String rezultat;
            rezultat = conexiune2("{\"codeinput\":\"11\",\"codeoutput\":\"13\",\"data\":{}}\n");
            System.out.println(rezultat);
            Assert.assertTrue(rezultat.contains("*"));
        }
    }
    
    @Test
    public void testConnection6() throws IOException, InterruptedException {
        String test = "test";
        for(int i = 0 ; i < 5 ; i++){
           TCPClient client =  new TCPClient();
            client.start();

        }
            Assert.assertTrue(test.contains("test"));// daca functia nu a fost blocat si a ajuns in acest 
			                                         //punct inseamna ca este ok.
    }
    

}
