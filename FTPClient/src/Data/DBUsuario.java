package Data;

import java.sql.ResultSet;
import java.util.ArrayList;


public class DBUsuario extends Conexion {
	
	public DBUsuario() {
		
	}
	
	public String getDatos(String user, String pass){
		String nombre = "";
		
		try {
			
			ResultSet rs = consultar("call login('"+user+"','"+pass+"');");
			
			while(rs.next()) {
				
				nombre = rs.getString("nombre");
				
			}
			
		} catch (Exception e) {
		System.out.println("error "+e.getMessage());
		}
		
		return nombre;
	}
	
	public String getDrive(String user) {
		String salida = "";
		try {
			
			ResultSet rs = consultar("call drive('"+user+"');");
			
			while(rs.next()) {
				
				salida = rs.getString(1);
				
			}
			
		} catch (Exception e) {
		System.out.println("error "+e.getMessage());
		}
		
		return salida;
	}
	
	public void actDrive(String user, String ruta) {
		ejecutar("call act_drive('"+user+"','"+ruta+"')");
	}
}
