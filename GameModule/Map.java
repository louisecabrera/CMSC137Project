import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Random;
import java.io.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.imageio.ImageIO;

public class Map extends JPanel {

	private char[][] mapArray = new char[20][20];
	JPanel [][] p = new JPanel[20][20];

	private Pacman pacman;

	Random r = new Random();
	String path;

	public Map() {
		path = "map.txt";		// reads map from txt file
		setLayout(new GridLayout(20,20));
		setPreferredSize(new Dimension(440,440));
		setBackground(Color.BLACK);
		
		pacman = new Pacman();

		readMap();
	}

	// reads map based on .txt file read
	// 'W' characters represent walls
	// 'S' characters represent spaces
	public void readMap() {
		for(int i=0; i<20; i++){
			for(int j=0; j<20; j++){
				p[i][j] = new JPanel();
				p[i][j].setBackground(Color.BLACK);
				this.add(p[i][j]);
			}
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;

			int i = 0;

			while((line = reader.readLine()) != null && i<20) {
				for(int j=0; j<20; j++){
					mapArray[i][j] = line.charAt(j);
					if(mapArray[i][j] == 'W'){
						p[i][j].setBackground(Color.BLUE);
					} else if(mapArray[i][j] == 'S') {
						p[i][j].setBackground(Color.BLACK);
					}
				}
				i++;
			}

			positionPacman();
		} catch(Exception e) {

		}
	}

	public void positionPacman() {
		do {
			pacman.setXPos(r.nextInt(20));
			pacman.setYPos(r.nextInt(20));
		} while(mapArray[pacman.getXPos()][pacman.getYPos()]=='W');

		p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.YELLOW);
	}

	public Pacman getPacman(){
		return this.pacman;
	}
}