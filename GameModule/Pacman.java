import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Pacman {

	private int hp;
	private int xPos;
	private int yPos;
	private boolean isAlive;
	private boolean isGhost;
	private int foodEaten;

	public Pacman() {
		this.hp = 5;
		this.isAlive = true;
		this.isGhost = false;
		this.foodEaten = 0;
	}

	public void eat() {
		this.foodEaten += 1;
	}

	public void turnToGhost() {
		this.isAlive = false;
		this.isGhost = true;
	}

	// getter methods

	public int getHp() {
		return this.hp;
	}

	public int getXPos() {
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}

	// setter methods
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
}