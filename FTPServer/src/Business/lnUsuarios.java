package Business;

import java.util.ArrayList;


import Data.DBUsuarios;
import Domain.Usuario;

public class lnUsuarios {
	
	private DBUsuarios db;
	
	public lnUsuarios() {
		db = new DBUsuarios();
	}
	
	public ArrayList<Usuario> getDatos(){
		return db.getDatos();
	}
	
	public void insertar(Usuario u) {
		db.insertar(u);
	}
	
	public void eliminar(int id) {
		db.eliminar(id);
	}
	
	public String[] columnasUsuario() {
		return new String[] {"idUsuario","Nombre","Contraseña"};
	}
	
	public Object[][] getRowsUsuarios(){
		ArrayList<Usuario> datos = getDatos();
		Object[][] rows = new Object[datos.size()][columnasUsuario().length];
		
		int i = 0;
		
		for (Usuario u : datos) {
			
			rows[i][0] = u.getIdUsuario();
			rows[i][1] = u.getNombre();
			rows[i][2] = u.getPassword();
			
			i++;
		}
		
		return rows;
	}
	
	public Object[][] getRowsServer(ArrayList<String> data){
		ArrayList<String> datos = data;
		Object[][] rows = new Object[datos.size()][1];
		
		int i = 0;
		
		for (String u : datos) {
			
			rows[i][0] = u;
			
			i++;
		}
		
		return rows;
	}
}
