package ChatPackage;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Declaracion de variable y elementos que se usan en la parte grafica 
 * y el acceso a sockets
 */
public class ChatClient {
	JFrame ventana_chat = null;
	JButton btn_enviar = null;
	JTextField txt_mensaje = null;
	JTextArea area_chat = null;
	JTextField ip = null;
	JTextField puerto = null;
	JPanel contenedor_areachat = null;
	JPanel contenedor_btntxt = null;
	JPanel contenedor_ip = null;
	JPanel contenedor_puerto = null;
	JScrollPane scroll = null;
	ServerSocket servidor = null;
	Socket socket = null;
	BufferedReader lector = null;
	PrintWriter escritor = null;
	
	public ChatClient() {
		hacerInterfaz();
	}
	
	/**
	 * Creacion de la interfaz 
	 */
	public void hacerInterfaz() {
		ventana_chat = new JFrame("Servidor");
		btn_enviar = new JButton("Enviar");
		txt_mensaje = new JTextField(20);
		ip = new JTextField(4);
		puerto = new JTextField(4);
		area_chat = new JTextArea(12,20);
		scroll = new JScrollPane(area_chat);
		contenedor_ip = new JPanel();
		contenedor_ip.setLayout(new GridLayout(1,1));
		contenedor_puerto = new JPanel();
		contenedor_puerto.setLayout(new GridLayout(2,1));
		contenedor_areachat = new JPanel();
		contenedor_areachat.setLayout(new GridLayout(1,2));
		contenedor_areachat.add(scroll);
		contenedor_btntxt = new JPanel();
		contenedor_btntxt.setLayout(new GridLayout(1,3));
		contenedor_btntxt.add(txt_mensaje);
		contenedor_btntxt.add(btn_enviar);
		contenedor_ip.add(ip);
		contenedor_puerto.add(puerto);
		ventana_chat.setLayout(new BorderLayout());
		ventana_chat.add(contenedor_ip,BorderLayout.NORTH);
		ventana_chat.add(contenedor_puerto,BorderLayout.AFTER_LAST_LINE);
		ventana_chat.add(contenedor_areachat,BorderLayout.CENTER);
		ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
		ventana_chat.setBounds(600,300,280,350);
		ventana_chat.setVisible(true);
		ventana_chat.setResizable(false);
		ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	/**
	 * Creacion de instancia que permite comunicarse con la interfaz y metodos
	 */
	public static void main(String[] args) {
			
			new ChatClient();
		
	}
}

