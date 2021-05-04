package shooter.entities;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.gfx.Tile;
import shooter.gfx.World;

public class Enemy extends Entity{
    private Rectangle hitbox;
    private final int SPEED = 8;
    private int imageWidth = 50, imageHeight = 50;
    private Item item;
    private Animation walkAnimation, walkAnimation_ak;

    public Enemy(int posX, int posY, int dir, int gunType, Handler handler, World world) {
        super(posX, posY, 4, dir, handler, world);
        this.setActive();
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        item = new Item(posX, posY, gunType, 20, 20, handler, world);
        item.setInActive();
        world.getEntityManager().addEntity(item);
        walkAnimation = new Animation(100, Assets.enemy_walk);
        walkAnimation_ak = new Animation(100, Assets.enemy_walk_ak);
        activeAnimation = walkAnimation_ak;
    }
    public void die(){
        item.drop(this);
        hitbox = null;
        this.setInActive();
        activeAnimation = walkAnimation;
        activeAnimation.stop();
        //TODO: implement corpse texture
    }
    public boolean lineOfSight(){
        ArrayList<Tile> tempTiles = new ArrayList<Tile>();
        //world.setAllTiles(Color.green);
        Line2D line = new Line2D.Float(world.getPlayer().getX()+CREATURESIZE/2,world.getPlayer().getY()+CREATURESIZE/2,posX+CREATURESIZE/2,posY+CREATURESIZE/2);
        //System.out.println(Math.toDegrees(Math.PI + Math.atan2(world.getPlayer().getY() - posY, world.getPlayer().getX() - posX)));
        float tempDir = (float) (Math.PI + Math.atan2(world.getPlayer().getY() - posY, world.getPlayer().getX() - posX));
        float tempX = posX + 90;
        float tempY = posY + 90;
        while(Math.abs(world.getPlayer().getX() + 90 - tempX) > 40 || Math.abs(world.getPlayer().getY() + 90 - tempY) > 40) {
            tempX = tempX + (float) (Math.cos(tempDir + Math.PI) * 30);
            tempY = tempY + (float) (Math.sin(tempDir + Math.PI) * 30);
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    Tile tempT = world.getTiles((int) (x+tempX / 30 - 1), (int) (y+tempY / 30 - 1));
                    if(!tempTiles.contains(tempT))
                        tempTiles.add(tempT);
                    //tempT.setColor(Color.pink);
                }
            }

        }
        for(Tile t : tempTiles){
            if(line.intersects(t.getHitbox()) && t.isSolid())
//                System.out.println("false");
                return false;
        }
//        System.out.println("true");
        return true;
    }
    @Override
    public void tick() {
        if(item.getAmmo()==0){
            item.reload();
            return;
        }
        if(active) {
            if (lineOfSight()) {
                dir = (float) (180 + Math.toDegrees(Math.atan2(posY - world.getPlayer().getY(), posX - world.getPlayer().getX() )));
                if (item != null)
                    item.activate(this);
                System.out.println("lineOfSight");
            }
        }
        activeAnimation.tick();
    }
    @Override
    public void render(Graphics g) {
        //g.drawLine((int) (world.getPlayer().getX()- handler.getxOffset())+CREATURESIZE/2, (int) (world.getPlayer().getY()+CREATURESIZE/2- handler.getyOffset()), (int) (posX+CREATURESIZE/2- handler.getxOffset()), (int) (posY+CREATURESIZE/2- handler.getyOffset()));
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
