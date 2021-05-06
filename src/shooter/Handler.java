package shooter;

import shooter.gfx.GameCamera;
import shooter.gfx.World;
import shooter.input.*;
import shooter.states.GameState;

public class Handler {
    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    public float getxOffset(){
        return game.getGameCamera().getxOffset();
    }
    public float getyOffset(){
        return game.getGameCamera().getyOffset();
    }
}
