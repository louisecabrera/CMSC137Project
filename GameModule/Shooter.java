import java.util.*;
import java.awt.Graphics2D;

public class Shooter {
	
	LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	Bullet tempBullet;
	Map map = new Map();
    List<Barrier> barriers = map.getBarriers();

	public Shooter(){

	}

	public void tick(int direction){
		tempBullet = bullets.get(bullets.size()-1);
		tempBullet.giveDirection(direction);
	}

	public void render(Graphics2D g){
		for(int i=0; i<bullets.size(); i++){
			tempBullet = bullets.get(i);
			for(int j=0; j<barriers.size(); j++){
				if(tempBullet.checkCollision(barriers.get(j).getBounds())){
					bullets.remove(tempBullet);
				}
			}
			tempBullet.tick();
			tempBullet.render(g);
		}
	}

	public void addBullet(Bullet b){
		bullets.add(b);
	}

	public void removeBullet(Bullet b){
		bullets.remove(b);
	}
	
}