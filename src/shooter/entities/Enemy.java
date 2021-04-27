package shooter.entities;

import java.awt.Graphics;
import shooter.Handler;
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
        //TODO render Enemies
    }
}
