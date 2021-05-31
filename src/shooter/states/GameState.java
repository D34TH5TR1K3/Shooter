package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.World;

import java.awt.*;

public class GameState extends State {
    //world saves the world to distribute variables
    private final World world;

    //this constructor initializes the values
    public GameState(Game game, Handler handler) {
        super(game,handler);
        world = handler.getGame().getWriter().createGame(handler);
    }

    //ticks the world
    @Override
    public void tick() {
        if(handler.getKeyManager().esc)
            State.setState(handler.getGame().menuState);
        world.tick();
    }
    //renders the world
    @Override
    public void render(Graphics g) {
        world.render(g);
    }

    //getters and setters
    public World getWorld(){ return world; }
}
