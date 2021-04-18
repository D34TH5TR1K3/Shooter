package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.Menu;

import java.awt.Graphics;

public class MenuState extends State {

    private Menu menu1;

    public MenuState(Game game, Handler handler){
        super(game,handler);
    }

    @Override
    public void tick() {
        menu1.tick();
    }
    @Override
    public void render(Graphics g){
        menu1.render(g);
    }
}
