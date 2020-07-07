package Data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;

import Domain.Envio;
import Domain.Mensaje;

public class Server {
	private ServerSocket server;
	private ArrayList<String> clientes;
	
	public Server(int puerto, JLabel l) {
		clientes = new ArrayList<String>();
		aceptarConexiones(puerto, l);
	}
	
	public void aceptarConexiones(int puerto, JLabel l) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					server = new ServerSocket(puerto);
					while(true) {
						l.setText("Esperando Clientes...");
						Socket nuevo = server.accept();
						String aux = recibirUser(nuevo);
						l.setText("Usuario: "+aux+" ,conectado");
						l.revalidate();
						l.repaint();
						enviar(aux, nuevo);
						recibir(nuevo, aux);
						clientes.add(aux);
					}
				} catch (IOException e) {
					System.out.println("No se levanto el server: "+e.getMessage());
				}
			}
		}).start();
	}
	
	public void enviar(String username, Socket socket) {
		File file = new File("C:\\Users\\Randall\\Desktop\\FTP\\"+username);
		if(!file.exists()) {
			file.mkdirs();
			System.out.println("Directorio creado");
		}
		
		try{
            
            Socket enviar = socket;
            ObjectOutputStream flujoSalida = new ObjectOutputStream(enviar.getOutputStream());
            File filelist[] = file.listFiles();
            
            int random = (int)(Math.random()*20)+1;
            Mensaje m = new Mensaje("lista", null, null,"",filelist);
            Envio e = new Envio(random, encriptar(toBytes(m), random));
            
            flujoSalida.writeObject(e);
            
        }catch(IOException e){
            System.err.println("El servidor no pudo reenviar "+e);
        }
	}
	
	public void recibir(Socket cliente, String user) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) { 
						Socket recibir = cliente;
						ObjectInputStream flujoEntrada = new ObjectInputStream(recibir.getInputStream());
						
						Envio envio = (Envio) flujoEntrada.readObject();
						Mensaje mensaje = toObject(desencriptar(envio.getMensaje(), envio.getLlave()));
						
						if(mensaje.getTipo().equals("archivo")) {
							String nombre = mensaje.getArchivo().getName();
							File newFile = new File("C:\\Users\\Randall\\Desktop\\FTP\\"+user+"\\"+nombre);
							
							FileInputStream input = new FileInputStream(mensaje.getArchivo());
							FileOutputStream output = new FileOutputStream(newFile);
							
							byte[] buffer = new byte[1024];
							int length;
							while((length = input.read(buffer))>0) {
								output.write(buffer,0,length);
							}
							//flujoEntrada.close();
							input.close();
							output.close();
							System.out.println("Archivo recibido correctamente");
							enviar(user, cliente);
						}else {
							if(!mensaje.getTipo().equals("drive")) {
								File pedido = new File("C:\\Users\\Randall\\Desktop\\FTP\\"+user+"\\"+mensaje.getMensaje());
								
								ObjectOutputStream flujoSalida = new ObjectOutputStream(recibir.getOutputStream());
								
								int random = (int)(Math.random()*20)+1;
								
								Mensaje m = new Mensaje("pedido", null, pedido, "Archivo "+pedido.getName()+" guardado con exito", null);
								Envio e = new Envio(random, encriptar(toBytes(m), random));
								
								flujoSalida.writeObject(e);
								flujoSalida.flush();
								flujoSalida.close();
							}
							
						}
						
						if(mensaje.getTipo().equals("drive")) {
							File[] lista = mensaje.getArchivos();
							File file = new File("C:\\Users\\Randall\\Desktop\\FTP\\"+user);
							if(file.exists()) {
								for (int i = 0; i < lista.length; i++) {
									File f = new File("C:\\Users\\Randall\\Desktop\\FTP\\"+user+"\\"+lista[i].getName());
									if(!f.exists()) {
										FileInputStream input = new FileInputStream(lista[i]);
										FileOutputStream output = new FileOutputStream(f);
										
										byte[] buffer = new byte[1024];
										int length;
										while((length = input.read(buffer))>0) {
											output.write(buffer,0,length);
										}
										input.close();
										output.close();
										//System.out.println("Archivo recibido correctamente");
									}
									enviar(user, cliente);
								}
								
							}
							
						}
						
						
						
					}
					
					
				}catch(IOException  | ClassNotFoundException  e) {
					System.out.println("El servidor no pudo recibir peticion de archivo: "+e.getMessage());
				}
				
			}
		}).start();
	}
	
	public String recibirUser(Socket cliente) {
		String salida = "";
		try {
			Socket recibir = cliente;
			ObjectInputStream flujoEntrada = new ObjectInputStream(recibir.getInputStream());
			salida = (String) flujoEntrada.readObject();
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("Error recibiendo usuario: "+e.getMessage());
		}
		return salida;
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

	public ArrayList<String> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<String> clientes) {
		this.clientes = clientes;
	}
	
	

}
