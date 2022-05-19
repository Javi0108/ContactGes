package fct.contactges;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.stage.Stage;


public class AccesoBD {
	
	private static Stage stage;
	
	static String url = "jdbc:mysql://localhost:3306/gescon";
	static String usr = "root";
	static String pswd = "";
	static Connection con;
	
	public static void main(String[] args) throws Exception {			
		url = "jdbc:mysql://localhost/gescon";
		usr = "root";
		pswd = "";
		con = DriverManager.getConnection(url, usr, pswd);
	}
	
	public static Connection getConnection() {
		return con;
	}

}
