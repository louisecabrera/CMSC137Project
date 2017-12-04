import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.Painter;
import javax.swing.Timer;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import java.io.File;
import java.util.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Pacman extends JPanel implements Painter, ActionListener, KeyListener, Constants{
    Timer t = new Timer(5, this);
    Graphics g;
    int dx=0, dy=0;
    private int speed;
    int xPos, yPos;
    private int hp;
    private int foodEaten;
    private boolean alive;
    private boolean ghost;

    private Image pac;

    Map map = new Map();
    List<Barrier> barriers = map.getBarriers();
    List<Food> foods = map.getFoods();

    Shooter s;

    int direction;
    int prevDirection;
    int advancedKey;
    boolean collided = false;
    final int LEFT = 1;
    final int RIGHT = 2;
    final int UP = 3;
    final int DOWN = 4;
    final int STOP = 0;
    int bulletDirection;
    boolean newKeyPress = false;
    String name, server;

    DatagramSocket socket = new DatagramSocket();

    public Pacman(int xPos, int yPos, String name, String server) throws Exception{
        this.name = name;
        this.server =server;
        this.hp = 5;
        this.foodEaten = 0;
        this.alive = true;
        this.ghost = false;
        this.speed = 1;

        this.xPos = xPos;
        this.yPos = yPos;

        this.direction = STOP;
        this.prevDirection = STOP;
        this.advancedKey = STOP;

        t.start();
        try{
            this.pac = ImageIO.read(new File("images/pmtile.png"));
        } catch(Exception e){ }

        this.setVisible(true);
        addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        s = new Shooter();
    }

    public void paint(Graphics2D g, Object thePanel, int width, int height){
        // g.drawImage(pac, this.xPos, this.yPos, null);
        s.render(g);
    }

    public void setDirection(int direction){
        if(direction==UP){
            dy = -1*speed;
            dx = 0;
        }
        else if(direction==DOWN){
            dy = speed;
            dx = 0;
        }
        else if(direction==LEFT){
            dx = -1*speed;
            dy = 0;
        }
        else if(direction==RIGHT){
            dx = speed;
            dy = 0;
        }
    }

    public void eat(){
        this.foodEaten += 1;
    }

    public int getFoodEaten(){
        return this.foodEaten;
    }

    public void actionPerformed(ActionEvent e){

    	//switch pacman from right to left/left to right portal
    	if(this.xPos == 0 && this.yPos == 360){//left to right portal
    			this.xPos = (map.mapWidth * 30)-30;
    			this.yPos = 360;
    	}else if(this.xPos == ((map.mapWidth * 30)-30) && this.yPos == 360){//right to left portal
    			this.xPos = 0;
    			this.yPos = 360;
    	}

        boolean isAdvance = false;
        for(int i=0; i<barriers.size(); i++){
            if(this.checkCollision(barriers.get(i).getBounds())){
                if(newKeyPress){
                	if(!(this.direction==this.prevDirection)){
	                    this.advancedKey = this.direction;
	                }
                    else{
                        advancedKey = STOP;
                    }
                    this.direction = this.prevDirection;
                    this.setDirection(this.direction);
                }
                break;
            }
            else{
                if(newKeyPress){
                    advancedKey = STOP;
                }
            }
        }

        int temp = this.direction;
        if(advancedKey!=STOP){  
            this.direction = this.advancedKey;
            for(int i=0; i<barriers.size(); i++){
                if(this.checkCollision(barriers.get(i).getBounds())){
                    isAdvance = true;
                }
            }

            if(isAdvance) this.direction = temp;
            else{
                this.setDirection(this.direction);
                this.advancedKey = STOP;
            }
        }

        for(int i=0; i<barriers.size(); i++){
            if(this.checkCollision(barriers.get(i).getBounds())){
                //System.out.println(this.getBounds());
                //System.out.println("Collision w/ Barrier: " + barriers.get(i).getX() + ", " + barriers.get(i).getY());
                this.stopMoving();
            }
        }

        int prevX=xPos;
        int prevY=yPos;

        if(dx<0){
            if(this.xPos>=1){
                this.xPos += dx;
                this.yPos += dy;
            }
        }
        else if(dx>0){
            if(this.xPos<1290-30){
                this.xPos += dx;
                this.yPos += dy;
            }
        }
        else if(dy<0){
            if(this.yPos>=1){
                this.xPos += dx;
                this.yPos += dy;
            }
        }
        else if(dy>0){
            if(this.yPos<720-30){
                this.xPos += dx;
                this.yPos += dy;
            }
        }
        else if (dx == 0 && dy == 0){
            this.xPos += dx;
            this.yPos += dy;
        }
        newKeyPress = false;
        this.prevDirection = this.direction;

        // sends string to be read by other clients
        if(prevX!=xPos || prevY!=yPos){
            send("PLAYER "+name+" "+xPos+" "+yPos);
        }
    }

    // this sends player information
    public void send(String msg){
        try{
            byte[] buf = msg.getBytes();
            InetAddress address = InetAddress.getByName(server);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
            socket.send(packet);
        } catch(Exception e){ }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP){
            this.direction = UP;
            if(this.yPos>=1){
                dy = -1*speed;
                dx = 0;
            }
        }
        else if(key == KeyEvent.VK_DOWN){
            this.direction = DOWN;
            if(this.yPos<720-30){
                dy = speed;
                dx = 0;
            }
        }
        else if(key == KeyEvent.VK_LEFT){
            this.direction = LEFT;
            if(this.xPos>=1){
                dx = -1*speed;
                dy = 0;
            }
        }
        else if(key == KeyEvent.VK_RIGHT){
            this.direction = RIGHT;
            if(this.xPos<1290-30){
                dx = speed;
                dy = 0;
            }
        }
        else if(key == KeyEvent.VK_SPACE){
            if(this.foodEaten >= 5){
                s.addBullet(new Bullet(this.xPos+10, this.yPos+10));
                bulletDirection = this.direction;
                s.tick(bulletDirection);    
                this.foodEaten-=5;
            }
            
        }
        newKeyPress = true;
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    public Rectangle getBounds(){
        int x = 0;
        int y = 0;
        if(this.direction == UP){
            y = -1*speed;
        }
        else if(this.direction == DOWN){
            y = speed;
        }
        else if(this.direction == LEFT){
            x = -1*speed;
        }
        else if(this.direction == RIGHT){
            x = speed;
        }
        return new Rectangle(this.xPos+x, this.yPos+y, 30, 30);
    }

    public boolean checkCollision(Rectangle entity){
        return this.getBounds().intersects(entity);
    }

    public void stopMoving(){
        dx = 0;
        dy = 0;
    }

    public void setXPos(int x){
        this.xPos = x;
    }

    public void setYPos(int y){
        this.yPos = y;
    }

    public int getX(){
        return this.xPos;
    }

    public int getY(){
        return this.yPos;
    }
}