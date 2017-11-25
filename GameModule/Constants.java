import javax.swing.*;
import java.awt.*;

public interface Constants {
	
	public Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	Double h = SCREEN_SIZE.getHeight();
	Double w = SCREEN_SIZE.getWidth();
	public int HEIGHT = h.intValue();
	public int WIDTH = w.intValue();
}