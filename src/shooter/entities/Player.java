package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.World;

import static shooter.gfx.Display.fraktur;

public class Player extends Entity{
    //saves the players hitbox
    private final Rectangle hitbox;
    //velocities required for smooth movement
    private int velX = 0, velY = 0;
    //saves the equipped item
    private Item item;
    //saves the players animations
    private final Animation walkAnimation, walkAnimation_ak;
    //required for player weapon interaction
    private boolean ableToPickup = true;
    private boolean ableToDrop = true;

    //this constructor initializes the values
    public Player(int posX, int posY, float dir, Handler handler, World world) {
        super(posX, posY, 4,dir, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, 50, 50);
        item = new Item(posX, posY, 3, handler, world); //temporary
        item.setInActive();
        world.getEntityManager().addItem(item);
        for(int y = 0; y < 3; y++) {
            world.getEntityManager().addItem(new Item(100, 100+50*y, 1, handler, world));
            world.getEntityManager().addItem(new Item(150, 100+50*y, 2, handler, world));
            world.getEntityManager().addItem(new Item(200, 100+50*y, 3, handler, world));
            world.getEntityManager().addItem(new Item(250, 100+50*y, 4, handler, world));
            world.getEntityManager().addItem(new Item(300, 100+50*y, 5, handler, world));
        }
        walkAnimation = new Animation(Assets.enemy_walk,100);
        walkAnimation_ak = new Animation(Assets.enemy_walk_ak,100);
        activeAnimation = walkAnimation;
    }

    //ticks input, animation and other logic
    @Override
    public void tick() {
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
            if(handler.getMouseManager().isLeftPressed())
                item.activate(this);
            else if(handler.getMouseManager().isRightPressed() && ableToDrop){
                item.drop(this);
                item = null;
                ableToDrop = false;
            }else if(!handler.getMouseManager().isRightPressed())
                ableToDrop = true;
        }else{
            activeAnimation = walkAnimation;
            if(handler.getMouseManager().isRightPressed() && ableToPickup) {
                item = (Item) (world.getEntityManager().getClosestItem(posX, posY));
                if (item != null) {
                    item.pick_up(this);
                    ableToPickup = false;
                }
            }else if(!handler.getMouseManager().isRightPressed())
                ableToPickup = true;
        }
        activeAnimation.tick();
        dir = (float) (180 + Math.toDegrees(Math.atan2(posY - handler.getMouseManager().getMouseY() - handler.getGameCamera().getyOffset() + (float)CREATURESIZE/2, posX - handler.getMouseManager().getMouseX() - handler.getGameCamera().getxOffset() + (float)CREATURESIZE/2)));
        int velXmax = 10;
        if(handler.getKeyManager().left && velX > -velXmax)
            velX -= 1;
        else if(!handler.getKeyManager().right && !handler.getKeyManager().left)
            velX = 0;
        if(handler.getKeyManager().right && velX < velXmax)
            velX += 1;
        else if(!handler.getKeyManager().right && !handler.getKeyManager().left)
            velX = 0;
        int velYmax = 10;
        if(handler.getKeyManager().up && velY > -velYmax)
            velY -= 1;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
        if(handler.getKeyManager().down && velY < velYmax)
            velY += 1;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
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
                    } else
                        hitbox.setLocation(((int) posX), ((int) (posY)));
                }
            }
        }else
            activeAnimation.stop();
        handler.getGameCamera().centerOnEntity(this);
    }
    //renders the Player
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform reset = g2d.getTransform();
        g2d.rotate(Math.toRadians(dir), posX+(float)CREATURESIZE/2-handler.getxOffset(), posY+(float)CREATURESIZE/2-handler.getyOffset());

        g2d.drawImage(activeAnimation.getCurrentFrame(), (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);
        g.setFont(fraktur);
        if(item!=null)
            g.drawString(Integer.toString(item.getAmmo()),100,800);
        else
            g.drawString("No Weapon",100,800);
    }

    //getters and setters
    public Item getItem(){ return item; }
    public String getData(){ return ((int)posX+","+(int)posY+","+(int)dir+","+item.getAmmo()); }
}
