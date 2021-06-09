package shooter.gfx;

import shooter.entities.Entity;

public class GameCamera {
    //saves the offset of the world
    private float xOffset, yOffset;

    //this constructor initializes the values
    public GameCamera(float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    //method to center the viewport on a given entity
    public void centerOnEntity(Entity e) {
        xOffset = e.getX() - 960;
        yOffset = e.getY() - 540;
    }

    //method to move a certain amount
    public void move(float xAmt, float yAmt) {
        xOffset += xAmt;
        yOffset += yAmt;
    }

    //getters
    public float getxOffset() {
        return xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }
}
