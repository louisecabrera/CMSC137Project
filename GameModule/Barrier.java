import java.awt.Rectangle;

public class Barrier {

	private int x, y, width, height;
	
	public Barrier(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	// makes rectangle for collision checking
	public Rectangle getBounds(){
		return new Rectangle(this.x, this.y, this.width, this.height);
	}
}