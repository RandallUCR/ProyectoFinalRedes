package Data;

import java.sql.ResultSet;
import java.util.ArrayList;

import Domain.Usuario;

public class DBUsuarios extends Conexion {
	
	public DBUsuarios() {
		
	}
	
	public ArrayList<Usuario> getDatos(){
		ArrayList<Usuario>datos = new ArrayList<Usuario>();
		
		try {
			
			ResultSet rs = consultar("call consultar();");
			
			while(rs.next()) {
				
				Usuario u = new Usuario(rs.getInt("id_usuario"),
						rs.getString("nombre"),
						rs.getString("password"));
				
				datos.add(u);
				
			}
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		
		return datos;
	}
	
	public void insertar(Usuario u) {
		ejecutar("call registrar('"+u.getNombre()+"','"+u.getPassword()+"');");
	}
	
	public void eliminar(int id) {
		ejecutar("call eliminar("+id+");");
	}
}
