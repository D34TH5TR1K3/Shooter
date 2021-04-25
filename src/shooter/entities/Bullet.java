package shooter.entities;

import java.awt.Graphics;

import shooter.Handler;
import shooter.gfx.World;
import shooter.gfx.Tile;

public class Bullet extends Entity {
    private float speed = 25f;      //speed multiplication

    public Bullet(float posX, float posY, float dir, Handler handler, World world) {
        super(posX, posY,3, dir, handler, world);

    }
    public boolean collisionBullet(){
        int tx = (int) ((posX + (Math.cos(dir + Math.PI) * speed*2) +32 )/ Tile.width);
        int ty = (int) ((posY + (Math.sin(dir + Math.PI) * speed*2) +32 )/ Tile.height);

        if(collisionCheck()){

            return true;

        }

        else {
            return false;
    }


    @Override
    public void tick() {
        float amtX = 0;
        float amtY = 0;
        //TODO implement directional movement
        move(amtX, amtY);
    }
    @Override
    public void render(Graphics g) {
        g.drawOval(0,0,0,0);
        //TODO implement rendering for the Bullet
    }
}
