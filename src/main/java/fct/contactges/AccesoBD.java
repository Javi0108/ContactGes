package fct.contactges;

import java.sql.DriverManager;
import java.sql.SQLException;


public class AccesoBD {

	public static void main(String[] args) throws SQLException {
		App.url = "jdbc:mysql://localhost/bdhoteles03";
		App.usr = "root";
		App.pswd = "";
		App.con = DriverManager.getConnection(App.url, App.usr, App.pswd);
	}

}
