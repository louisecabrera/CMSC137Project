import javax.swing.*;
import java.awt.*;

public interface Constants {
	
	public Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	Double h = SCREEN_SIZE.getHeight();
	Double w = SCREEN_SIZE.getWidth();
	public int HEIGHT = h.intValue();
	public int WIDTH = w.intValue();

	final int LEFT = 1;
    final int RIGHT = 2;
    final int UP = 3;
    final int DOWN = 4;
    final int STOP = 0;

	public static final int GAME_START=0;
	public static final int IN_PROGRESS=1;
	public final int GAME_END=2;
	public final int WAITING_FOR_PLAYERS=3;

	public static final int PORT=4444;	
}