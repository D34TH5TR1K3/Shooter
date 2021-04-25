package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.World;

public class Player extends Entity{
    private Rectangle hitbox;
    private int velX = 0, velY = 0;   //velocity in x and y direction
    private int velXmax = 10, velYmax = 10;   //maximum velocity
    private Item gun = null;
    private int imageWidth = 50, imageHeight = 50;
    private float dir;

    public Player(int posX, int posY, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent
    }

    public Player(int posX, int posY, float dir, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        this.dir = dir;
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent
    }

    @Override
    public void tick() {
        //System.out.println(posX + "   "+posY+"   "+velX);
        //System.out.println(hitbox.getBounds());
        //hitbox.setLocation(((int) posX + CREATURESIZE/2 - 15), ((int) posY + CREATURESIZE/2 - 25));
        dir = (float) (180 + Math.toDegrees(Math.atan2(posY - handler.getMouseManager().getMouseY() - handler.getGameCamera().getyOffset() + CREATURESIZE/2, posX - handler.getMouseManager().getMouseX() - handler.getGameCamera().getxOffset() + CREATURESIZE/2)));
        if(handler.getKeyManager().left && velX > -velXmax){
            velX -= 1;
        }else if(handler.getKeyManager().left && velX == velXmax){

        }else if(!handler.getKeyManager().right && !handler.getKeyManager().left){
            velX = 0;
        }
        if(handler.getKeyManager().right && velX < velXmax){
            velX += 1;
        }else if(!handler.getKeyManager().right && !handler.getKeyManager().left){
            velX = 0;
        }
        if(handler.getKeyManager().up && velY > -velYmax){
            velY -= 1;
        }else if(!handler.getKeyManager().down && !handler.getKeyManager().up){
            velY = 0;
        }
        if(handler.getKeyManager().down && velY < velYmax){
            velY += 1;
        }else if(!handler.getKeyManager().down && !handler.getKeyManager().up){
            velY = 0;
        }
        hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25 + velX)), ((int) (posY + CREATURESIZE / 2 - 25 + velY)));
        if(!collisionCheck(hitbox)){
            move(velX, velY);
        }else {
            hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25 + velX)), ((int) (posY + CREATURESIZE / 2 - 25)));
            if (!collisionCheck(hitbox)) {
                move(velX, 0);
            }else {
                hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25)), ((int) (posY + velY + CREATURESIZE / 2 - 25)));
                if (!collisionCheck(hitbox)) {
                    move(0, velY);
                }else {
                    hitbox.setLocation(((int) posX), ((int) (posY - velY)));
                }
            }
        }
        /*move(((handler.getKeyManager().right)?SPEED:0)-((handler.getKeyManager().left)?SPEED:0),
                ((handler.getKeyManager().down)?SPEED:0)-((handler.getKeyManager().up)?SPEED:0));*/
        //TODO implement Handler
        //TODO player collision
        handler.getGameCamera().centerOnEntity(this);
    }
    @Override
    public void render(Graphics g) {
        //System.out.println("Position"+posX+"\t"+posY);
        //System.out.println("Offset"+handler.getxOffset()+"\t"+ handler.getyOffset());

        Graphics2D g2d = (Graphics2D)g;
        AffineTransform reset = g2d.getTransform();
        g2d.rotate(Math.toRadians(dir), posX+60-handler.getxOffset(), posY+60-handler.getyOffset());

        g.drawImage(Assets.player, (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);
        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
        //TODO render Player
    }
}
