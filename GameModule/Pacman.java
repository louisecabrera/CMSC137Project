import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.Painter;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import java.io.File;

public class Pacman extends JPanel implements Painter {
	Graphics g;
	private int xPos, yPos;
	private int hp;
	private int foodEaten;
	private boolean alive;
	private boolean ghost;

	private Image pac;

	public Pacman(int xPos, int yPos){
		this.hp = 5;
		this.foodEaten = 0;
		this.alive = true;
		this.ghost = false;

		this.xPos = xPos;
		this.yPos = yPos;

		try{
			this.pac = ImageIO.read(new File("images/pmtile.png"));
		} catch(Exception e){ }

		this.setVisible(true);
		this.setPreferredSize(new Dimension(700,700));

		

	}

	public void paint(Graphics2D g, Object thePanel, int width, int height){
		g.drawImage(pac, this.xPos, this.yPos, null);
	}
}