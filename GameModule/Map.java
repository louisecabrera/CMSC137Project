import java.awt.Graphics;
import javax.swing.JPanel;

public class Map extends JPanel implements MapInterface{
    int[][] grid = MapInterface.CELL;
    
    public Map(){

    }

    public void paint(Graphics graph2D){ //paints the background depending on the configuration of CELL 
        for(int i=0; i<MapInterface.ROWS; i++){
            for(int j=0; j<MapInterface.COLUMNS; j++){
                if(grid[i][j]==MapInterface.EMPTY_TILE){
                    graph2D.setColor(MapInterface.EMPTY_COLOR);
                    graph2D.fillRect(j*TILE_DIMS, i*TILE_DIMS, MapInterface.TILE_DIMS, MapInterface.TILE_DIMS);
                }
                else if(grid[i][j]==MapInterface.FOOD_TILE){
                    graph2D.setColor(MapInterface.EMPTY_COLOR);
                    graph2D.fillRect(j*TILE_DIMS, i*TILE_DIMS, MapInterface.TILE_DIMS, MapInterface.TILE_DIMS);
                    graph2D.setColor(MapInterface.FOOD_COLOR);
                    graph2D.fillOval(j*TILE_DIMS+15, i*TILE_DIMS+15, 10, 10);
                }
                else if(grid[i][j]==MapInterface.WALL_TILE){
                    graph2D.setColor(MapInterface.WALL_COLOR);
                    graph2D.fillRect(j*TILE_DIMS, i*TILE_DIMS, MapInterface.TILE_DIMS, MapInterface.TILE_DIMS);
                }
            }
        }
    }
}