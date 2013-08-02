package BD;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class Ligacao {
    private static Connection derby = null;
    private static Statement stmt = null;
    private static final String DB_URL = "jdbc:derby:C:\\TEMP\\trabalho1";

    public static Connection getConnection() {
		if (derby == null)
			createConnection();
		return derby;
	}
    
    public static void createConnection()
    {
        try{
            Properties prop = new Properties();
            prop.put("user", "joao");
            prop.put("password", "joao");
            prop.put("create", "true");
            derby = DriverManager.getConnection(DB_URL, prop);
            System.out.println("A ligar...");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        if (derby==null){
            try{
                getConnection();
                stmt = derby.createStatement();
                stmt.close();
                derby.close();
            }catch(SQLException e1){
                e1.printStackTrace();
                System.err.println(e1.getMessage());
            }catch(Exception e2){
                e2.printStackTrace();
                System.err.println(e2.getMessage());
            }
            System.out.println("Desligado!");
        }
    }

    public Statement createStatement()
    {
        Statement st = null;
        try{
            st = derby.createStatement();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return st;
    }

    public void closeLigacao()
    {
        if (derby==null){
            try{
                getConnection();
                stmt = derby.createStatement();
                stmt.close();
                derby.close();
            }catch(SQLException e1){
                e1.printStackTrace();
                System.err.println(e1.getMessage());
            }catch(Exception e2){
                e2.printStackTrace();
                System.err.println(e2.getMessage());
            }
            derby = null;
            System.out.println("Desligado!");
        }
    }
}