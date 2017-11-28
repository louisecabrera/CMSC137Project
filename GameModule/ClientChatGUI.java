import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClientChatGUI extends JPanel {
	
	JTextField typeArea;
	JTextArea viewArea;
	String name;
	GamePanel game;

	public ClientChatGUI(String name, GamePanel game){
		this.game = game;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		this.name = name;

		typeArea = new JTextField(20);
		viewArea = new JTextArea(20,20);
		viewArea.setEditable(false);
		viewArea.setLineWrap(true);

		JScrollPane scroll = new JScrollPane(viewArea);

		c.fill= GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(scroll, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		add(typeArea, c);

		setVisible(true);
	}

	public void start(){
		Scanner scanner = new Scanner(System.in);

		try (Socket socket = new Socket("localhost", 2222);
			DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			BufferedReader In = new BufferedReader(new InputStreamReader(System.in))) {

			// thread that reads input stream (gets input of other users)
			Thread input = new Thread(() -> {
				String msg;
				try {
					while((msg = in.readUTF()) != null) {
						// System.out.println(msg);
						viewArea.append(msg);
					}
				} catch(Exception e) { }
			});
			input.start();

			// asks for username
			String msg;

			typeArea.addKeyListener(new KeyListener(){
				public void keyPressed(KeyEvent e){
					int key = e.getKeyCode();

					if(key == KeyEvent.VK_ENTER){
						try{
							out.writeUTF("["+name+"]:  "+typeArea.getText()+"\n");
							out.flush();
						} catch(Exception ioe){ }
						typeArea.setText("");
					}
					else if(key == KeyEvent.VK_TAB){
						game.requestFocus();
					}
				}
				public void keyReleased(KeyEvent e){ }
				public void keyTyped(KeyEvent e) { }
			});

			try {
				// loop that writes user input to stream
				while((msg = In.readLine()) != null) {
					
					if(msg.equals("/q")){
						out.writeUTF(name + " has disconnected.");
						break;
					}
					else{
						out.writeUTF(name + ": " + msg + "\n");
						out.flush();
					}
					
				}
			} catch(Exception e) { }

		} catch(IOException e) { }
	}

}