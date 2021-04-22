package shooter.entities;

import java.awt.*;
import shooter.Handler;
import shooter.gfx.Assets;

public class Player extends Entity{
    private final int SPEED = 8;
    private Item gun = null;

    public Player(int posX, int posY, int width, int height, Handler handler) {
        super(posX, posY, 4, handler);
    }

    @Override
    public void tick() {
        move(((handler.getKeyManager().right)?SPEED:0)-((handler.getKeyManager().left)?SPEED:0),
                ((handler.getKeyManager().down)?SPEED:0)-((handler.getKeyManager().up)?SPEED:0));
        //TODO implement Handler
        //TODO player collision
        handler.getGameCamera().centerOnEntity(this);
    }
    @Override
    public void render(Graphics g) {
        System.out.println("Position"+posX+"\t"+posY);
        System.out.println("Offset"+handler.getxOffset()+"\t"+ handler.getyOffset());
        g.setColor(Color.DARK_GRAY);
        g.fillRect(10,10,20,20);
        g.drawImage(Assets.player, (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);
        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
        //TODO render Player
    }
}
