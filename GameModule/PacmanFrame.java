import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.GridBagLayout;

public class PacmanFrame extends JFrame{

	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel westPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	Dimension screenSize;
	private int height, width;
	
	public PacmanFrame(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double h = screenSize.getHeight();
		height = h.intValue();
		Double w = screenSize.getWidth();
		width = w.intValue();

		this.setLayout(new BorderLayout());
		eastPanel.setBackground(Color.YELLOW);
		eastPanel.setPreferredSize(new Dimension(250, height));
		
		centerPanel.setPreferredSize(new Dimension(height,height));
		centerPanel.setBackground(Color.BLACK);
		GamePanel game = new GamePanel();
		Thread gp = new Thread(game);
		centerPanel.add(game);

		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		gp.start();
		try{ gp.join(); } catch(Exception e){ }
	}
}