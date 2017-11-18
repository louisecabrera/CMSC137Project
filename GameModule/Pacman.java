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

public class Pacman extends JPanel implements Painter, KeyListener, ActionListener {
	Timer t = new Timer(5, this);
	Graphics g;
	int dx, dy;
	private int xPos, yPos;
	private int hp;
	private int foodEaten;
	private boolean alive;
	private boolean ghost;

	private Image pac;

	Map map = new Map();
	List<Barrier> barriers = map.getBarriers();

	int direction;
	final int LEFT = 1;
	final int RIGHT = 2;
	final int UP = 3;
	final int DOWN = 4;

	public Pacman(int xPos, int yPos){
		this.hp = 5;
		this.foodEaten = 0;
		this.alive = true;
		this.ghost = false;

		this.xPos = xPos;
		this.yPos = yPos;

		this.direction = RIGHT;

		t.start();
		try{
			this.pac = ImageIO.read(new File("images/pmtile.png"));
		} catch(Exception e){ }

		this.setVisible(true);
		addKeyListener(this);
		this.setPreferredSize(new Dimension(600,600));
		this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
	}

	public void paint(Graphics2D g, Object thePanel, int width, int height){
		g.drawImage(pac, this.xPos, this.yPos, null);
	}

	public void actionPerformed(ActionEvent e){
		for(int i=0; i<barriers.size(); i++){
			// System.out.println(this.getBounds());
			if(this.checkCollision(barriers.get(i).getBounds())){
				System.out.println(this.getBounds());
				System.out.println("Collision w/ Barrier: " + barriers.get(i).getX() + ", " + barriers.get(i).getY());
				this.stopMoving();
			}
		}
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
	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_UP){
			this.direction = UP;
            System.out.println("Up");
            if(this.yPos>=1){
                dy = -2;
                dx = 0;
            }
        }
        else if(key == KeyEvent.VK_DOWN){
			this.direction = DOWN;
            if(this.yPos<720-30){
                dy = 2;
                dx = 0;
            }
        }
        else if(key == KeyEvent.VK_LEFT){
			this.direction = LEFT;
            if(this.xPos>=1){
                dx = -2;
                dy = 0;
            }
        }
        else if(key == KeyEvent.VK_RIGHT){
			this.direction = RIGHT;
            if(this.xPos<1290-30){
                dx = 2;
                dy = 0;
            }
        }
	}

	public void keyReleased(KeyEvent e){}

	public void keyTyped(KeyEvent e){}

	public Rectangle getBounds(){
		int x = 0;
		int y = 0;
		if(this.direction == UP){
			y = -2;
		}
		else if(this.direction == DOWN){
			y = 2;
		}
		else if(this.direction == LEFT){
			x = -2;
		}
		else if(this.direction == RIGHT){
			x = 2;
		}
		return new Rectangle(this.xPos+2+x, this.yPos+2+y, 28, 28);
	}

	public boolean checkCollision(Rectangle barrier){
		return this.getBounds().intersects(barrier);
	}

	public void stopMoving(){
		dx = 0;
		dy = 0;
	}
}