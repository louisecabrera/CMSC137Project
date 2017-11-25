import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	private ServerSocket serverSocket;
	public List<Client> clients = new ArrayList<Client>();
	private ConnectionListener connectionListener;

	public Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch(IOException e) { }

		connectionListener = new ConnectionListener(this);
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

	// public static void main(String[] args) throws IOException{
		
	// 	Server s = new Server(2222);
	// 	s.start();	// server start
	// }
}