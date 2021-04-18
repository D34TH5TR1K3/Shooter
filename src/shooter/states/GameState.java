package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.World;

import java.awt.*;

public class GameState extends State {

    private World world;

    public GameState(Game game, Handler handler) {
        super(game,handler);
        world = new World(handler);
    }

    @Override
    public void tick() {
        //TODO add player and world
    }
    @Override
    public void render(Graphics g) {
        //TODO add player and world
        g.setColor(Color.DARK_GRAY);
        g.fillOval(100,100,100,100);
    }
}
