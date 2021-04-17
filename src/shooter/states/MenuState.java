package shooter.states;

import shooter.Game;
import shooter.gfx.Menu;

import java.awt.Graphics;

public class MenuState extends State {

    private Menu menu1;

    public MenuState(Game game){
        super(game);
        this.menu1 = new Menu();
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
