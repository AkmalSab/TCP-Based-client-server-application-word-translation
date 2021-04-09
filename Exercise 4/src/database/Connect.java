/**
 * 
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author akmal
 *
 */
public class Connect {
	
	static String driver;
	static String dbName;
	static String connectionURL;
	static String username;
	static String password;
	
	public Connect() {
		// TODO Auto-generated constructor stub
		
		driver = "com.mysql.cj.jdbc.Driver";
		connectionURL ="jdbc:mysql://localhost:3306/";
		dbName = "translation";
		username = "root";
		password = "";
	}
	
	public Connection getConnection() throws Exception {
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(connectionURL+dbName+"?serverTimezone=UTC",username,password);
		return connection;
	}
	
	public void insert() throws Exception {
		final String var1 = "Tamil";
		final String query = "INSERT INTO LANGUAGES (language) VALUES ('" + var1 + "')";
		try {
			Connection conn = getConnection();
			PreparedStatement posted = conn.prepareStatement(query);
			posted.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.print("insert completed");
		}
	}
	
	public String retrieve() throws Exception {
		
		String output = null;
		
		try {
			Connection conn = getConnection();
			
			String word = "Good morning";
			
			String query = "SELECT translate from DICTIONARY WHERE word = '"+ word +"' and did = 1;";
			
			PreparedStatement select = conn.prepareStatement(query);
			
			ResultSet result = select.executeQuery();
			
			while(result.next()) {
				output = result.getString("translate");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.print("retrieve completed");
		}
		
		return output;
		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Connect db = new Connect();
		
		try (Connection conn = db.getConnection())
		{
			if (conn != null) 
			{
				System.out.println("Database successfully connected!\n");
			}
		}
		catch (Exception e ) 
		{
			e.printStackTrace();
		}
		
		System.out.println(db.retrieve());
	}

}
