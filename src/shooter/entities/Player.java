package shooter.entities;

import java.awt.*;
import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.World;

public class Player extends Entity{
    private Rectangle hitbox;
    private int velX = 0, velY = 0;   //velocity in x and y direction
    private int velXmax = 10, velYmax = 10;   //maximum velocity
    private Item gun = null;

    public Player(int posX, int posY, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 15, posY + CREATURESIZE/2 - 25, 30, 50);
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent
    }

    @Override
    public void tick() {
        //System.out.println(posX + "   "+posY+"   "+velX);
        //System.out.println(hitbox.getBounds());
        //hitbox.setLocation(((int) posX + CREATURESIZE/2 - 15), ((int) posY + CREATURESIZE/2 - 25));
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

        hitbox.setLocation(((int) (posX + CREATURESIZE/2 - 15 + velX)), ((int) (posY + CREATURESIZE/2 - 25 + velY)));
        if(!collisionCheck(hitbox)){
            move(velX, velY);
        }else {
            hitbox.setLocation(((int) (posX + CREATURESIZE/2 - 15 + velX)), ((int) (posY + CREATURESIZE/2 - 25)));
            if (!collisionCheck(hitbox)) {
                move(velX, 0);
            }else {
                hitbox.setLocation(((int) (posX + CREATURESIZE/2 - 15)), ((int) (posY + velY + CREATURESIZE/2 - 25)));
                if (!collisionCheck(hitbox)) {
                    move(0, velY);
                }else {
                    hitbox.setLocation(((int) (posX)), ((int) (posY - velY)));
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
        g.setColor(Color.DARK_GRAY);
        g.fillRect(10,10,20,20);
        g.drawImage(Assets.player, (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);
        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
        //TODO render Player
    }
}
