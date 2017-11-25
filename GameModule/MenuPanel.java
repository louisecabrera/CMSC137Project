import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;

// main menu - first menu shown to player

public class MenuPanel extends JPanel implements Constants{

	private JPanel northPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();

	Image create1, create2, join1, join2, exit1, exit2;

	private JLabel logo = new JLabel(new ImageIcon(getClass().getResource("images/text/logo.png")));
	JLabel create, join, exit;

	
	public MenuPanel(){
		try{
			create1 = ImageIO.read(new File("images/text/create1.png"));
			create2 = ImageIO.read(new File("images/text/create2.png"));
			join1 = ImageIO.read(new File("images/text/join1.png"));
			join2 = ImageIO.read(new File("images/text/join2.png"));
			exit1 = ImageIO.read(new File("images/text/exit1.png"));
			exit2 = ImageIO.read(new File("images/text/exit2.png"));
		} catch(Exception e){ }

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		setVisible(true);

		northPanel.setPreferredSize(new Dimension(Constants.WIDTH, 100));
		northPanel.setBackground(Color.BLACK);

		centerPanel.setBackground(Color.BLACK);
		centerPanel.add(logo);

		create = new JLabel(new ImageIcon(create1));
		create.setHorizontalAlignment(JLabel.CENTER);

		join = new JLabel(new ImageIcon(join1));
		join.setHorizontalAlignment(JLabel.CENTER);

		exit = new JLabel(new ImageIcon(exit1));
		exit.setHorizontalAlignment(JLabel.CENTER);

		southPanel.setBackground(Color.BLACK);
		southPanel.setPreferredSize(new Dimension(Constants.WIDTH, 200));
		southPanel.setLayout(new GridLayout(4,1,0,0));
		southPanel.add(create);
		southPanel.add(join);
		southPanel.add(exit);

		add(northPanel, BorderLayout.NORTH);
		add(southPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
	}
}