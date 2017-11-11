import java.awt.Dimension;
import javax.swing.JFrame;

public class Main{
    public static void main(String[] args){
        JFrame frame = new JFrame("The Pacman City");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(MapInterface.SCREEN_WIDTH, MapInterface.SCREEN_HEIGHT));
        frame.setResizable(false);
        Map map = new Map();
        Pacman pac = new Pacman(0,0);
        frame.add(pac);
        frame.add(map);
        frame.pack();
        frame.setVisible(true);
    }
}