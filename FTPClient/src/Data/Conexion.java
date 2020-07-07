package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Conexion {
	private String host, user, pass, db, url;
	private Connection conexion;
	
	public Conexion() {
		host = "163.178.107.10";
		user = "laboratorios";
		pass = "UCRSA.118";
		db = "proyecto_redes_B61776";
		url = "jdbc:mysql://"+host+"/"+db;
	}
	
	public void conectar() {
		try {
			
			Class.forName("com.mysql.jdbc.Connection");
			conexion = (Connection) DriverManager.getConnection(url, user, pass);
			
			if(conexion != null) {
				System.out.println("Conexion Exitosa");
			}else {
				System.out.println("Conexion Fallida");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet consultar(String sql) {
		ResultSet rs = null;
		
		try {
			
			conectar();
			Statement estado = conexion.createStatement();
			rs = estado.executeQuery(sql);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void ejecutar(String sql) {
		try {
			
			conectar();
			Statement estado = conexion.createStatement();
			estado.execute(sql);
			
		}catch (SQLException e) {
			JOptionPane.showMessageDialog(null,"Este cliente posee contratos en el sistema", "Error", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
