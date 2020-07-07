package Domain;

import java.io.Serializable;

public class Envio implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int llave;
	private byte[] mensaje;
	public Envio(int llave, byte[] mensaje) {
		super();
		this.llave = llave;
		this.mensaje = mensaje;
	}
	public int getLlave() {
		return llave;
	}
	public void setLlave(int llave) {
		this.llave = llave;
	}
	public byte[] getMensaje() {
		return mensaje;
	}
	public void setMensaje(byte[] mensaje) {
		this.mensaje = mensaje;
	}
	
}
