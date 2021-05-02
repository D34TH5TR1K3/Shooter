package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.World;

public class Enemy extends Entity{
    private final int SPEED = 8;
    private Item gun;

    public Enemy(int posX, int posY, int gunType, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        gun = new Item(posX, posY, gunType, width, height, handler, world);
    }

    @Override
    public void tick() {
        //TODO implement pathfinding and movement


    }
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform reset = g2d.getTransform();
        g2d.rotate(Math.toRadians(dir), posX+CREATURESIZE/2-handler.getxOffset(), posY+CREATURESIZE/2-handler.getyOffset());

        g2d.drawImage(Assets.enemy, (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);
    }
}
