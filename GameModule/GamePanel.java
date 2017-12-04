import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.Painter;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.awt.Point;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.util.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GamePanel extends JPanel implements Runnable, Constants{
	
	Image pac;
	Map map = new Map();
	List<Painter> layerPainters;
	Pacman p;
	boolean init = true;
	List<Barrier> barriers = map.getBarriers();
	Point[] positions; 

	private JLabel wait = new JLabel(new ImageIcon(getClass().getResource("images/text/wait.png")));

	int numPlayers;
	int x=0, y=0, prevX, prevY, speed=1;
	String name;
	String pname;
	String server;
	boolean connected = false;
	DatagramSocket socket = new DatagramSocket();
	String serverData;
	int currentCount = 0;
	boolean start = false;
	

	public GamePanel(String numPlayers, String server, String name) throws Exception{
		this.name = name;
		this.numPlayers = Integer.parseInt(numPlayers);
		this.server = server;
		socket.setSoTimeout(100);
		layerPainters = new LinkedList<Painter>();
		positions = new Point[this.numPlayers];

		// this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(map.mapWidth*30,map.mapHeight*30));
		layerPainters.add(map);

		try{
            this.pac = ImageIO.read(new File("images/pmtile.png"));
        } catch(Exception e){ }

        Random random = new Random();

        // Randomizes pacman position
		boolean clash=true;

		while(clash){
			clash = false;
			x = random.nextInt((map.mapWidth)-1)+1;
			y = random.nextInt((map.mapHeight)-1)+1;

			// checks if pos is a barrier
			for(Barrier b : barriers){
				if(x*30==b.getX() && y*30==b.getY()){
					clash = true;
				}
			}
		}
		System.out.println((x*30) + " " + (y*30));
		p = new Pacman(x*30,y*30,name,server);

		// layerPainters.add(p);
		this.add(p);
		this.setVisible(true);
		// this.requestFocusInWindow();
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		for(Painter painter : layerPainters){
			painter.paint(g2d, this, map.mapWidth*30,map.mapHeight*30);
		}
		// fix pacman render here too
		if(!start){
			System.out.print("1");
			g.setColor(Color.YELLOW); //random
			g.fillOval(this.x*30, this.y*30, 30, 30);
		}
		else{
			System.out.println("2");
			for(int i=0; i<currentCount; i++){
				g.setColor(Color.YELLOW); //random
				int xpos = (int)positions[i].getX();
				int ypos = (int)positions[i].getY();
				g.fillOval(xpos, ypos, 30, 30);
			}
		}
	}

	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
			InetAddress address = InetAddress.getByName(server);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
			socket.send(packet);
		} catch(Exception e){ }
	}

	public void run(){
		while(true){
			try{ Thread.sleep(1); } catch(Exception e){ }

			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{ socket.receive(packet); } catch(Exception e){ }

			serverData = new String(buf);
			//System.out.println(serverData.split(":").length);

			// checks connection with server
			if(!connected && serverData.startsWith("CONNECTED")){
				connected = true;
				System.out.println("Connected.");
			}
			else if(!connected){
				System.out.println("Connecting...");
				send("CONNECT "+name);
			}
			else if(connected){
				//System.out.println("Connecteeeed");
				// start of food operations
				for(int i=0; i<map.foods.size(); i++){
					if(p.checkCollision(map.foods.get(i).getBounds())){
						if(map.foods.get(i).isVisible()){
							p.eat();
							map.foods.get(i).eaten();	
						}
						
						break;
					}
				}

				for(int i=0; i<map.foods.size(); i++){
					if(!map.foods.get(i).isVisible()){
						map.foods.get(i).awaitRespawn();
					}
				}
				// end of food operations

				// updates board info if receives a player from serverData
				if(serverData.startsWith("PLAYER")){
					//System.out.println("Startttttt");
					start = true;
					String[] playersInfo = serverData.split(":");
					currentCount = 0;
					for(int i=0; i<playersInfo.length-1; i++){
						String[] playerInfo = playersInfo[i].split(" ");

						String pname = playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);

						this.x = x;
						this.y = y;
						Point point = new Point(x,y);
						positions[i] = point;
						currentCount++;

						// FIX IMAGE RENDER of pacman here; based from circlewars idk
						
					}
					this.repaint();
				}
			}
		}
	}
}