package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Business.lnUsuarios;
import Data.Cliente;
import Domain.Mensaje;


public class GUI extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton iniciar, abrirArchivo, enviarArchivo, pedir, drive;
	private JLabel label, errorpedir;
	private JTextField puerto, user, pass, archivo;
	
	private DefaultTableModel model;
	private JTable tabla;
	private JScrollPane scroll;
	
	private JFileChooser chooser,chooser2,chooserDrive;
	
	private lnUsuarios ln;
	private String nombre, ruta, driveS;
	
	private Cliente cliente;
	
	public GUI() {
		// TODO Auto-generated constructor stub
		ln = new lnUsuarios();
		nombre = "";
		ruta = "";
		driveS = "";
		
		JPanel fondo = crearPanel(new BorderLayout(), Color.GRAY,0,0);
		
		JPanel arriba = crearPanel(new FlowLayout(), Color.GRAY,0,80);
		JPanel izquierda = crearPanel(new BorderLayout(), Color.DARK_GRAY,400,0);
		JPanel centro = crearPanel(new FlowLayout(), new Color(185,183,183),0,0);
		JPanel abajo = crearPanel(new FlowLayout(), Color.DARK_GRAY, 0, 50);
		centro.setBorder(new EmptyBorder(250, 0, 0, 0));
		
		fondo.add(centro,BorderLayout.CENTER);
		fondo.add(arriba,BorderLayout.PAGE_START);
		fondo.add(izquierda, BorderLayout.LINE_START);
		fondo.add(abajo, BorderLayout.PAGE_END);
		
		iniciar = crearBoton("Iniciar Cliente", Color.darkGray, Color.green);
		pedir = crearBoton("Solicitar Archivo", Color.gray, Color.green);
		iniciar.addActionListener(this);
		puerto = crearTextField();
		user = crearTextField();
		pass = crearTextField();
		puerto.setPreferredSize(new Dimension(150,40));
		user.setPreferredSize(new Dimension(150,40));
		pass.setPreferredSize(new Dimension(150,40));
		JLabel l = crearLabel("Puerto: ", Color.WHITE, 30);
		JLabel l1 = crearLabel("Usuario: ", Color.WHITE, 30);
		JLabel l2 = crearLabel("Contraseña: ", Color.WHITE, 30);
		label = crearLabel("", Color.red, 15);
		
		arriba.add(l1);
		arriba.add(user);
		arriba.add(l2);
		arriba.add(pass);
		arriba.add(l);
		arriba.add(puerto);
		arriba.add(iniciar);
		arriba.add(label);
		
		JLabel larchivos = crearLabel("Archivos subidos", null, 20);
		JPanel izArriba = crearPanel(new BorderLayout(), null, 0, 50);
		JPanel izCentro = crearPanel(new BorderLayout(), null, 0, 0);
		
		izquierda.add(izArriba,BorderLayout.PAGE_START);
		izquierda.add(izCentro, BorderLayout.CENTER);
		
		model = new DefaultTableModel();
		tabla = new JTable(model);
		scroll = new JScrollPane(tabla);
		
		izArriba.add(larchivos, BorderLayout.CENTER);
		izCentro.add(scroll, BorderLayout.CENTER);
		
		archivo = crearTextField();
		archivo.setEnabled(false);
		archivo.setPreferredSize(new Dimension(300,50));
		abrirArchivo = crearBoton("Seleccionar Archivo", Color.darkGray, Color.yellow);
		abrirArchivo.addActionListener(this);
		enviarArchivo = crearBoton("Subir Archivo", Color.darkGray, Color.green);
		enviarArchivo.addActionListener(this);
		drive = crearBoton("Drive", Color.darkGray, Color.blue);
		drive.addActionListener(this);
		
		centro.add(archivo);
		centro.add(abrirArchivo);
		centro.add(enviarArchivo);
		centro.add(drive);
		
		chooser = new JFileChooser();
		chooser2 = new JFileChooser();
		chooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		chooserDrive = new JFileChooser();
		chooserDrive.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		
		abajo.add(pedir);
		errorpedir = crearLabel("", Color.RED, 15);
		abajo.add(errorpedir);
		pedir.addActionListener(this);
		
		this.add(fondo, BorderLayout.CENTER);
		enviarDrive();
	}
	
	public JPanel crearPanel(LayoutManager l, Color fondo, int w, int h) {
		JPanel p = new JPanel();
		p.setLayout(l);
		p.setBackground(fondo);
		p.setPreferredSize(new Dimension(w,h));
		return p;
	}
	
	public JLabel crearLabel(String texto, Color c, int size) {
		JLabel l = new JLabel(texto);

		if(c == null) {
			c = Color.white;
		}

		l.setForeground(c);
		l.setFont(new Font("Century Gothic", Font.BOLD, size));

		return l;
	}
	
	public JButton crearBoton(String texto, Color c1, Color c2) {
		JButton b = new JButton(texto);

		b.setFont(new Font("Century Gothic" , Font.BOLD, 20));
		b.setForeground(Color.WHITE);
		b.setBackground(c1);
		b.setBorder(new EmptyBorder(10 , 10 , 10 , 10));
		b.setFocusable(false);
		b.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

				b.setBackground(c1);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				b.setBackground(c1);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				b.setBackground(c2);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		return b;
	}
	
	public JTextField crearTextField() {
		JTextField t = new JTextField();
		t.setBorder(new EmptyBorder(10 , 10 , 10 , 10));
		t.setFont(new Font("Century Gothic", Font.PLAIN, 15));

		return t;
	}
	
	public void cargarTabla(DefaultTableModel modelo, Object[][]data, String[]columns) {
		modelo.setDataVector(data, columns);
	}
	
	public void init() {
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setResizable(false);
	}
	
	public void cargar(DefaultTableModel modelo, Object[][]data, String[]columns) {
		modelo.setDataVector(data, columns);
	}
	
	public void cargarTablaServer() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					Mensaje m = cliente.recibir();
					if(m.getTipo().equals("lista")) {
						cargar(model, ln.getRowsCliente(m.getArchivos()), new String[] {"Archivos: "});
					}else {
						System.out.println("ruta2 "+ruta);
						//errorpedir.setText("ouo");
						errorpedir.setText(m.getMensaje());
						//errorpedir.setText("ouo");
					}
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
	public void enviarDrive() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					
					if(!driveS.equals("") && !driveS.equals("nada")) {
						cliente.enviarDrive(driveS);
					}
					
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}
	
public void accionesChooser(JFileChooser c) {
		
		if(c.showOpenDialog(this) == 0) {
			File a = c.getSelectedFile();
			archivo.setText(a.getPath());
			
		}
		
	}

public void accionesChooser2(JFileChooser c, String ar) {
	
	if(c.showOpenDialog(this) == 0) {
		File a = c.getSelectedFile();
		cliente.enviarPeticion(ar);
		cliente.ruta   = a.getPath();
		cliente.arch = a.getName();
		System.out.println("ruta1 "+ruta);
	}
	
}

public void accionesDrive() {
	
	if(chooserDrive.showOpenDialog(this) == 0) {
		File a = chooserDrive.getSelectedFile();
		String path = a.getPath().replace("\\", "\\\\");
		ln.actDrive(nombre, path);
		driveS=ln.getDrive(nombre);
	}
	
}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(abrirArchivo == e.getSource()) {
			accionesChooser(chooser);
		}
		
		if(enviarArchivo == e.getSource()) {
			if(nombre.equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(null, "Inicie sesion primero");
			}else {
				if(archivo.getText().length() > 0) {
					cliente.enviar(new File(archivo.getText()));
					archivo.setText("");
				}else {
					JOptionPane.showMessageDialog(null, "Seleccione un archivo");
				}
			}
		}
		
		if(iniciar == e.getSource()) {
			nombre = ln.getDatos(user.getText(), pass.getText());
			if(nombre.equalsIgnoreCase("")) {
				label.setText("Usuario o contraseña incorrectos");
			}else {
				label.setText("Bienvenido "+nombre);
				cliente = new Cliente("localhost", Integer.parseInt(puerto.getText()), label);
				cliente.enviarUser(nombre);
				driveS = ln.getDrive(nombre);
				cargarTablaServer();
			}
		}
		
		if(pedir == e.getSource()) {
			int fila = tabla.getSelectedRow();
			if(fila > -1) {
				String a = (String) model.getValueAt(fila, 0);
				accionesChooser2(chooser2, a);
			}else {
				JOptionPane.showMessageDialog(null, "Seleccione una fila");
			}
		}
		
		if(drive == e.getSource()) {
			accionesDrive();
		}
	}

}
