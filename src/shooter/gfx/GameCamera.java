package shooter.gfx;

import shooter.Game;
import shooter.entities.Entity;

public class GameCamera {
    private Game game;
    private float xOffset, yOffset;

    public GameCamera(Game game, float xOffset, float yOffset){
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public void centerOnEntity(Entity e){
        xOffset = e.getX() - 1920 / 2 +e.getWidth() / 2;
    }
}
