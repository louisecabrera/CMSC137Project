import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.Painter;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

import java.util.*;

public class Map implements Painter {

	Graphics g;
	int tileSize = 30;
	int mapArray[][];
	private int mapWidth, mapHeight;
	private List<Barrier> barriers = new ArrayList<Barrier>();
	private Image wall, bg, pac;

	public Map(){
		try {
			this.wall = ImageIO.read(new File("images/wall.png"));
			this.bg = ImageIO.read(new File("images/bgtile.png"));
			this.pac = ImageIO.read(new File("images/pmtile.png"));
		} catch(Exception e){ }
		readMap();
	}

	public void readMap(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader("maps/input.txt"));
			this.mapWidth = Integer.parseInt(reader.readLine());
			this.mapHeight = Integer.parseInt(reader.readLine());

			mapArray = new int[mapHeight][mapWidth];

			// populate 2d array with values from file
			for(int row=0; row<mapHeight; row++){
				String line = reader.readLine();
				String[] tokens = line.split(" ");

				for(int col=0; col<mapWidth; col++){
					mapArray[row][col] = Integer.parseInt(tokens[col]);

					switch(mapArray[row][col]){
						case 1:
							// makes new barrier
							Barrier b = new Barrier(col*this.tileSize,row*this.tileSize,this.tileSize,this.tileSize);
							// stores all found barriers to list
							barriers.add(b);
							break;
					}
				}
			}

			// System.out.println("BARRIER SIZE:" + barriers.size());
			// for(int i=0; i<barriers.size(); i++){
			// 	System.out.println("Barrier: " + barriers.get(i).getX() + ", " + barriers.get(i).getY());
			// }
		} catch(Exception e){ e.printStackTrace(); }
	}

	public void paint(Graphics2D g, Object thePanel, int width, int height){
		
		for(int row=0; row<mapHeight; row++){
			for(int col=0; col<mapWidth; col++){
				switch(mapArray[row][col]){
					case 0:
						g.drawImage(bg, col*this.tileSize, row*this.tileSize, null);
						break;
					case 1:
						g.drawImage(wall, col*this.tileSize, row*this.tileSize, null);
						break;
				}
			}
		}
	}

	public List<Barrier> getBarriers(){
		return this.barriers;
	}
}