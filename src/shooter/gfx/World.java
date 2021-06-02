package shooter.gfx;

import shooter.Handler;
import shooter.entities.EntityManager;
import shooter.entities.Player;
import shooter.levels.Level;
import shooter.utils.Writer;

import java.awt.*;

public class World {
    //handler distributes variables
    private final Handler handler;
    //levels contain all entities and the map
    private Level[] levels;
    //the active level gets ticked
    private Level activeLevel;

    //this constructor initializes the values
    public World(Handler handler) {
        this.handler = handler;
        levels = new Level[]{Writer.loadLevel(1,handler)};
        activeLevel = levels[0];
    }

    //ticks the entityManager
    public void tick(){
        activeLevel.tick();
    }
    //renders the map and the entityManager
    public void render(Graphics g){
        activeLevel.render(g);
    }

    //getters and setters
    public EntityManager getEntityManager() { return activeLevel.getEntityManager(); }
    public Player getPlayer() { return activeLevel.getPlayer(); }
}
