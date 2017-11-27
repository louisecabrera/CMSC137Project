import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JPanel {

	JTextField typeArea;
	JTextArea viewArea;

	private ServerSocket serverSocket;
	public LinkedList<Client> clients = new LinkedList<Client>();
	private ConnectionListener connectionListener;

	public Server(int port) {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		try {
			serverSocket = new ServerSocket(port);
		} catch(IOException e) { }

		connectionListener = new ConnectionListener(this);

		typeArea = new JTextField(20);
		viewArea = new JTextArea(20,20);
		viewArea.setEditable(false);
		viewArea.setLineWrap(true);

		// typeArea.addKeyListener(new KeyListener(){
		// 	public void keyPressed(KeyEvent e){
				
		// 	}
		// 	public void keyReleased(KeyEvent e){ }
		// 	public void keyTyped(KeyEvent e){ }
		// });

		c.fill= GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(viewArea, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		add(typeArea, c);

		setVisible(true);
	}

	// reads input of server, prints on terminal, then sends to all clients
	public void start(String username) throws IOException {
		connectionListener.start();

		BufferedReader In = new BufferedReader(new InputStreamReader(System.in));
		String input;

		// reads line and checks if not null; also checks if connection listener is still up
		while(((input=In.readLine()) != null) && connectionListener.isAlive()) {
			if(input.equalsIgnoreCase("/q")) {
				break;
			} else {
				// prints server input on terminal
				System.out.println(username+": " + input);

				// sends server input to all open clients
				for(Client c : clients) {
					c.send(username+": " + input);
				}
			}
		}

		stop();
	}

	// stops the connection and closes server
	public void stop() throws IOException{
		// shuts down listener to stop any client from connecting
		connectionListener.stop();

		// closes all client sessions
		for(Client c : clients) {
			c.closeSession();
		}

		System.out.println("Server closed.");
	}


	// adds a new client connection to the server
	public synchronized void addConnection(Connection connection) {
		Client c = new Client(connection, clients);
		clients.add(c);
		c.startSession();
		System.out.println("New Client Connected.");
	}

	// return socket
	public ServerSocket getSocket() {
		return serverSocket;
	}

	public int getNumberOfClients(){
		return clients.size();
	}

	// public static void main(String[] args) throws IOException{
		
	// 	Server s = new Server(2222);
	// 	s.start();	// server start
	// }
}