package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.World;

public class Enemy extends Entity{
    private Rectangle hitbox;
    private final int SPEED = 8;
    private int imageWidth = 50, imageHeight = 50;
    private Item item;
    private Animation walkAnimation, walkAnimation_ak;

    public Enemy(int posX, int posY, int dir, int gunType, Handler handler, World world) {
        super(posX, posY, 4, dir, handler, world);
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        item = new Item(posX, posY, gunType, 20, 20, handler, world);
        item.setInActive();
        world.getEntityManager().addEntity(item);
        walkAnimation = new Animation(100, Assets.enemy_walk);
        walkAnimation_ak = new Animation(100, Assets.enemy_walk_ak);
        activeAnimation = walkAnimation;
    }
    public void die(){
        item.drop(this);
        hitbox = null;
        this.setInActive();
        activeAnimation.stop();
        //TODO: implement corpse texture
    }
    public boolean lineOfSight(){
        Line2D line = new Line2D.Float(world.getPlayer().getX(),world.getPlayer().getY(),posX,posY);
        if()
        return true;
    }
    @Override
    public void tick() {
        activeAnimation.tick();
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

        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
    }
    public Rectangle getHitbox(){
        return hitbox;
    }
}
