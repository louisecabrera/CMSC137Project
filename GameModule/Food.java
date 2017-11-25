import java.awt.Rectangle;

public class Food{

    private int x, y, width, height;
    private boolean visible;
    int timer;
    
    public Food(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.visible = true;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void eaten(){
        this.visible = false;
        this.timer = 10000;
    }

    public void awaitRespawn(){
        this.timer = this.timer - 10;
        if(this.timer == 0){
            this.respawn();
        }
    }

    public void respawn(){
        this.visible = true;
    }

    // makes rectangle for collision checking
    public Rectangle getBounds(){
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public boolean isVisible(){
        return this.visible;
    }
}