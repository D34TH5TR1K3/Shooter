package shooter;

import shooter.gfx.GameCamera;
import shooter.input.*;

public class Handler {
    //game saves the game to distribute its attributes
    private final Game game;

    //this constructor initializes the values
    public Handler(Game game) {
        this.game = game;
    }

    //Getters und Setters
    public Game getGame() { return game; }
    public KeyManager getKeyManager() { return game.getKeyManager(); }
    public MouseManager getMouseManager() { return game.getMouseManager(); }
    public GameCamera getGameCamera() { return game.getGameCamera(); }
    public float getxOffset(){ return game.getGameCamera().getxOffset(); }
    public float getyOffset(){ return game.getGameCamera().getyOffset(); }
}