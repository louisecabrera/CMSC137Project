import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.io.File;

public class Bullet {
	
	final int LEFT = 1;
    final int RIGHT = 2;
    final int UP = 3;
    final int DOWN = 4;

	private int x, y;
	private Image bullet;
	private int width, height;
	private int direction;

	public Bullet(int x, int y){
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;

		try{
			bullet = ImageIO.read(new File("images/bullet.png"));
		} catch(Exception e){ }
	}

	public void giveDirection(int direction){
		this.direction = direction;
	}

	public void tick(){
		if(this.direction == UP){
			y-=5;
		}
		else if(this.direction == DOWN){
			y+=5;
		}
		else if(this.direction == LEFT){
			x-=5;
		}
		else if(this.direction == RIGHT){
			x+=5;
		}
	}

	public void render(Graphics2D g){
		g.drawImage(bullet, x, y, null);
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}

	public boolean checkCollision(Rectangle entity){
        return this.getBounds().intersects(entity);
	}
	
}