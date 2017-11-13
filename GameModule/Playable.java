import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Playable extends JPanel {
	
	private Map map;

	private JPanel centerPanel = new JPanel();
	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();

	private Pacman pacman;
	private JFrame frame;

	private Timer timer;

	public Playable() {
		frame = new JFrame("The Pacman City");
		map = new Map();

		pacman = map.getPacman();

		frame.add(map);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		frame.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_LEFT) {
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.BLACK);
					pacman.setXPos(pacman.getXPos());
					pacman.setYPos(pacman.getYPos()-1);
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.YELLOW); 
			    }

			    if (key == KeyEvent.VK_RIGHT) {
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.BLACK);
					pacman.setXPos(pacman.getXPos());
					pacman.setYPos(pacman.getYPos()+1);
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.YELLOW);
			    }

			    if (key == KeyEvent.VK_UP) {
			    	map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.BLACK);
					pacman.setXPos(pacman.getXPos()-1);
					pacman.setYPos(pacman.getYPos());
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.YELLOW);
			    }

			    if (key == KeyEvent.VK_DOWN) {
			    	map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.BLACK);
					pacman.setXPos(pacman.getXPos()+1);
					pacman.setYPos(pacman.getYPos());
					map.p[pacman.getXPos()][pacman.getYPos()].setBackground(Color.YELLOW);
			    }
			}

			public void keyReleased(KeyEvent e){

			}

			public void keyTyped(KeyEvent e){

			}
		});
		
	}

}