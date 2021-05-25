package shooter.states;

import shooter.Game;
import shooter.Handler;

import java.awt.Graphics;

public abstract class State {
    private static State currentState = null;   //hier wird gespeichert, ob gerade der Zustand mit dem Menü oder der mit dem Spiel aktiv ist
    protected Game game;                        //hier wird das Spiel gespeichert
    protected Handler handler;                  //hier wird der Handler gespeichert, der Variablen verteilt

    public State(Game game,Handler handler) {   //hier werden der Spiel- und der Handlervariable ihre Werte zugeschrieben
        this.game = game;
        this.handler = handler;
    }

    public abstract void tick();                //eine tick-methode ohne Körper, die von den states angepasst wird
    public abstract void render (Graphics g);   //eine render-methode ohne Körper, die von den states angepasst wird

    //Getters and Setters
    public static void setState(State state) { currentState = state; }
    public static State getState() { return currentState; }
}
