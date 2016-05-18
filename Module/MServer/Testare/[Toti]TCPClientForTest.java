
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class TCPClient extends Thread {

    private Socket connection;
    private OutputStream out;
    private InputStream in;
    private byte buffer[];
    private String msg;
    private String fromServer;
    private int flag;

    public void run(){
        try {
            try {
                connection = new Socket("localhost", 7777);
            } catch (IOException ex) {
                Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            out = connection.getOutputStream();
            in = connection.getInputStream();
            msg = "{\"codeinput\":\"11\",\"codeoutput\":\"12\",\"data\":{}}\n";
            out.write(msg.getBytes(), 0, msg.length());
            System.out.print("Am trimis");
            msg += "";
            do {
                flag = in.read();
                System.out.println(flag);
                if ((char) flag != '\n') {
                    msg += (char) flag;
                }
            } while ((char) flag != '\n');
            
           msg = "{\"codeinput\":\"0\",\"codeoutput\":\"12\",\"data\":{}}\n";
            out.write(msg.getBytes(), 0, msg.length());
            connection.close();
            System.out.println(msg);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String argv[]) throws Exception {

        TCPClient proba = new TCPClient();
    }
}
