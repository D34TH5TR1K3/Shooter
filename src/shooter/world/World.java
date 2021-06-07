package shooter.world;

import shooter.Handler;
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
        levels = new Level[]{Writer.loadGameSave(handler), Writer.loadLevel(1,handler), Writer.loadLevel(2,handler), Writer.loadLevel(3,handler), Writer.loadLevel(4,handler), Writer.loadLevel(5,handler)};
        activeLevel = levels[3];
    }

    //ticks the active Level
    public void tick(){
        activeLevel.tick();
    }
    //renders the active Level
    public void render(Graphics g){
        activeLevel.render(g);
    }

    //reloads the Levels
    public void reloadLevels(){
        levels = new Level[]{Writer.loadGameSave(handler), Writer.loadLevel(1,handler), Writer.loadLevel(2,handler), Writer.loadLevel(3,handler), Writer.loadLevel(4,handler), Writer.loadLevel(5,handler)};
         activeLevel = levels[activeLevel.getLevelNumber()];
    }

    //getters and setters
    public Level getActiveLevel() { return activeLevel; }
    public void setLevel(int num) { activeLevel = levels[num]; }
}
