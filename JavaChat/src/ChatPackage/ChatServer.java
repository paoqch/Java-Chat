package ChatPackage;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Declaracion de variable y elementos que se usan en la parte grafica 
 * y el acceso a sockets
 */
public class ChatServer {
	JFrame ventana_chat = null;
	JButton btn_enviar = null;
	JTextField txt_mensaje = null;
	JTextField ip = null;
	JTextField puerto = null;
	JTextArea area_chat = null;
	JPanel contenedor_areachat = null;
	JPanel contenedor_btntxt = null;
	JPanel contenedor_ip = null;
	JPanel contenedor_puerto = null;
	JScrollPane scroll = null;
	ServerSocket servidor = null;
	Socket socket = null;
	BufferedReader lector = null;
	PrintWriter escritor = null;
	
	public ChatServer() {
		hacerInterfaz();
	}

	/**
	 * Creacion de la interfaz 
	 */
	public void hacerInterfaz() {
		ventana_chat = new JFrame("Cliente");
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
		ventana_chat.add(contenedor_puerto,BorderLayout.NORTH);
		ventana_chat.add(contenedor_areachat,BorderLayout.CENTER);
		ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
		ventana_chat.setBounds(600,300,280,350);
		ventana_chat.setVisible(true);
		ventana_chat.setResizable(false);
		ventana_chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	/**
	 * Creacion del metodo leer que permite visualizar el mensaje, ip, puerto 
	 */
	public void leer() {
		Thread leer_hilo = new Thread(new Runnable() {
			public void run() {
				try {
					lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(true) {
							String ip_recibida = lector.readLine();
							String puerto_recibido = lector.readLine();
							String mensaje_recibido = lector.readLine();
							area_chat.append("IP: "+ip_recibida+" Port: "+puerto_recibido+" Mensaje Servidor: "+mensaje_recibido+"\n");
								
						}
					
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
		});
		leer_hilo.start();
	}


	/**
	 * Creacion de instancia que permite comunicarse con la interfaz y metodos
	 */
	public static void main(String[] args) {
		new ChatServer();


	}

}

