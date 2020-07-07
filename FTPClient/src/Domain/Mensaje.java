package Domain;

import java.io.File;
import java.io.Serializable;

public class Mensaje implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;
	private File archivo, archivoPeticion;
	private String mensaje;
	private File[] archivos;
	
	public Mensaje(String tipo, File archivo, File archivoPeticion, String mensaje, File[]archivos) {
		super();
		this.tipo = tipo;
		this.archivo = archivo;
		this.archivoPeticion = archivoPeticion;
		this.mensaje = mensaje;
		this.archivos = archivos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public File getArchivo() {
		return archivo;
	}

	public void setArchivo(File archivo) {
		this.archivo = archivo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public File getArchivoPeticion() {
		return archivoPeticion;
	}

	public void setArchivoPeticion(File archivoPeticion) {
		this.archivoPeticion = archivoPeticion;
	}

	public File[] getArchivos() {
		return archivos;
	}

	public void setArchivos(File[] archivos) {
		this.archivos = archivos;
	}
	
	
	
}
