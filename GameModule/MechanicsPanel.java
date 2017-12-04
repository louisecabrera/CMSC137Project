import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;

public class MechanicsPanel extends JPanel implements Constants {
	
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	Image back1, back2;
	JLabel back;

	private JLabel manual = new JLabel(new ImageIcon(getClass().getResource("images/text/manual.png")));

	public MechanicsPanel(){
		try{
			back1 = ImageIO.read(new File("images/text/back1.png"));
			back2 = ImageIO.read(new File("images/text/back2.png"));
		} catch(Exception e){ }

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		setVisible(true);

		centerPanel.setBackground(Color.BLACK);
		centerPanel.add(manual);

		back = new JLabel(new ImageIcon(back1));
		back.setHorizontalAlignment(JLabel.LEFT);
		southPanel.setBackground(Color.BLACK);
		southPanel.add(back);

		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}
}