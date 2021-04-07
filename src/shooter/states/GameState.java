package shooter.states;

import shooter.Game;

import java.awt.*;

public class GameState extends State {

    public GameState(Game game) {
        super(game);
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
