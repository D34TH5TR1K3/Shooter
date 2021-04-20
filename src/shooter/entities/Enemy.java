package shooter.entities;

import java.awt.Graphics;
import shooter.Handler;

public class Enemy extends Entity{
    private final int SPEED = 8;
    private Item gun;

    public Enemy(int posX, int posY, int gunType, int width, int height, Handler handler) {
        super(posX, posY, 4, width, height, handler);
        gun = new Item(posX, posY, gunType, width, height, handler);
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
