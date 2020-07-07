package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
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
import Data.Server;
import Domain.Usuario;

public class GUI extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Server server;
	private lnUsuarios ln;
	
	private DefaultTableModel modelo;
	private JTable tabla;
	private JScrollPane scroll;
	
	private DefaultTableModel modelo2;
	private JTable tabla2;
	private JScrollPane scroll2;
	
	private JButton iniciar;
	private JTextField puerto;
	private JTextField usuario;
	private JTextField password;
	private JButton insertar;
	private JButton eliminar;
	
	private JLabel label;

	public GUI() {
		// TODO Auto-generated constructor stub
		ln = new lnUsuarios();
		
		JPanel fondo = crearPanel(new BorderLayout(), Color.GRAY,0,0);
		
		JPanel arriba = crearPanel(new FlowLayout(), Color.GRAY,0,80);
		JPanel izquierda = crearPanel(new BorderLayout(), Color.DARK_GRAY,400,0);
		JPanel centro = crearPanel(new BorderLayout(), Color.GRAY,0,0);
		
		fondo.add(centro,BorderLayout.CENTER);
		fondo.add(arriba,BorderLayout.PAGE_START);
		fondo.add(izquierda, BorderLayout.LINE_START);
		
		JPanel izArriba = crearPanel(new BorderLayout(), null, 0, 50);
		JPanel izCentro = crearPanel(new BorderLayout(), null, 0, 0);
		
		izquierda.add(izArriba,BorderLayout.PAGE_START);
		izquierda.add(izCentro, BorderLayout.CENTER);
				
		JLabel lConectados = crearLabel("Usuarios Conectados", null, 20);
		
		izArriba.add(lConectados, BorderLayout.CENTER);
		
		modelo = new DefaultTableModel();
		tabla = new JTable(modelo);
		scroll = new JScrollPane(tabla);
		
		izCentro.add(scroll);
		
		iniciar = crearBoton("Iniciar Servidor", Color.darkGray, Color.green);
		iniciar.addActionListener(this);
		puerto = crearTextField();
		puerto.setPreferredSize(new Dimension(200,40));
		JLabel l = crearLabel("Puerto: ", Color.WHITE, 40);
		label = crearLabel("", Color.red, 15);
		
		arriba.add(l);
		arriba.add(puerto);
		arriba.add(iniciar);
		arriba.add(label);
		
		JPanel cenIzquierda = crearPanel(new FlowLayout(), Color.DARK_GRAY, 350, 0);
		JPanel ceCentro = crearPanel(new BorderLayout(), null, 0, 0);
		JPanel ceAbajo = crearPanel(new FlowLayout(), Color.gray, 0, 50);
		
		JPanel registro = crearPanel(new GridLayout(4, 1, 10, 10), null, 300, 200);
		cenIzquierda.add(registro);
		
		centro.add(cenIzquierda, BorderLayout.LINE_START);
		centro.add(ceCentro, BorderLayout.CENTER);
		centro.add(ceAbajo, BorderLayout.PAGE_END);
		
		usuario = crearTextField();
		password = crearTextField();
		JLabel lusuario = crearLabel("Usuario", Color.WHITE, 30);
		JLabel lpassword = crearLabel("Contraseña", Color.WHITE, 30);
		insertar = crearBoton("Registar", Color.GRAY, Color.GREEN);
		insertar.addActionListener(this);
		
		registro.add(lusuario);
		registro.add(usuario);
		registro.add(lpassword);
		registro.add(password);
		cenIzquierda.add(insertar);
		
		modelo2 = new DefaultTableModel();
		tabla2 = new JTable(modelo2);
		scroll2 = new JScrollPane(tabla2);
		
		ceCentro.add(scroll2, BorderLayout.CENTER);
		
		eliminar = crearBoton("Eliminar", Color.DARK_GRAY, Color.RED);
		eliminar.addActionListener(this);
		
		ceAbajo.add(eliminar);
		
		this.add(fondo, BorderLayout.CENTER);
		
		cargarTabla(modelo2, ln.getRowsUsuarios(), ln.columnasUsuario());
		tabla2.getColumnModel().getColumn(0).setMaxWidth(0);
		tabla2.getColumnModel().getColumn(0).setMinWidth(0);
		tabla2.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tabla2.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
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
	
	public void cargarTablaServer() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					cargarTabla(modelo, ln.getRowsServer(server.getClientes()), new String[] {"Conectados: "});
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(iniciar == e.getSource()) {
			server = new Server(Integer.parseInt(puerto.getText()), label);
			cargarTablaServer();
		}
		
		if(insertar == e.getSource()) {
			Usuario u = new Usuario(-1, usuario.getText(), password.getText());
			ln.insertar(u);
			cargarTabla(modelo2, ln.getRowsUsuarios(), ln.columnasUsuario());
			tabla2.getColumnModel().getColumn(0).setMaxWidth(0);
			tabla2.getColumnModel().getColumn(0).setMinWidth(0);
			tabla2.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
			tabla2.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
			usuario.setText("");
			password.setText("");
		}
		
		if(eliminar == e.getSource()) {
			int fila = tabla2.getSelectedRow();
			if(fila > -1) {
				ln.eliminar((int)modelo2.getValueAt(fila, 0));
				cargarTabla(modelo2, ln.getRowsUsuarios(), ln.columnasUsuario());
				tabla2.getColumnModel().getColumn(0).setMaxWidth(0);
				tabla2.getColumnModel().getColumn(0).setMinWidth(0);
				tabla2.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
				tabla2.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
			}else {
				JOptionPane.showMessageDialog(null, "Seleccione una fila");
			}
		}
		
	}

}
