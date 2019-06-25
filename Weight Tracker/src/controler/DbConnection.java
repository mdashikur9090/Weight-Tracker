package controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
	
	private  final String driver = "com.mysql.jdbc.Driver";
    private  final String url = "jdbc:mysql://localhost/mainul_kobir";
    private  final String user = "root";
    private  final String pass = "";
    
    public Connection con = null;
    public Statement statement =null;

    public DbConnection() { 
    }
    
    public void getCon() {
	    	try {
	            Class.forName(driver);
	            con = DriverManager.getConnection(url, user, pass);
	            statement = con.createStatement();
	        } catch (Exception ex) {
	            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
	        }
    }
	

}
