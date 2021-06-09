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
    //required for moving to other level
    boolean moveW = false, moveA = false, moveS = false, moveD = false;
    //provides a variable to change Player speed
    public static float speed = new shooter.utils.Writer().getSettingValue("Player Movement Speed");
    //which death image?
    public int deathImage;
    //this constructor initializes the values
    public Player(int posX, int posY, float dir, int itemType, int ammo, Handler handler, Level level) {
        super(posX, posY, 4,dir, handler, level);
        hitbox = new Rectangle(posX - 35, posY - 35, 70, 70);
        if(itemType>0) {
            item = new Item(posX, posY, itemType, handler, level);
            item.setAmmo(ammo);
            level.getEntityManager().addEntity(item);
            item.pick_up(this);
            ableToPickup = false;
        }

        for(int y = 0; y < 3; y++) {
            level.getEntityManager().addEntity(new Item(100+700, 100+50*y, 1, handler, level));
            level.getEntityManager().addEntity(new Item(150+700, 100+50*y, 2, handler, level));
            level.getEntityManager().addEntity(new Item(200+700, 100+50*y, 3, handler, level));
            level.getEntityManager().addEntity(new Item(250+700, 100+50*y, 4, handler, level));
            level.getEntityManager().addEntity(new Item(300+700, 100+50*y, 5, handler, level));
            level.getEntityManager().addEntity(new Item(350+700, 100+50*y, 6, handler, level));
            level.getEntityManager().addEntity(new Item(400+700, 100+50*y, 7, handler, level));
            level.getEntityManager().addEntity(new Item(450+700, 100+50*y, 8, handler, level));
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
                new Animation(Assets.player_attack_uzi, 50, 10, 16),
                new Animation(Assets.player_attack_mp,100,10,16),
                new Animation(Assets.player_attack_shotgun, 76, 10, 16),
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
                    activeAnimation = walkAnimations[item.getType()];
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
        if((handler.getKeyManager().left || moveA) && velX > -velXmax)
            velX--;
        else if((!handler.getKeyManager().right || moveD) && !handler.getKeyManager().left)
            velX = 0;
        if((handler.getKeyManager().right || moveD) && velX < velXmax)
            velX++;
        else if(!handler.getKeyManager().right && !handler.getKeyManager().left)
            velX = 0;
        if((handler.getKeyManager().up || moveW) && velY > -velYmax)
            velY--;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
        if((handler.getKeyManager().down || moveS) && velY < velYmax)
            velY++;
        else if(!handler.getKeyManager().down && !handler.getKeyManager().up)
            velY = 0;
        if(velX != 0 || velY != 0 ||moveW || moveA || moveS || moveD) {
            if(moveA) {
                velY = 0;
                velX = -2;
            }else if(moveD){
                velY = 0;
                velX = 2;
            }else if(moveS){
                velY = 2;
                velX = 0;
            }else if(moveW){
                velY = -2;
                velX = 0;
            }
            hitbox.setLocation(((int) (posX - 35 + velX)), ((int) (posY - 35 + velY)));
            if (!level.collisionCheck(hitbox) || moveW || moveA || moveS || moveD) {
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
                    hitbox.setLocation(((int) (posX - 35)), ((int) (posY - 35 + velY)));
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
        if(!moveW && !moveA && !moveS && !moveD)
            handler.getGameCamera().centerOnEntity(this);
    }
    //renders the Player
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), 5, 5);//debugging player pos
        AffineTransform reset = g2d.getTransform();
        if(alive) {
            g2d.rotate(Math.toRadians(moveDir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(legAnimation.getCurrentFrame(), (int) (posX - legAnimation.getxOffset() * 3 - handler.getxOffset()), (int) (posY - legAnimation.getyOffset() * 3 - handler.getyOffset()), legAnimation.getWidth() * 3, legAnimation.getHeight() * 3, null);
            g2d.setTransform(reset);
            g2d.rotate(Math.toRadians(dir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(activeAnimation.getCurrentFrame(), (int) (posX - activeAnimation.getxOffset() * 3 - handler.getxOffset()), (int) (posY - activeAnimation.getyOffset() * 3 - handler.getyOffset()), activeAnimation.getWidth() * 3, activeAnimation.getHeight() * 3, null);
        }else{
            g2d.rotate(Math.toRadians(dir+180), posX - handler.getxOffset(), posY - handler.getyOffset());
            System.out.println(deathImage);
            g2d.drawImage(Assets.player_die[deathImage], (int) (posX - 25 * 3 - handler.getxOffset()), (int) (posY - 16 * 3 - handler.getyOffset()), 60 * 3, 32 * 3, null);
        }
        g2d.setTransform(reset);
        //g.setColor(Color.cyan);
        //g.fillRect((int)(hitbox.getX() - handler.getxOffset()), (int)(hitbox.getY()-handler.getyOffset()), (int)hitbox.getWidth(), (int)hitbox.getHeight());
    }
    //lets the player die and resets the Level
    public void die() {
        alive = false;
        deathImage = (int)(Math.random() * 4);
        LoadingImage.renderDeathScreen();
    }
    //getters and setters
    public Item getItem(){ return item; }
    public void setItem(Item item){ this.item = item; }
    public String getData(){ return ((int)posX+","+(int)posY+","+(int)dir+","+((item != null) ? item.getType()+","+item.getAmmo() : 0+","+0)); }
    public Rectangle getHitbox(){ return hitbox; }
    public boolean isAlive(){ return alive; }
    public void setMoveTrue(int dir) {
        switch(dir){
            case 1:
                moveW = true;
                break;
            case 2:
                moveA = true;
                break;
            case 3:
                moveS = true;
                break;
            case 4:
                moveD = true;
                break;
        }
    }
    public void setMoveFalse(String dir) {
        switch(dir){
            case "W":
                moveW = false;
                break;
            case "A":
                moveA = false;
                break;
            case "S":
                moveS = false;
                break;
            case "D":
                moveD = false;
                break;
        }
    }
    public boolean isMoveW() {
        return moveW;
    }
    public boolean isMoveA() {
        return moveA;
    }
    public boolean isMoveS() {
        return moveS;
    }
    public boolean isMoveD() {
        return moveD;
    }
    public void resetMove(){
        moveW = false;
        moveA = false;
        moveS = false;
        moveD = false;
    }

}
