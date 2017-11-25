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
		this.setPreferredSize(new Dimension(map.mapWidth*30,map.mapHeight*30));
		layerPainters.add(map);

		p = new Pacman(30,30);
		layerPainters.add(p);

		this.add(p);
		this.setVisible(true);
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		for(Painter painter : layerPainters){
			painter.paint(g2d, this, map.mapWidth*30,map.mapHeight*30);
		}
	}

	public void run(){
		while(true){

			for(int i=0; i<map.foods.size(); i++){
				if(p.checkCollision(map.foods.get(i).getBounds())){

					if(map.foods.get(i).isVisible()){
						p.eat();
						map.foods.get(i).eaten();	
					}
					
					//map.foods.remove(i);
					break;
				}
			}

			for(int i=0; i<map.foods.size(); i++){
				if(!map.foods.get(i).isVisible()){
					map.foods.get(i).awaitRespawn();
				}
			}

			this.repaint();
			try{ Thread.sleep(10); } catch(Exception e){ }
		}
	}
}