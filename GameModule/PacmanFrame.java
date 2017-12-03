import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PacmanFrame extends JFrame implements Constants{

	int SERVER = 1, CLIENT = 0, numPlayers;

	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	String name, servername;

	GamePanel game;
	
	public PacmanFrame(int type, String name, String servername, String numPlayers) {
		this.name = name;
		this.numPlayers = Integer.parseInt(numPlayers);
		this.servername = servername;

		try{
			game = new GamePanel(numPlayers, servername, name);
		} catch(Exception e){
			e.printStackTrace();
		}

		this.setLayout(new BorderLayout());
		eastPanel.setBackground(Color.YELLOW);
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setPreferredSize(new Dimension(250, Constants.HEIGHT));
		
		centerPanel.setPreferredSize(new Dimension(Constants.HEIGHT,Constants.HEIGHT));
		centerPanel.setBackground(Color.BLACK);

		// starts server
		if(type == SERVER){
			// starts server;
			Server chatServer = new Server(2222);
			Thread server = new Thread(() -> {
				
				try{ chatServer.start(); } catch(Exception e){ }
			});
			server.start();

			// makes server a client
			ClientChatGUI chatClient = new ClientChatGUI(this.name, game, servername);
			Thread client = new Thread(() -> {
				chatClient.start();
			});
			client.start();

			GameServer gameServer = new GameServer(this.numPlayers);

			Thread gServer = new Thread(gameServer);
			gServer.start();

			eastPanel.add(chatClient, BorderLayout.SOUTH);
		}
		else if(type == CLIENT){
			System.out.println("New Client");
			// adds client
			ClientChatGUI chatClient = new ClientChatGUI(this.name, game, servername);
			Thread client = new Thread(() -> {
				chatClient.start();
			});
			client.start();

			eastPanel.add(chatClient, BorderLayout.SOUTH);
		}

		Thread gp = new Thread(game);
		gp.start();

		centerPanel.add(game);

		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}