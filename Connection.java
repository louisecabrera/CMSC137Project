import java.io.*;
import java.net.*;
import java.util.*;

public class Connection {

	private Socket client;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean alive;

	public Connection(Socket socket) {
		client = socket;

		try {
			in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
		} catch(IOException e) {
			// e.printStackTrace();
		}

		alive = true;
	}

	// reads input and puts into input stream
	public String read() {
		try {
			return in.readUTF();
		} catch(IOException e) {
			// e.printStackTrace();
		}

		return null;
	}

	// writes message to output stream
	public void write(String message) {
		try {
			out.writeUTF(message);
		} catch(IOException e) {
			// e.printStackTrace();
		}
	}

	// clears output stream
	public void flush() {
		try {
			out.flush();
		} catch(IOException e) {
			// e.printStackTrace();
		}
	}

	// closes connections
	public boolean close() {
		boolean temp = true;

		try {
			in.close();
			out.close();
			client.close();
			alive = false;
		} catch(IOException e) {
			// e.printStackTrace();
			temp = false;
		} 

		return temp;
	}

	public boolean isAlive() {
		return alive;
	}
}