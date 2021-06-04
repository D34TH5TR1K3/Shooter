package shooter.entities;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.LoadingImage;
import shooter.world.Level;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static shooter.gfx.Display.fraktur;

public class Player extends Entity{
    private float moveDir;
    //saves the players hitbox
    private final Rectangle hitbox;
    //velocities required for smooth movement
    private int velX = 0, velY = 0;
    //saves the equipped item
    private Item item;
    //indicates whether the player is alive
    private boolean alive = true;
    //saves the players animations
    private final Animation legAnimation,
                            walkAnimation, walkAnimation_knife, walkAnimation_machete, walkAnimation_handgun, walkAnimation_uzi, walkAnimation_shotgun, walkAnimation_mp, walkAnimation_silencer,
                            attackAnimation_unarmed, attackAnimation_knife, attackAnimation_machete, attackAnimation_handgun, attackAnimation_uzi, attackAnimation_shotgun, attackAnimation_mp, attackAnimation_silencer;
    //required for player weapon interaction
    private boolean ableToPickup = true;
    private boolean ableToDrop = true;

    //this constructor initializes the values
    public Player(int posX, int posY, float dir, Handler handler, Level level) {
        super(posX, posY, 4,dir, handler, level);
        hitbox = new Rectangle(posX - 35, posY - 35, 70, 70);
        item = new Item(posX, posY, 3, handler, level); //temporary
        item.setInActive();
        level.getEntityManager().addEntity(item);
        for(int y = 0; y < 3; y++) {
            level.getEntityManager().addEntity(new Item(100, 100+50*y, 1, handler, level));
            level.getEntityManager().addEntity(new Item(150, 100+50*y, 2, handler, level));
            level.getEntityManager().addEntity(new Item(200, 100+50*y, 3, handler, level));
            level.getEntityManager().addEntity(new Item(250, 100+50*y, 4, handler, level));
            level.getEntityManager().addEntity(new Item(300, 100+50*y, 5, handler, level));
        }
        legAnimation = new Animation(Assets.player_legs, 50, 16, 16);

        walkAnimation = new Animation(Assets.player_walk,100, 15, 16);
        walkAnimation_knife = new Animation(Assets.player_walk_knife,100, 14, 15);
        walkAnimation_machete = new Animation(Assets.player_walk_machete, 100, 17, 22);
        walkAnimation_handgun = new Animation(Assets.player_walk_handgun, 100, 9, 16);
        walkAnimation_uzi = new Animation(Assets.player_walk_uzi,100,9 , 16);
        walkAnimation_shotgun = new Animation(Assets.player_walk_shotgun, 100, 10, 16);
        walkAnimation_mp = new Animation(Assets.player_walk_mp, 100, 10, 16);
        walkAnimation_silencer = new Animation(Assets.player_walk_silencer, 100, 9, 16);

        attackAnimation_unarmed = new Animation(Assets.player_attack_unarmed,100, 15, 16);
        attackAnimation_knife = new Animation(Assets.player_attack_knife,100, 14, 15);
        attackAnimation_machete = new Animation(Assets.player_attack_machete, 100, 17, 22);
        attackAnimation_handgun = new Animation(Assets.player_attack_handgun, 150, 9, 16);
        attackAnimation_uzi = new Animation(Assets.player_attack_uzi,50,9 , 16);
        attackAnimation_shotgun = new Animation(Assets.player_attack_shotgun, 100, 10, 16);
        attackAnimation_mp = new Animation(Assets.player_attack_mp, 100, 10, 16);
        attackAnimation_silencer = new Animation(Assets.player_attack_silencer, 100, 9, 16);

        activeAnimation = walkAnimation_uzi;
    }
    @Override
    public void shoot(int type){
        switch (type) {
            case 1:
                activeAnimation = attackAnimation_handgun;
                activeAnimation.incHealth();
                break;
            case 2:
                activeAnimation = attackAnimation_handgun;
                activeAnimation.incHealth();
                break;
            case 3:
                activeAnimation = attackAnimation_uzi;
                activeAnimation.incHealth();
                break;
            case 4:
                activeAnimation = attackAnimation_shotgun;
                activeAnimation.incHealth();
                break;
            case 5:
                activeAnimation = attackAnimation_handgun;
                activeAnimation.incHealth();
                break;
            default:
                activeAnimation = attackAnimation_unarmed;
                break;
        }
    }

    //ticks input, animation and other logic
    @Override
    public void tick() {
        if(item != null) {
            if(handler.getMouseManager().isLeftPressed())
                item.activate(this);
            if(activeAnimation.getHealth() <= 0) {
                switch (item.getType()) {
                    case 1:
                        activeAnimation = walkAnimation_handgun;
                        break;
                    case 2:

                        break;
                    case 3:
                        activeAnimation = walkAnimation_uzi;
                        break;
                    case 4:
                        activeAnimation = walkAnimation_shotgun;
                        break;
                    case 5:

                        break;
                    default:
                        activeAnimation = walkAnimation;
                        break;
                }
            }
            if(handler.getMouseManager().isRightPressed() && ableToDrop){
                item.drop(this);
                item = null;
                ableToDrop = false;
            }else if(!handler.getMouseManager().isRightPressed())
                ableToDrop = true;
        }else{
            activeAnimation = walkAnimation;
            if(handler.getMouseManager().isRightPressed() && ableToPickup) {
                item = (Item) (level.getEntityManager().getClosestItem(posX, posY));
                if (item != null) {
                    item.pick_up(this);
                    ableToPickup = false;
                }
            }else if(!handler.getMouseManager().isRightPressed())
                ableToPickup = true;
        }
        activeAnimation.tick();
        legAnimation.tick();
        dir = (float) (180 + Math.toDegrees(Math.atan2(posY - handler.getMouseManager().getMouseY() - handler.getGameCamera().getyOffset(), posX - handler.getMouseManager().getMouseX() - handler.getGameCamera().getxOffset())));
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
            hitbox.setLocation(((int) (posX - 35 + velX)), ((int) (posY - 35 + velY)));
            if (!level.collisionCheck(hitbox)) {
                move(velX, velY);
                moveDir = (float) (Math.toDegrees(Math.atan2(velY , velX)));
                activeAnimation.start();
                legAnimation.start();
            } else {
                hitbox.setLocation(((int) (posX - 35 + velX)), ((int) (posY - 35)));
                if (!level.collisionCheck(hitbox)) {
                    move(velX, 0);
                    activeAnimation.start();
                    legAnimation.start();
                } else {
                    hitbox.setLocation(((int) (posX - 35)), ((int) (posY - 35)));
                    if (!level.collisionCheck(hitbox)) {
                        move(0, velY);
                        activeAnimation.start();
                        legAnimation.start();
                    } else {
                        hitbox.setLocation(((int) posX - 35), ((int) (posY - 35)));
                    }
                }
            }
        }else{
            legAnimation.stop();
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
        alive = false;
        LoadingImage.renderDeathScreen();
    }

    //getters and setters
    public Item getItem(){ return item; }
    public String getData(){ return ((int)posX+","+(int)posY+","+(int)dir+","+item.getAmmo()); }
    public Rectangle getHitbox(){ return hitbox; }
    public boolean isAlive(){ return alive; }
}
