package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.World;

import java.awt.*;

public class GameState extends State {

    private World world;                            //hier wird die Welt (Level) gespeichert, in der das tatsächliche Spiel stattfindet

    public GameState(Game game, Handler handler) {  //hier wird der GameState und die Welt initialisiert
        super(game,handler);
        world = handler.getGame().getWriter().createGame(handler);
    }

    @Override
    public void tick() {                            //hier wird der GameState getickt und somit die tick-methode der Speilwelt aufgerufen
        //TODO add player and world
        if(handler.getKeyManager().esc){
            State.setState(handler.getGame().menuState);
        }
        world.tick(); // tick world
    }

    @Override
    public void render(Graphics g) {                //hier wird die Welt gerendert und dargestellt
        //TODO add player and world
        //world.renderTiles(g);
        world.render(g);    //render world
    }

    //Getters und Setters
    public World getWorld(){
        return world;
    }
}
