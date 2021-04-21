package shooter.gfx;

import shooter.Game;
import shooter.Handler;
import shooter.entities.Entity;

public class GameCamera {
    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public void centerOnEntity(Entity e){
        xOffset = e.getX() - 960 + Entity.CREATURESIZE / 2;
        yOffset = e.getY() - 540 + Entity.CREATURESIZE / 2;
    }
    public void move(float xAmt, float yAmt){
        xOffset += xAmt;
        yOffset += yAmt;
    }
    public float getxOffset(){return xOffset;}
    public float getyOffset(){return yOffset;}
}
