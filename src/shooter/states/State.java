package shooter.states;

import shooter.Game;
import shooter.Handler;

import java.awt.Graphics;

public abstract class State {
    private static State currentState = null;
    protected Game game;
    protected Handler handler;

    public State(Game game,Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    public abstract void tick();
    public abstract void render (Graphics g);

    public static void setState(State state) { currentState = state; }
    public static State getState() { return currentState; }
}
