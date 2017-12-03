import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Main frame that shows player menus

public class PacmanMenu extends JFrame implements Constants{

	private MenuPanel menuPanel;
	private ServerPanel serverPanel;
	private ClientPanel clientPanel;

	public PacmanMenu(){
		setTitle("The Pacman City");
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuPanel = new MenuPanel();
		add(menuPanel);

		serverPanel = new ServerPanel();
		clientPanel = new ClientPanel();

		/* Start of menu panel controls */
		// create means player is server
		(menuPanel.create).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(menuPanel.create).setIcon(new ImageIcon((menuPanel.create2)));
			}
			public void mouseExited(MouseEvent e){
				(menuPanel.create).setIcon(new ImageIcon((menuPanel.create1)));
			}
			public void mouseClicked(MouseEvent e){
				menuPanel.setVisible(false);
				remove(menuPanel);

				serverPanel.username.setText("");
				serverPanel.numOfPlayers.setText("");
				serverPanel.servername.setText("");

				serverPanel.setVisible(true);
				add(serverPanel);
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }
		});

		// join means player is client
		(menuPanel.join).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(menuPanel.join).setIcon(new ImageIcon((menuPanel.join2)));
			}
			public void mouseExited(MouseEvent e){
				(menuPanel.join).setIcon(new ImageIcon((menuPanel.join1)));
			}
			public void mouseClicked(MouseEvent e){	
				menuPanel.setVisible(false);
				remove(menuPanel);

				clientPanel.username.setText("");
				clientPanel.servername.setText("");

				clientPanel.setVisible(true);
				add(clientPanel);
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }
		});

		(menuPanel.exit).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(menuPanel.exit).setIcon(new ImageIcon((menuPanel.exit2)));
			}
			public void mouseExited(MouseEvent e){
				(menuPanel.exit).setIcon(new ImageIcon((menuPanel.exit1)));
			}
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }
		});
		/* End of menu panel controls */

		/* Start of server panel controls */
		(serverPanel.back).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(serverPanel.back).setIcon(new ImageIcon((serverPanel.back2)));
			}
			public void mouseExited(MouseEvent e){
				(serverPanel.back).setIcon(new ImageIcon((serverPanel.back1)));
			}
			public void mouseClicked(MouseEvent e){
				serverPanel.setVisible(false);
				remove(serverPanel);
				menuPanel.setVisible(true);
				add(menuPanel);
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }	
		});

		(serverPanel.go).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(serverPanel.go).setIcon(new ImageIcon((serverPanel.go2)));
			}
			public void mouseExited(MouseEvent e){
				(serverPanel.go).setIcon(new ImageIcon((serverPanel.go1)));
			}
			public void mouseClicked(MouseEvent e){
				final String name = (serverPanel.getName()).trim();
				final String players = (serverPanel.getNumOfPlayers()).trim();
				final String servername = (serverPanel.getServerName()).trim();


				if(name==null || name.isEmpty() || players==null || players.isEmpty() || servername==null || servername.isEmpty()){
					// checks if textfields are empty
				}
				else{
					serverPanel.setVisible(false);
					menuPanel.setVisible(true);
					add(menuPanel);

					// opens gamepanel
					Thread game = new Thread(() -> {
						PacmanFrame pacframe = new PacmanFrame(1, name,servername,players);	
					});
					game.start();	
				}

				// start servers here
				// start chat server
				// start udp server
				// create new chat client
				// create new udp client
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }	
		});
		/* End of server panel controls */

		/* Start of client panel controls */
		(clientPanel.back).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(clientPanel.back).setIcon(new ImageIcon((clientPanel.back2)));
			}
			public void mouseExited(MouseEvent e){
				(clientPanel.back).setIcon(new ImageIcon((clientPanel.back1)));
			}
			public void mouseClicked(MouseEvent e){
				clientPanel.setVisible(false);
				remove(clientPanel);
				menuPanel.setVisible(true);
				add(menuPanel);	
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }	
		});

		(clientPanel.go).addMouseListener(new MouseListener(){
			public void mouseEntered(MouseEvent e){
				(clientPanel.go).setIcon(new ImageIcon((clientPanel.go2)));
			}
			public void mouseExited(MouseEvent e){
				(clientPanel.go).setIcon(new ImageIcon((clientPanel.go1)));
			}
			public void mouseClicked(MouseEvent e){
				final String name = (clientPanel.getName()).trim();
				final String servername = (clientPanel.getServerName()).trim();

				if(name==null || name.isEmpty() || servername==null || servername.isEmpty()){
					// checks if textfield is empty
				}
				else{
					clientPanel.setVisible(false);
					menuPanel.setVisible(true);
					add(menuPanel);

					// opens gamepanel
					Thread game = new Thread(() -> {
						PacmanFrame pacframe = new PacmanFrame(0, name, servername,"0");
					});
					game.start();	
				}

				// create new client for chat
				// create new client for udp
			}
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }	
		});
		/* End of client panel controls */

		pack();
		setVisible(true);
		setResizable(false);
	}
}