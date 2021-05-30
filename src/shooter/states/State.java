package shooter.states;

import shooter.Game;
import shooter.Handler;

import java.awt.Graphics;

public abstract class State {
    //currentState organizes which State gets ticked and rendered
    private static State currentState = null;
    //game and handler distribute variables
    protected Game game;
    protected Handler handler;

    //this constructor initializes the values
    public State(Game game,Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    //abstract tick without a body
    public abstract void tick();
    //abstract render without a body
    public abstract void render (Graphics g);

    //getters and setters
    public static void setState(State state) { currentState = state; }
    public static State getState() { return currentState; }
}
