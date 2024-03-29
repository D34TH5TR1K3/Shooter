package shooter.world;

import shooter.Handler;
import shooter.states.State;
import shooter.utils.Writer;

import java.awt.*;

public class World {
    //handler distributes variables
    private final Handler handler;
    //levels contain all entities and the map
    private Level[] levels;
    //the active level gets ticked
    private Level activeLevel;
    //the number of the current level
    private int activeLevelNumber = 1;

    //this constructor initializes the values
    public World(Handler handler) {
        this.handler = handler;
        levels = new Level[]{
                Writer.loadGameSave(handler),
                Writer.loadLevel(1,handler),
                Writer.loadLevel(2,handler),
                Writer.loadLevel(3,handler),
                Writer.loadLevel(4,handler),
                Writer.loadLevel(5,handler),
                Writer.loadLevel(6,handler),
                Writer.loadLevel(7,handler),
                Writer.loadLevel(8,handler)
        };
        activeLevel = levels[activeLevelNumber];
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
        levels = new Level[]{
                Writer.loadGameSave(handler),
                Writer.loadLevel(1,handler),
                Writer.loadLevel(2,handler),
                Writer.loadLevel(3,handler),
                Writer.loadLevel(4,handler),
                Writer.loadLevel(5,handler),
                Writer.loadLevel(6,handler),
                Writer.loadLevel(7,handler),
                Writer.loadLevel(8,handler)
        };
        activeLevel = levels[activeLevel.getLevelNumber()];
    }
    //reloads a specific level
    public void reloadLevel(int levelNumber) {
        if(levelNumber==0) levels[levelNumber] = Writer.loadGameSave(handler);
        else levels[levelNumber] = Writer.loadLevel(levelNumber,handler);
        activeLevel = levels[levelNumber];
    }
    //sets the next level active
    public void nextLevel(){
        int levelNumber = (activeLevelNumber==0)?activeLevel.getLevelNumber()+1:activeLevelNumber+1;
        if(levelNumber > 8) {
            State.setState(handler.getGame().menuState);
            levelNumber = 1;
        }
        reloadLevel(levelNumber);
        activeLevel=levels[levelNumber];
        activeLevelNumber = activeLevel.getLevelNumber();
        activeLevel.start();
    }

    //getters and setters
    public Level getActiveLevel() { return activeLevel; }
    public void setLevel(int num) { activeLevel = levels[num]; activeLevelNumber = num; }
}
