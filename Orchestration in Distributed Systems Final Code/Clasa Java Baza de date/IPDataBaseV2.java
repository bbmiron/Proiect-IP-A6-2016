/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip.database.v2;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class IPDataBaseV2 {
    Connection conn;
    private Algoritm alg;
    /**
     * @param args the command line arguments
     */
    IPDataBaseV2 (){
        System.out.println("-------- Test ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Lipsa JDBC Driver");
                e.printStackTrace();
                return;
                    }
            System.out.println("Oracle JDBC Driver Registered!");
            try {
                conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "Alex", "admin");
            } catch (SQLException e) {
                System.out.println("Connection Failed!");
                e.printStackTrace();
                return;
            }
            if (conn != null) {
                System.out.println("Connected!");
            } else {
                System.out.println("Failed to make connection!");
            }

    }
    
    public void setAlgortim(Algoritm x){
        this.alg = x;
    }
    
    //lista de calculatoare din baza de date
    public ArrayList<ClasaSpeciala> getList() throws SQLException{
        ArrayList<ClasaSpeciala> output = new ArrayList<ClasaSpeciala>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT input, output, tipFunctie from Computers");
        int cc = rs.getMetaData().getColumnCount();
        
        for(int i=1; rs.next() ;i++){
            ClasaSpeciala temp = new ClasaSpeciala(rs.getInt("input"), rs.getInt("output"), rs.getInt("tipFunctie"));
            output.add(temp);
        }
        
        return output;
    }
    
    public ArrayList<String> getStatus(int tipF) throws SQLException{
        ArrayList<String> output = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT IP_Adress, port FROM Computers WHERE tipFunctie = " + tipF);
        int cc = rs.getMetaData().getColumnCount();
        
        for(int i=1; rs.next(); i++){
            String temp = rs.getString("IP_Adress") +":"+ rs.getInt("port");
            output.add(temp);
        }
        
        return output;
    }
    
    public boolean modifyStatus(int port) throws SQLException{
    CallableStatement temp;
        temp = conn.prepareCall("{call ? := java_modify_status(?)}");
        temp.registerOutParameter(1, Types.VARCHAR);
        temp.setInt(2, port);
        temp.execute();
        String outPLSQL = temp.getString(1);
        
        return "True".equals(outPLSQL);
    }
    
    public boolean addFunction(String IP, int port, int input, int output, int tipFunctie) throws SQLException{
        CallableStatement temp;
        temp = conn.prepareCall("{call ? := java_add_calc(?, ?, ?, ?, ?)}");
        temp.registerOutParameter(1, Types.VARCHAR);
        temp.setString(2, IP);
        temp.setInt(3, port);
        temp.setInt(4, input);
        temp.setInt(5, output);
        temp.setInt(6, tipFunctie);
        temp.execute();
        String outPLSQL = temp.getString(1);
        alg.addFunctie(input, output, tipFunctie);
        return "True".equals(outPLSQL);
    }
    
    public boolean removeFunction(String IP, int port, int input, int output, int tipFunctie) throws SQLException{
        CallableStatement temp;
        temp = conn.prepareCall("{call ? := java_rm_calc(?, ?)}");
        temp.registerOutParameter(1, Types.VARCHAR);
        temp.setString(2, IP);
        temp.setInt(3, port);
        temp.execute();
        String outPLSQL = temp.getString(1);
        
        alg.removeFunctie(input, output, tipFunctie);
        return "True".equals(outPLSQL);
    }
    
    public static void main(String[] args) throws SQLException {
        IPDataBaseV2 x = new IPDataBaseV2();
        ArrayList<ClasaSpeciala> out = new ArrayList<>();
        out = x.getList();
        int contor = 0;
        for(ClasaSpeciala p : out)
        {          System.out.println("Input: " + p.getInput() + " " + "Output: "+ p.getOutput() + " " + "Tip Functie: " + p.getTipFunctie() + " ");
                   contor ++;
        }
        System.out.println(contor);
        boolean fd;
        fd = x.modifyStatus(6801);
        System.out.print(fd);
        ArrayList<String> wer;
        wer = x.getStatus(1);
        System.out.print(wer);
        boolean re;
        //re = x.removeFunction("88", 7775);
        System.out.println(re);
    }
}
