import java.awt.Color;
import java.awt.Image;

public interface MapInterface{ //declaration of constants
    public static int SCREEN_WIDTH = 960;
    public static int SCREEN_HEIGHT = 480;
    public static int ROWS = 12;
    public static int COLUMNS = 24;
    public static int TILE_DIMS = 40;

    public static int EMPTY_TILE = 0;
    public static int FOOD_TILE = 1;
    public static int WALL_TILE = 2;

    public static Color PACMAN_COLOR = new Color(244, 212, 66);
    public static Color WALL_COLOR = Color.BLUE;
    public static Color EMPTY_COLOR = Color.BLACK;
    public static Color FOOD_COLOR = Color.WHITE;

    public static final int[][] CELL = {
        {0,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1},
        {1,2,2,2,2,1,2,1,1,1,1,1,1,1,1,1,2,2,1,2,1,2,2,1},
        {1,2,2,2,2,1,1,1,2,2,2,2,2,2,2,1,2,2,1,2,1,2,2,1},
        {1,2,2,1,1,1,2,2,2,2,2,2,2,2,2,1,2,2,1,2,1,2,2,1},
        {1,2,2,1,2,2,2,1,1,1,1,1,1,1,1,1,2,2,1,2,1,2,2,1},
        {1,1,1,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,1,2,1,2,2,1},
        {1,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,1,2,1,2,2,1},
        {1,2,2,1,2,2,2,1,2,2,2,1,2,2,2,1,2,2,1,2,1,2,2,1},
        {1,2,2,1,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,2,2,1,2,2,1,1,2,2,2,2,2,2,2,2,2,2,1,2,2,1,2,1},
        {1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,1,2,2,1,2,1},
        {2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,1,1,1}
    };
}