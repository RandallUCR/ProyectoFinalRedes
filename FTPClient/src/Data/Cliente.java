package Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JLabel;

import Domain.Envio;
import Domain.Mensaje;

public class Cliente {
	private Socket socket;
	private ObjectOutputStream emisor;
	private ObjectInputStream receptor;
	public String ruta, arch;
	
	public Cliente(String ip, int puerto, JLabel l) {
		// TODO Auto-generated constructor stub
		this.ruta = "";
		this.arch = "";
		try {
			socket = new Socket(ip, puerto);
		}catch(IOException e) {
			//System.out.println("No se levantó el cliente: "+e.getMessage());
			l.setText("Puerto Incorrecto");
		}
	}
	
	public void enviar(File file) {
		try {
			int random = (int)(Math.random()*20)+1;
			Mensaje m = new Mensaje("archivo", file, null,"",null);
			Envio e = new Envio(random, encriptar(toBytes(m), random));
			
			emisor= new ObjectOutputStream(socket.getOutputStream()); 
			emisor.writeObject(e);
			emisor.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enviarUser(String username) {
		try {
			emisor= new ObjectOutputStream(socket.getOutputStream());
			emisor.writeObject(username);
			emisor.flush();
		}catch(IOException e) {
			System.out.println("Error al enviar el user: "+e.getMessage());
		}
	}
	
	public Mensaje recibir() {
		Mensaje m = null;
        
        try{
            
            receptor = new ObjectInputStream(socket.getInputStream());
            Envio e = (Envio)receptor.readObject();
            System.out.println(e.getLlave());
            m = toObject(desencriptar(e.getMensaje(), e.getLlave()));
           // System.out.println(desencriptar(e.getMensaje(), e.getLlave()).length);
            if(m.getTipo().equals("pedido")) {
            	File file = m.getArchivoPeticion();
    			File newFile = new File(this.ruta+"\\"+file.getName());
    			FileInputStream input = new FileInputStream(file);
				FileOutputStream output = new FileOutputStream(newFile);
				
				byte[] buffer = new byte[1024];
				int length;
				while((length = input.read(buffer))>0) {
					output.write(buffer,0,length);
				}
				input.close();
				output.close();
            }
            
        }catch(IOException | ClassNotFoundException e){
            System.err.println("No se pudieron recibir los datos en cliente "+e.getMessage());
        }
        return m;
	}
	
	public void enviarPeticion(String archivo) {
		try {
			emisor = new ObjectOutputStream(socket.getOutputStream());
			int random = (int)(Math.random()*20)+1;
			Mensaje m = new Mensaje("text", null,null, archivo, null);
			Envio e = new Envio(random, encriptar(toBytes(m), random));
			
			emisor.writeObject(e);
			emisor.flush();
		}catch(IOException e) {
			System.out.println("Error al enviar peticion user: "+e.getMessage());
		}
	}
	
	public byte[] toBytes(Mensaje m) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream ob = new ObjectOutputStream(bo);
		ob.writeObject(m);
		ob.flush();
		byte[] x =  bo.toByteArray();
		return x;
	}
	
	public Mensaje toObject(byte[] objeto) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bs = new ByteArrayInputStream(objeto);
		ObjectInputStream m1 = new ObjectInputStream(bs);
		Mensaje m = (Mensaje) m1.readObject();
		return m;
	}
	
	public byte[] encriptar(byte[] objeto, int key) {
		byte[] salida = new byte[objeto.length];
		
		for (int i = 0; i < objeto.length; i++) {
			byte x = objeto[i];
			salida[i] = (byte)(x-key);
		}
		
		return salida;
	}
	
	public byte[] desencriptar(byte[] objeto, int key) {
		byte[] salida = new byte[objeto.length];
		
		for (int i = 0; i < objeto.length; i++) {
			int x = objeto[i];
			salida[i] = (byte)(x+key);
		}
		
		return salida;
	}
	
	public void enviarDrive(String path) {
		try {
			System.out.println(path);
			File file = new File(path);
			File[] lista = file.listFiles();
			
			emisor = new ObjectOutputStream(socket.getOutputStream());
			
			int random = (int)(Math.random()*20)+1;
			
			Mensaje m = new Mensaje("drive", null,null, null, lista);
			Envio e = new Envio(random, encriptar(toBytes(m), random));
			
			emisor.writeObject(e);
			emisor.flush();
			
		}catch(IOException e) {
			System.out.println("Error en drive "+e.getMessage());
		}
	}

}
