package shooter.entities;

import java.awt.Graphics;

public class Enemy extends Entity{
    private final int SPEED = 8;
    private Item gun;

    public Enemy(int posX, int posY, int gunType) {
        super(posX, posY, 4);
        gun = new Item(posX, posY, gunType);
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
