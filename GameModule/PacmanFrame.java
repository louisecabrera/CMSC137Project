import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class PacmanFrame extends JFrame implements Constants{

	int SERVER = 1, CLIENT = 0;

	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	
	public PacmanFrame(int type, String name){
		Server chatServer = new Server(2222);

		this.setLayout(new BorderLayout());
		eastPanel.setBackground(Color.YELLOW);
		eastPanel.setPreferredSize(new Dimension(250, Constants.HEIGHT));
		
		centerPanel.setPreferredSize(new Dimension(Constants.HEIGHT,Constants.HEIGHT));
		centerPanel.setBackground(Color.BLACK);

		GamePanel game = new GamePanel();
		Thread gp = new Thread(game);
		gp.start();

		centerPanel.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				chatServer.typeArea.setFocusable(false);
				game.setFocusable(true);
			}
			public void mouseEntered(MouseEvent e){ }
			public void mouseExited(MouseEvent e){ }
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }
		});

		chatServer.typeArea.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e){
				chatServer.typeArea.setFocusable(true);
			}
			public void mouseEntered(MouseEvent e){ }
			public void mouseExited(MouseEvent e){ }
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }
		});

		// starts server
		if(type == SERVER){
			
			Thread server = new Thread(() -> {
				
				try{ chatServer.start(name); } catch(Exception e){ }
			});
			server.start();
			eastPanel.add(chatServer);
		}
		else if(type == CLIENT){

		}

		centerPanel.add(game);


		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}