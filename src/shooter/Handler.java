package shooter;

import shooter.gfx.GameCamera;
import shooter.input.*;

public class Handler {
    private final Game game;                    //game ist die Variable in der der Handler das Spiel speichert

    public Handler(Game game) {                 //in dem Konstruktor von Handler wird das Spiel übergeben und gespeichert
        this.game = game;
    }

    //Getters und Setters
    public Game getGame() {
        return game;
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }
    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public GameCamera getGameCamera() {
        return game.getGameCamera();
    }
    public float getxOffset(){
        return game.getGameCamera().getxOffset();
    }
    public float getyOffset(){
        return game.getGameCamera().getyOffset();
    }
}

//TODO: world in Handler