package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.LoadingImage;
import shooter.world.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

import static shooter.gfx.Display.fraktur;

public class Player extends Entity{
    private float moveDir;
    //saves the players hitbox
    private final Rectangle hitbox;
    //velocities required for smooth movement
    private float velX = 0, velY = 0;
    //saves the equipped item
    private Item item;
    //indicates whether the player is alive
    private boolean alive = true;
    //saves the players animations
    private final Animation legAnimation;
    private final Animation[] walkAnimations;
    private final Animation[] attackAnimations;
    //required for player weapon interaction
    private boolean ableToPickup = true;
    private boolean ableToDrop = true;
    //provides a variable to change Player speed
    public static float speed = new shooter.utils.Writer().getSettingValue("Player Movement Speed");

    //this constructor initializes the values
    public Player(int posX, int posY, float dir, Handler handler, Level level) {
        super(posX, posY, 4,dir, handler, level);
        hitbox = new Rectangle(posX - 35, posY - 35, 70, 70);
        item = new Item(posX, posY, 3, handler, level); //temporary
        item.setInActive();
        level.getEntityManager().addEntity(item);
        for(int y = 0; y < 3; y++) {
            level.getEntityManager().addEntity(new Item(100, 100+50*y, 3, handler, level));
            level.getEntityManager().addEntity(new Item(150, 100+50*y, 5, handler, level));
            level.getEntityManager().addEntity(new Item(200, 100+50*y, 6, handler, level));
            level.getEntityManager().addEntity(new Item(250, 100+50*y, 7, handler, level));
        }
        legAnimation = new Animation(Assets.player_legs, 50, 16, 16);

        walkAnimations = new Animation[]{
                new Animation(Assets.player_walk,100, 15, 16),
                new Animation(Assets.player_walk_knife,100, 14, 15),
                new Animation(Assets.player_walk_machete, 100, 17, 22),
                new Animation(Assets.player_walk_handgun, 100, 9, 16),
                new Animation(Assets.player_walk_silencer, 100, 9, 16),
                new Animation(Assets.player_walk_uzi, 100, 10, 16),
                new Animation(Assets.player_walk_mp,100,10,16),
                new Animation(Assets.player_walk_shotgun, 100, 10, 16),
                null
        };
        attackAnimations = new Animation[]{
                new Animation(Assets.player_attack_unarmed,100, 15, 16),
                new Animation(Assets.player_attack_knife,100, 14, 15),
                new Animation(Assets.player_attack_machete, 100, 17, 22),
                new Animation(Assets.player_attack_handgun, 150, 9, 16),
                new Animation(Assets.player_attack_silencer, 100, 9, 16),
                new Animation(Assets.player_attack_uzi, 100, 10, 16),
                new Animation(Assets.player_attack_mp,100,10,16),
                new Animation(Assets.player_attack_shotgun, 100, 10, 16),
                null
        };

        activeAnimation = walkAnimations[0];
    }

    //ticks input, animation and other logic
    @Override
    public void tick() {
        if(item != null) {
            if(handler.getMouseManager().isLeftPressed()&&item.getAmmo()!=0) {
                item.activate(this);
                activeAnimation = attackAnimations[item.getType()];
            }
            if(handler.getMouseManager().isRightPressed() && ableToDrop){
                item.drop(this);
                item = null;
                ableToDrop = false;
            }else if(!handler.getMouseManager().isRightPressed())
                ableToDrop = true;
        }else{
            activeAnimation = walkAnimations[0];
            if(handler.getMouseManager().isRightPressed() && ableToPickup) {
                item = (Item) (level.getEntityManager().getClosestItem(posX, posY));
                if (item != null) {
                    item.pick_up(this);
                    ableToPickup = false;
                }
            }else if(!handler.getMouseManager().isRightPressed())
                ableToPickup = true;
        }
        if(activeAnimation.lastFrame()&&Arrays.asList(attackAnimations).contains(activeAnimation)) {
            activeAnimation.tick();
            activeAnimation = walkAnimations[(item==null)?0:item.getType()];
        }
        activeAnimation.tick();
        legAnimation.tick();
        dir = (float) (180 + Math.toDegrees(Math.atan2(posY - handler.getMouseManager().getMouseY() - handler.getGameCamera().getyOffset(), posX - handler.getMouseManager().getMouseX() - handler.getGameCamera().getxOffset())));
        float velXmax = speed / 5f + 5f,velYmax = speed / 5f + 5f;
        if(handler.getKeyManager().left && velX > -velXmax)
            velX--;
        else if(!handler.getKeyManager().right && !handler.getKeyManager().left)
            velX = 0;
        if(handler.getKeyManager().right && velX < velXmax)
            velX++;
        else if(!handler.getKeyManager().right && !handler.getKeyManager().left)
            velX = 0;
        if(handler.getKeyManager().up && velY > -velYmax)
            velY--;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
        if(handler.getKeyManager().down && velY < velYmax)
            velY++;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
        if(velX != 0 || velY != 0) {
            hitbox.setLocation(((int) (posX - 35 + velX)), ((int) (posY - 35 + velY)));
            if (!level.collisionCheck(hitbox)) {
                move(velX, velY);
                moveDir = (float) (Math.toDegrees(Math.atan2(velY , velX)));
                if(Arrays.asList(walkAnimations).contains(activeAnimation))
                    activeAnimation.start();
                legAnimation.start();
            } else {
                hitbox.setLocation(((int) (posX - 35 + velX)), ((int) (posY - 35)));
                if (!level.collisionCheck(hitbox)) {
                    move(velX, 0);
                    if(Arrays.asList(walkAnimations).contains(activeAnimation))
                        activeAnimation.start();
                    legAnimation.start();
                } else {
                    hitbox.setLocation(((int) (posX - 35)), ((int) (posY - 35)));
                    if (!level.collisionCheck(hitbox)) {
                        move(0, velY);
                        if(Arrays.asList(walkAnimations).contains(activeAnimation))
                            activeAnimation.start();
                        legAnimation.start();
                    } else {
                        hitbox.setLocation(((int) posX - 35), ((int) (posY - 35)));
                    }
                }
            }
        }else{
            legAnimation.stop();
            if(Arrays.asList(walkAnimations).contains(activeAnimation))
                activeAnimation.stop();
        }

        handler.getGameCamera().centerOnEntity(this);
    }
    //renders the Player
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), 5, 5);//debugging player pos
        AffineTransform reset = g2d.getTransform();

        g2d.rotate(Math.toRadians(moveDir), posX-handler.getxOffset(), posY-handler.getyOffset());
        g2d.drawImage(legAnimation.getCurrentFrame(), (int)(posX-legAnimation.getxOffset()*3-handler.getxOffset()), (int)(posY-legAnimation.getyOffset()*3-handler.getyOffset()), legAnimation.getWidth()*3, legAnimation.getHeight()*3, null);
        g2d.setTransform(reset);
        g2d.rotate(Math.toRadians(dir), posX-handler.getxOffset(), posY-handler.getyOffset());
        g2d.drawImage(activeAnimation.getCurrentFrame(), (int)(posX-activeAnimation.getxOffset()*3-handler.getxOffset()), (int)(posY-activeAnimation.getyOffset()*3-handler.getyOffset()), activeAnimation.getWidth()*3, activeAnimation.getHeight()*3, null);

        g2d.setTransform(reset);
        g.setFont(fraktur);
        if(item!=null)
            g.drawString(Integer.toString(item.getAmmo()),100,800);
        else
            g.drawString("No Weapon",100,800);
    }

    //lets the player die and resets the Level
    public void die() {
        //alive = false;
        //LoadingImage.renderDeathScreen();
    }

    //getters and setters
    public Item getItem(){ return item; }
    public String getData(){ return ((int)posX+","+(int)posY+","+(int)dir+","+item.getAmmo()); }
    public Rectangle getHitbox(){ return hitbox; }
    public boolean isAlive(){ return alive; }
}
