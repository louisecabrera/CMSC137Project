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

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.io.File;
import java.util.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GamePanel extends JPanel implements Runnable, Constants, KeyListener{
	
	Image pac;
	Map map = new Map();
	List<Painter> layerPainters;
	Pacman p;
	boolean init = true;
	List<Barrier> barriers = map.getBarriers();

	private JLabel wait = new JLabel(new ImageIcon(getClass().getResource("images/text/wait.png")));

	int numPlayers;
	int x=30, y=30, prevX, prevY, speed=1;
	String name;
	String pname;
	String server;
	boolean connected = false;
	DatagramSocket socket = new DatagramSocket();
	String serverData;

	public GamePanel(String numPlayers, String server, String name) throws Exception{
		this.name = name;
		this.numPlayers = Integer.parseInt(numPlayers);
		this.server = server;
		socket.setSoTimeout(100);
		layerPainters = new LinkedList<Painter>();

		// this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(map.mapWidth*30,map.mapHeight*30));
		layerPainters.add(map);

		p = new Pacman(x,y);
		p.addKeyListener(this);

		layerPainters.add(p);

		this.add(p);
		this.setVisible(true);
		// this.requestFocusInWindow();
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		for(Painter painter : layerPainters){
			painter.paint(g2d, this, map.mapWidth*30,map.mapHeight*30);
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
			// // start of food operations
			// for(int i=0; i<map.foods.size(); i++){
			// 	if(p.checkCollision(map.foods.get(i).getBounds())){

			// 		if(map.foods.get(i).isVisible()){
			// 			p.eat();
			// 			map.foods.get(i).eaten();	
			// 		}
					
			// 		break;
			// 	}
			// }

			// for(int i=0; i<map.foods.size(); i++){
			// 	if(!map.foods.get(i).isVisible()){
			// 		map.foods.get(i).awaitRespawn();
			// 	}
			// }
			// // end of food operations

			// this.repaint();
			// try{ Thread.sleep(10); } catch(Exception e){ }

			try{ Thread.sleep(1); } catch(Exception e){ }

			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{ socket.receive(packet); } catch(Exception e){ }

			serverData = new String(buf);

			if(!connected && serverData.startsWith("CONNECTED")){
				connected = true;
				System.out.println("Connected.");
			}
			else if(!connected){
				System.out.println("Connecting...");
				send("CONNECT "+name);
			}
			else if(connected){
				if(serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					System.out.println("length:"+playersInfo.length);
					for(int i=0; i<playersInfo.length; i++){
						String[] playerInfo = playersInfo[i].split(" ");

						String pname = playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);

						p.setXPos(x);
						p.setYPos(y);
					}

					this.repaint();					
				}
				
			}
		}
	}

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        prevX=p.getX();
        prevY=p.getY();

        if(key == KeyEvent.VK_UP){
            p.direction = UP;
            if(p.getY()>=1){
                p.dy = -1*speed;
                p.dx = 0;
            }
        }
        else if(key == KeyEvent.VK_DOWN){
            p.direction = DOWN;
            if(p.getY()<720-30){
                p.dy = speed;
                p.dx = 0;
            }
        }
        else if(key == KeyEvent.VK_LEFT){
            p.direction = LEFT;
            if(p.getX()>=1){
                p.dx = -1*speed;
                p.dy = 0;
            }
        }
        else if(key == KeyEvent.VK_RIGHT){
            p.direction = RIGHT;
            if(p.getX()<1290-30){
                p.dx = speed;
                p.dy = 0;
            }
        }
        // else if(key == KeyEvent.VK_SPACE){
        //     if(this.foodEaten >= 5){
        //         s.addBullet(new Bullet(this.xPos+10, this.yPos+10));
        //         bulletDirection = this.direction;
        //         s.tick(bulletDirection);    
        //         this.foodEaten-=5;
        //     }
            
        // }
        p.newKeyPress = true;

        if(prevX!=p.getX() || prevY!=p.getY()){
        	send("PLAYER "+name+" "+p.getX()+" "+p.getY());
        }

    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}
}