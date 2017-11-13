import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectionListener implements Runnable {

	private Thread t;
	private Server server;
	private ServerSocket serverSocket;
	private boolean running;

	public ConnectionListener(Server server) {
		this.server = server;
		serverSocket = server.getSocket();
		running = false;
	}

	// Thread that adds new connections to the server
	public void run() {
		System.out.println("Server open at " + serverSocket.getLocalPort());

		try {
			while(running) {
				Socket s = serverSocket.accept();
				Connection connection = new Connection(s);
				server.addConnection(connection);
			}
		} catch(IOException e) { }
	}

	// starts listener to be able to accept new connections
	public synchronized void start() {
		if(running)
			return;

		running = true;
		t = new Thread(this);
		t.start();
	}

	// stops and closes socket
	public synchronized void stop() {
		if(!running)
			return;

		running = false;

		try {
			serverSocket.close();
		} catch(IOException e) { }	

		try {
			t.join();
		} catch(InterruptedException e) { }
	}

	public boolean isAlive() {
		return running;
	}
}