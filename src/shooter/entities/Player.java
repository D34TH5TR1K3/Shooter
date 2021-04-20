package shooter.entities;

import java.awt.*;
import shooter.Handler;

public class Player extends Entity{
    private final int SPEED = 8;
    private Item gun = null;

    public Player(int posX, int posY, int width, int height, Handler handler) {
        super(posX, posY, 4, width, height, handler);
    }

    @Override
    public void tick() {
        move(((handler.getKeyManager().right)?SPEED:0)-((handler.getKeyManager().left)?SPEED:0),
                ((handler.getKeyManager().down)?SPEED:0)-((handler.getKeyManager().up)?SPEED:0));
        //TODO implement Handler
        //TODO player collision
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int)(posX), (int)(posY), 30,  30);
        //System.out.println(posX);
        //System.out.println(posY);
        //TODO render Player
    }


}
