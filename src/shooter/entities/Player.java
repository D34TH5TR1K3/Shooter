package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.World;

import static shooter.gfx.Display.fraktur;

public class Player extends Entity{
    private Rectangle hitbox;
    private int velX = 0, velY = 0;   //velocity in x and y direction
    private int velXmax = 10, velYmax = 10;   //maximum velocity
    private int imageWidth = 50, imageHeight = 50;
    private Item item;
    private Animation walkAnimation, walkAnimation_ak;
    private boolean ableToPickup = true;   //Prevents loop of picking up and dropping items when button held down. Will reset if button is released
    private boolean ableToDrop = true;

    public Player(int posX, int posY, float dir, Handler handler, World world) {
        super(posX, posY, 4,dir, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        item = new Item(posX, posY, 3, 20, 20, handler, world); //temporary
        item.setInActive();
        world.getEntityManager().addItem(item);
        for(int y = 0; y < 3; y++) {
            world.getEntityManager().addItem(new Item(100, 100+50*y, 1, 100, 100, handler, world));
            world.getEntityManager().addItem(new Item(150, 100+50*y, 2, 100, 100, handler, world));
            world.getEntityManager().addItem(new Item(200, 100+50*y, 3, 100, 100, handler, world));
            world.getEntityManager().addItem(new Item(250, 100+50*y, 4, 100, 100, handler, world));
            world.getEntityManager().addItem(new Item(300, 100+50*y, 5, 100, 100, handler, world));
        }
        //TODO automatically create hitbox by looking at player image and scanning for pixels not transparent
        // every anmiation has to be initialized here
        walkAnimation = new Animation(Assets.enemy_walk,100);
        walkAnimation_ak = new Animation(Assets.enemy_walk_ak,100);
        activeAnimation = walkAnimation;
    }

    @Override
    public void tick() {
        //System.out.println(posX+"  "+posY);
        if(item != null) {
            switch (item.getType()) {
                case 1:
                    break;
                case 2:
                    activeAnimation = walkAnimation_ak;
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    activeAnimation = walkAnimation;
                    break;
            }
            if(handler.getMouseManager().isLeftPressed()){
                item.activate(this);
                //world.getEntityManager().addEntitytemp(new Bullet(posX + CREATURESIZE/2, posY + CREATURESIZE/2, dir + 180, handler, world));
            }else if(handler.getMouseManager().isRightPressed() && ableToDrop){
                item.drop(this);
                item = null;
                ableToDrop = false;
                //world.getEntityManager().addEntitytemp(new Bullet(posX + CREATURESIZE/2, posY + CREATURESIZE/2, dir + 180, handler, world));
            }else if(!handler.getMouseManager().isRightPressed()){
                ableToDrop = true;
            }
        }else{
            activeAnimation = walkAnimation;
            if(handler.getMouseManager().isRightPressed() && ableToPickup){
                item = (Item)(world.getEntityManager().getClosestItem(posX, posY));
                if(item != null) {
                    item.pick_up(this);
                    ableToPickup = false;
                }
            }else if(!handler.getMouseManager().isRightPressed()){
                ableToPickup = true;
            }
        }
        activeAnimation.tick();
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
        if(velX != 0 || velY != 0) {
            hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25 + velX)), ((int) (posY + CREATURESIZE / 2 - 25 + velY)));
            if (!world.collisionCheck(hitbox)) {
                move(velX, velY);
                activeAnimation.start();
            } else {
                hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25 + velX)), ((int) (posY + CREATURESIZE / 2 - 25)));
                if (!world.collisionCheck(hitbox)) {
                    move(velX, 0);
                    activeAnimation.start();
                } else {
                    hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25)), ((int) (posY + velY + CREATURESIZE / 2 - 25)));
                    if (!world.collisionCheck(hitbox)) {
                        move(0, velY);
                        activeAnimation.start();
                    } else {
                        hitbox.setLocation(((int) posX), ((int) (posY)));
                    }
                }
            }
        }else{
            activeAnimation.stop();
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
        g2d.rotate(Math.toRadians(dir), posX+CREATURESIZE/2-handler.getxOffset(), posY+CREATURESIZE/2-handler.getyOffset());

        g2d.drawImage(activeAnimation.getCurrentFrame(), (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);
        g.setFont(fraktur);
        if(item!=null)
            g.drawString(Integer.toString(item.getAmmo()),100,800);
        else
            g.drawString("No Weapon",100,800);

        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
        //TODO render Player
    }

    public Item getItem(){
        return item;
    }

    public String getData(){
        return ((int)posX+","+(int)posY+","+(int)dir+","+item.getAmmo());
    }
}
