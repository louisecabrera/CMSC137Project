import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pacman extends JPanel implements ActionListener, KeyListener {
    Timer t = new Timer(5, this);
    int x = 0, y = 0;
    public int hp;
    public int bullets;
    public int xPos;
    public int yPos;
    public int foodEaten;
    public boolean alive;
    public boolean ghost;

    public Pacman(int xPos, int yPos) {
        t.start();
        this.hp = 5;
        this.bullets = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.foodEaten = 0;
        this.alive = false;
        this.ghost = false;
        this.wakeUp();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    public void shootNormalBullet(){//-1 HP to victim
      
    }

    public void shootLaser(){//-2 HP to victim
     
    }
    
    public void wakeUp(){ 
        this.alive = true;
    }

    public void eat(){
        this.foodEaten += 1;
    }

    public void turnToGhost(){
        this.alive = false;
        this.ghost = true;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(MapInterface.PACMAN_COLOR);
        g.fillOval(this.xPos, this.yPos, MapInterface.TILE_DIMS, MapInterface.TILE_DIMS);
    }

    public void actionPerformed(ActionEvent e) {
        if(x<0){
            if(xPos>=1){
                xPos += x;
                yPos += y;
            }
        }
        else if(x>0){
            if(xPos<MapInterface.SCREEN_WIDTH-MapInterface.TILE_DIMS){
                xPos += x;
                yPos += y;
            }
        }
        else if(y<0){
            if(yPos>=1){
                xPos += x;
                yPos += y;
            }
        }
        else if(y>0){
            if(yPos<MapInterface.SCREEN_HEIGHT-MapInterface.TILE_DIMS){
                xPos += x;
                yPos += y;
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            y = -1;
            x = 0;
        }
        else if(key == KeyEvent.VK_DOWN){
            y = 1;
            x = 0;
        }
        else if(key == KeyEvent.VK_LEFT){
            x = -1;
            y = 0;
        }
        else if(key == KeyEvent.VK_RIGHT){
            x = 1;
            y = 0;
        }
    }

    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}
}