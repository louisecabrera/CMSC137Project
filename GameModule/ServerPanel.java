import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;

// menu of player who creates game

public class ServerPanel extends JPanel implements Constants{

	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private Font font = new Font("Calibri", Font.PLAIN, 30);

	private JTextField username, numOfPlayers;

	Image back1, back2, go1, go2;
	JLabel back, go;

	public ServerPanel(){
		try{
			back1 = ImageIO.read(new File("images/text/back1.png"));
			back2 = ImageIO.read(new File("images/text/back2.png"));
			go1 = ImageIO.read(new File("images/text/go1.png"));
			go2 = ImageIO.read(new File("images/text/go2.png"));
		} catch(Exception e){ }

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));

		// center panel components
		username = new JTextField(10);
		username.setFont(font);
		numOfPlayers = new JTextField(10);
		numOfPlayers.setFont(font);

		JLabel uname = new JLabel("Name");
		uname.setFont(font);
		uname.setForeground(Color.WHITE);
		JLabel players = new JLabel("Number of Players");
		players.setFont(font);
		players.setForeground(Color.WHITE);

		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setBackground(Color.BLACK);
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		centerPanel.add(uname, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		centerPanel.add(username, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		centerPanel.add(players, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		centerPanel.add(numOfPlayers, c);
		// end of center panel

		// south panel components
		back = new JLabel(new ImageIcon(back1));
		back.setHorizontalAlignment(JLabel.CENTER);

		go = new JLabel(new ImageIcon(go1));
		go.setHorizontalAlignment(JLabel.CENTER);

		southPanel.setBackground(Color.BLACK);
		southPanel.setPreferredSize(new Dimension(Constants.WIDTH, 100));
		southPanel.setLayout(new GridLayout(1,2,0,0));
		southPanel.add(back);
		southPanel.add(go);
		// end of south panel

		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	public String getName(){
		return username.getText();
	}

	public String getNumOfPlayers(){
		return numOfPlayers.getText();
	}
}