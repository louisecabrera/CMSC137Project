import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.imageio.ImageIO;
import javax.swing.Painter;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import java.io.File;

import java.util.*;

public class GamePanel extends JPanel implements Runnable{
	
	Image pac;
	Map map = new Map();
	List<Painter> layerPainters;
	Pacman p;
	boolean init = true;
	List<Barrier> barriers = map.getBarriers();

	public GamePanel(){
		layerPainters = new ArrayList<Painter>();

		// this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1290,720));
		layerPainters.add(map);

		p = new Pacman(30,30);
		layerPainters.add(p);

		this.add(p);
		this.setVisible(true);
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		for(Painter painter : layerPainters){
			painter.paint(g2d, this, 1290,720);
		}
	}

	public void run(){
		while(true){
			// for(int i=0; i<barriers.size(); i++){
			// 	System.out.println(p.getBounds());
			// 	if(p.checkCollision(barriers.get(i).getBounds())){
			// 		// System.out.println("Collision w/ Barrier: " + barriers.get(i).getX() + ", " + barriers.get(i).getY());
			// 		p.stopMoving();
			// 	}
			// }

			for(int i=0; i<map.foods.size(); i++){
				System.out.println("Food Eaten: "+p.getFoodEaten());

				if(p.checkCollision(map.foods.get(i).getBounds())){

					if(map.foods.get(i).isVisible()){
						p.eat();
						map.foods.get(i).eaten();	
					}
					
					//map.foods.remove(i);
					break;
				}
			}



			this.repaint();
			try{ Thread.sleep(10); } catch(Exception e){ }
		}
	}
}