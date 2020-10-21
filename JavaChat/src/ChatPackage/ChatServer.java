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

import org.apache.log4j.Logger;

/**
 * Declaracion de variable y elementos que se usan en la parte grafica 
 * y el acceso a sockets
 */
public class ChatServer {
	static final Logger log = Logger.getLogger(ChatClient.class);
	JFrame ventana_chat = null;
	JButton btn_enviar = null;
	JTextField txt_mensaje = null;
	JTextField ip = null;
	
	JTextArea area_chat = null;
	JPanel contenedor_areachat = null;
	JPanel contenedor_btntxt = null;
	JPanel contenedor_ip = null;

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
		ventana_chat = new JFrame("Servidor");
		btn_enviar = new JButton("Enviar");
		txt_mensaje = new JTextField(20);
		ip = new JTextField(4);
		
		area_chat = new JTextArea(12,20);
		scroll = new JScrollPane(area_chat);
		contenedor_ip = new JPanel();
		contenedor_ip.setLayout(new GridLayout(1,1));
		
		contenedor_areachat = new JPanel();
		contenedor_areachat.setLayout(new GridLayout(1,2));
		contenedor_areachat.add(scroll);
		contenedor_btntxt = new JPanel();
		contenedor_btntxt.setLayout(new GridLayout(1,3));
		contenedor_btntxt.add(txt_mensaje);
		contenedor_btntxt.add(btn_enviar);
		contenedor_ip.add(ip);
		
		ventana_chat.setLayout(new BorderLayout());
		ventana_chat.add(contenedor_ip,BorderLayout.NORTH);
		ventana_chat.add(contenedor_areachat,BorderLayout.CENTER);
		ventana_chat.add(contenedor_btntxt,BorderLayout.SOUTH);
		ventana_chat.setBounds(600,300,280,350);
		ventana_chat.setVisible(true);
		ventana_chat.setResizable(false);
		ventana_chat.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		/**
		 * Creacion del acceso al socket para la comunicacion
		 * y lectura y escritura de mensajes
		 */
		Thread principal = new Thread(new Runnable() {
			public void run() {
				log.info("Iniciando chat servidor");
				try {
				socket = new Socket("127.0.0.1",9000);
				leer();
				escribir();	
				
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
				  
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}	
			}
		});
		principal.start();
		
	}
	/**
	 * Creacion del metodo leer que permite visualizar el mensaje, ip, puerto 
	 */
	public void leer() {
		Thread leer_hilo = new Thread(new Runnable() {
			public void run() {
				log.info("Conexión con el cliente");
				try {
					lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						while(true) {
							String ip_recibida = lector.readLine();
							String mensaje_recibido = lector.readLine();
							area_chat.append("IP: "+ip_recibida+" Port: 9000"+" Mensaje Cliente: "+mensaje_recibido+"\n");
							log.info("IP de conexión del cliente: "+ip_recibida);
								
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
	 * Creacion de metodo de escritura para obtener los datos a enviar
	 */
	public void escribir() {
		Thread escribir_hilo = new Thread(new Runnable() {
			
			public void run() {
				
				try {
					escritor = new PrintWriter(socket.getOutputStream(),true);
					btn_enviar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String enviar_ip = ip.getText();
							escritor.println(enviar_ip);
							
							String enviar_mensaje = txt_mensaje.getText();
							escritor.println(enviar_mensaje);
							txt_mensaje.setText("");
						}
					});
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
		});
		escribir_hilo.start();
	}
	
	/**
	 * Creacion de instancia que permite comunicarse con la interfaz y metodos
	 */
		public static void main(String[] args) {
			new ChatServer();
		}
		
}

