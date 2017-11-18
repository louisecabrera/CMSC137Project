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

public class GamePanel extends JPanel {
	
	Image pac;
	Map map = new Map();
	List<Painter> layerPainters;

	public GamePanel(){
		layerPainters = new ArrayList<Painter>();

		// this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(700,700));

		Map map = new Map();
		layerPainters.add(map);

		Pacman p = new Pacman(30,30);
		layerPainters.add(p);

		this.setVisible(true);
	}

	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D)g;

		for(Painter painter : layerPainters){
			painter.paint(g2d, this, 700,700);
		}
	}
}