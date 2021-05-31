package shooter;

import shooter.gfx.GameCamera;
import shooter.gfx.World;
import shooter.input.*;

public class Handler {
    //game saves the game to distribute its attributes
    private final Game game;
    private World world;

    //this constructor initializes the values
    public Handler(Game game) {
        this.game = game;
    }

    //Getters und Setters
    public Game getGame() { return game; }
    public void setWorld(World world) { this.world = world; }
    public World getWorld() { return world; }
    public KeyManager getKeyManager() { return game.getKeyManager(); }
    public MouseManager getMouseManager() { return game.getMouseManager(); }
    public GameCamera getGameCamera() { return game.getGameCamera(); }
    public float getxOffset(){ return game.getGameCamera().getxOffset(); }
    public float getyOffset(){ return game.getGameCamera().getyOffset(); }
}