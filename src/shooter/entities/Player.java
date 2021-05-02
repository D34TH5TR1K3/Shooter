package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.World;

public class Player extends Entity{
    private Rectangle hitbox;
    private int velX = 0, velY = 0;   //velocity in x and y direction
    private int velXmax = 10, velYmax = 10;   //maximum velocity
    private Item gun = null;
    private int imageWidth = 50, imageHeight = 50;
    private float PosX, PosY;
    private Handler handler;
    private World world;
    private Item item;
    private Animation walkAnimation, walkAnimation_ak;

    public Player(int posX, int posY, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        this.PosX = posX;
        this.PosY = posY;
        this.handler = handler;
        this.world = world;
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent
    }

    public Player(int posX, int posY, float dir, int width, int height, Handler handler, World world) {
        super(posX, posY, 4, handler, world);
        this.dir = dir;
        this.PosX = posX;
        this.PosY = posY;
        this.handler = handler;
        this.world = world;
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        item = new Item(posX, posY, 2, 20, 20, handler, world); //temporary
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent

        walkAnimation = new Animation(100, Assets.enemy_walk);
        walkAnimation_ak = new Animation(100, Assets.enemy_walk_ak);
        activAnimation = walkAnimation;
    }

    @Override
    public void tick() {
        if(item != null) {
            switch (item.getType()) {
                case 1:
                    break;
                case 2:
                    activAnimation = walkAnimation_ak;
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    activAnimation = walkAnimation;
                    break;
            }
            if(handler.getMouseManager().isLeftPressed()){
                item.activate(this);
                //world.getEntityManager().addEntitytemp(new Bullet(posX + CREATURESIZE/2, posY + CREATURESIZE/2, dir + 180, handler, world));
            }
        }
        activAnimation.tick();
        //System.out.println(posX+"   "+posY);
        //System.out.println(posX + "   "+posY+"   "+velX);
        //System.out.println(hitbox.getBounds());
        //hitbox.setLocation(((int) posX + CREATURESIZE/2 - 15), ((int) posY + CREATURESIZE/2 - 25));
        dir = (float) (180 + Math.toDegrees(Math.atan2(posY - handler.getMouseManager().getMouseY() - handler.getGameCamera().getyOffset() + CREATURESIZE/2, posX - handler.getMouseManager().getMouseX() - handler.getGameCamera().getxOffset() + CREATURESIZE/2)));
        //setDir(dirPlayer);
        if(handler.getKeyManager().left && velX > -velXmax){
            velX -= 1;
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
                    hitbox.setLocation(((int) posX), ((int) (posY)));
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
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(dir), posX+60-handler.getxOffset(), posY+60-handler.getyOffset());

        g2d.drawImage(activAnimation.getCurrentFrame(), (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);

        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
        //TODO render Player
    }
}
