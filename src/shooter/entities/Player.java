package shooter.entities;

import java.awt.Graphics;

public class Player extends Entity{
    private final int SPEED = 8;
    private Item gun = null;

    public Player(int posX, int posY) {
        super(posX, posY, 4);
    }

    @Override
    public void tick() {
        //move((keyManager.right)?SPEED:0-(keyManager.left)?SPEED:0,
        //        (keyManager.down)?SPEED:0-(keyManager.up)?SPEED:0);
        //TODO implement Handler
    }
    @Override
    public void render(Graphics g) {
        //TODO render Player
    }
}
