import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable {

	private Connection connection;
	private boolean alive;
	private List<Client> clients;
	private Thread t;

	public Client(Connection connection, List<Client> clients) {
		this.connection = connection;
		this.clients = clients;
		alive = false;
	}

	public void run() {
		while(connection.isAlive()) {
			String in = connection.read();
			if(in != null) {

				String[] messageList = in.split(" ");
				String message = messageList[1];

				if(message.equals("/q\n")){
					for(Client c : clients){
						if(c == this){
							c.closeSession();
						}
					}
					
				}

				System.out.print(in);

				for(Client c : clients){
					c.send(in);
				}
			} else {
				try {
					Thread.sleep(10);
				} catch(InterruptedException e) {
					// e.printStackTrace();
				}
			}
		}
	}

	// creates thread and starts client session
	public synchronized void startSession() {
		if(alive)
			return;

		alive = true;
		t = new Thread(this);
		t.start();
	}

	// closes client connection
	public synchronized void closeSession() {
		
		if(!alive)
			return;

		alive = false;
		
		try {
			System.out.println("Client closed.");
			connection.close();
			t.join();
		} catch(InterruptedException e) {
			// e.printStackTrace();
		}
	}

	// sends client/server input to output stream for reading
	public void send(String in) {
		connection.write(in);
		connection.flush();
	}

	public static void main(String[] args) {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		Scanner scanner = new Scanner(System.in);

		try (Socket socket = new Socket(serverName, port);
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			BufferedReader In = new BufferedReader(new InputStreamReader(System.in))) {

			// thread that reads input stream (gets input of other users)
			Thread input = new Thread(() -> {
				String msg;
				try {
					while((msg = in.readUTF()) != null) {
						System.out.println(msg);
					}
				} catch(Exception e) {
					// e.printStackTrace();
				}
			});
			input.start();

			// asks for username
			System.out.print("Enter name: ");
			String userName = scanner.nextLine();
			String msg;

			try {
				// loop that writes user input to stream
				while((msg = In.readLine()) != null) {
					
					if(msg.equals("/q")){
						break;
					}
					else{
						out.writeUTF(userName + ": " + msg + "\n");
						out.flush();
					}
					
				}
			} catch(Exception e) {
				// e.printStackTrace();
			}

		} catch(IOException e) {
			// e.printStackTrace();
		}
	}
}
