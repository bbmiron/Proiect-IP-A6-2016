package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public Connection conn;
    private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private Statement stmt;
    String USER = "student";
    String PASS = "student";

    public Database() throws SQLException{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    public void insert(String sql) throws SQLException {
        System.out.println("Inserting records into the table...");
        stmt = conn.createStatement();
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Inserted data on database Computers");
        rs.close();
    }

    public void delete(String sql) throws SQLException {
        System.out.println("Deleting records into the table...");
        stmt = conn.createStatement();
        System.out.println(sql);
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Deleted data on database Computers");
        rs.close();
    }
}
