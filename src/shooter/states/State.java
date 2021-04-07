package shooter.states;

import shooter.Game;

import java.awt.Graphics;

public abstract class State {
    private static State currentState = null;
    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public abstract void tick();
    public abstract void render (Graphics g);

    public static void setState(State state) { currentState = state; }
    public static State getState() { return currentState; }
}
