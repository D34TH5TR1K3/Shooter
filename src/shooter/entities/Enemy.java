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

import static java.lang.Math.abs;

public class Enemy extends Entity{
    //PATHFINDING
    private final ArrayList<Tile> openlist = new ArrayList<>();
    private final ArrayList<Tile> closedlist = new ArrayList<>();
    private final ArrayList<Tile> neighbors = new ArrayList<>();
    private short iterations = 0;
    private short pathlength = 0;
    private int addGCost, dx, dy;
    private int tempHCost, tempGCost;
    public ArrayList<Tile> trace = new ArrayList<>();
    private Tile current = null;
    private Tile tempNode = null;
    //END PATHFINDING
    private Rectangle hitbox;
    private final byte SPEED = 8;
    private int imageWidth = 50, imageHeight = 50;
    private Item item;
    private Animation walkAnimation, walkAnimation_ak;

    //this constructor initializes the values
    public Enemy(int posX, int posY, int dir, int gunType, Handler handler, World world) {  //im Konstruktor werden die Position und die Animation des Gegners initialisiert
        super(posX, posY, 4, dir, handler, world);
        this.setActive();
        hitbox = new Rectangle(posX + CREATURESIZE/2 - 25, posY + CREATURESIZE/2 - 25, imageWidth, imageHeight);
        item = new Item(posX, posY, gunType, handler, world);
        item.setInActive();
        world.getEntityManager().addItem(item);
        walkAnimation = new Animation(Assets.enemy_walk,100);
        walkAnimation_ak = new Animation(Assets.enemy_walk_ak,100);
        activeAnimation = walkAnimation_ak;
    }

    public void followTrace(ArrayList<Tile> trace){
        if(!trace.isEmpty() && trace.size() > 1){
            Tile currentTarget = trace.get(trace.size() - 2);
            //currentTarget.setColor(Color.white);
            //System.out.println(Math.abs(posX+ CREATURESIZE/2 - currentTarget.getTposX()*30+15)+"   "+Math.abs(posY+ CREATURESIZE/2 - currentTarget.getTposY()*30+15));
            if(Math.abs(posX+ CREATURESIZE/2 - currentTarget.getTposX()*30-15) < 20 && Math.abs(posY+ CREATURESIZE/2 - currentTarget.getTposY()*30-15) < 20){
                trace.remove(currentTarget);
            }
//            posX = currentTarget.getposX() - CREATURESIZE/2;
//            posY = currentTarget.getposY() - CREATURESIZE/2;
//            trace.remove(currentTarget);

//            dir = (float) (180+Math.toDegrees(Math.atan2(posY+ CREATURESIZE/2 - currentTarget.getTposY()*30 +15, posX+ CREATURESIZE/2- currentTarget.getTposX()*30+15)));
            dir = (float) (180 + Math.toDegrees(Math.atan2(posY +CREATURESIZE/2- currentTarget.getTposY()*30-15, posX +CREATURESIZE/2- currentTarget.getTposX()*30-15)));
            //System.out.println(posY+ 90+"   "+posX+ 90+"   "+currentTarget.getTposY()*30+"   "+currentTarget.getTposX()*30);
            posX = posX + (float) (Math.cos(Math.toRadians(dir+180) + Math.PI) * 5);
            posY = posY + (float) (Math.sin(Math.toRadians(dir+180) + Math.PI) * 5);
        }

    }
    public void drawtrace(Tile end, Tile start){
        //System.out.println("drawing trace");
        if(trace.isEmpty()){
            trace.add(end);
        }
        if(end != start) {
            trace.add(end.getParent());
            end.getParent().setColor(Color.orange); //Uncomment to mark path
            pathlength++;
            drawtrace(end.getParent(), start);
        }

    }
    public void resettiles(){
        for(int i = 0; i < 64 * world.getMapsize(); i++){
            for(int j = 0; j < 36 * world.getMapsize(); j++){

//                if(!world.getTiles()[i][j].isSolid()){
//                    world.getTiles()[i][j].setColor(Color.cyan);
//                }else if(world.getTiles()[i][j].isSolid()){
//                    world.getTiles()[i][j].setColor(Color.black);
//                }
                world.getTiles()[i][j].setColor(Color.green);
                //tiles[i][j].setSolidFalse();
                world.getTiles()[i][j].setParent(null);
                world.getTiles()[i][j].setVisited(false);
                world.getTiles()[i][j].setfCost(0);
                world.getTiles()[i][j].sethCost(0);
                world.getTiles()[i][j].setgCost(0);
            }
        }
    }
    public void findpath(Tile start, Tile end){
        resettiles();
        //System.out.println(start.getTposX()+"   "+start.getTposY()+"   "+end.getTposX()+"   "+end.getTposY());
        closedlist.clear();
        openlist.clear();
        start.setColor(Color.red);
        end.setColor(Color.green);
        openlist.add(start);
        start.setVisited(true);

        while(!openlist.isEmpty()){
            iterations += 1;
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("loopStart");
            current = openlist.get(0);
            for(Tile t : openlist){
                if(t.getfCost() < current.getfCost()){
                    current = t;
                }
            }
            //System.out.println("OpenlistCost:  ");
            //for(tile t : openlist){
            //    System.out.println(t.getfCost());
            //}
            //System.out.print("current Cost: ");
            //System.out.println(current.getfCost());
            current.setColor(Color.pink);
            openlist.remove(current);
            closedlist.add(current);

            if(current == end){
                //System.out.println("Target found!");
                //current.setColor(Color.green);
                trace.clear();
                pathlength = 0;
                drawtrace(current, start);
                //start();
                break;
            }

            neighbors.clear();
            neighborsadd(current.getTposX() - 1, current.getTposY());
            neighborsadd(current.getTposX() + 1, current.getTposY());
            neighborsadd(current.getTposX(),        current.getTposY() -1);
            neighborsadd(current.getTposX(),        current.getTposY() +1);
            if(current.getTposX()-1 >= 0 && current.getTposY()-1 >= 0) {
                if (!world.getTiles()[current.getTposX()][current.getTposY() - 1].isHalfSolid() || !world.getTiles()[current.getTposX() - 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() - 1, current.getTposY() - 1);// LINKS OBEN
                }
            }
            if(current.getTposX()+1 <36 * world.getMapsize() && current.getTposY()-1 >= 0) {
                if (!world.getTiles()[current.getTposX()][current.getTposY() - 1].isHalfSolid() || !world.getTiles()[current.getTposX() + 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() + 1, current.getTposY() - 1);// RECHTS OBEN
                }
            }
            if(current.getTposX()-1 >= 0 && current.getTposY()+1 < 36 * world.getMapsize()) {
                if (!world.getTiles()[current.getTposX()][current.getTposY() + 1].isHalfSolid() || !world.getTiles()[current.getTposX() - 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() - 1, current.getTposY() + 1);// LINKS UNTEN
                }
            }
            if(current.getTposX()+1 < 36 * world.getMapsize() && current.getTposY()+1 < 36 * world.getMapsize()) {
                if (!world.getTiles()[current.getTposX()][current.getTposY() + 1].isHalfSolid() || !world.getTiles()[current.getTposX() + 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() + 1, current.getTposY() + 1);// RECHTS UNTEN
                }
            }

            for (Tile temptile : neighbors) {
//                if(false) {     //toggle experimental diagonal avoidance
//                    if (i == 0) {
//                        wallL = neighbors.get(i).isSolid();
//                        System.out.println("WALLS:");
//                        System.out.println(wallL);
//                    } else if (i == 1) {
//                        wallR = neighbors.get(i).isSolid();
//                        System.out.println(wallR);
//                    } else if (i == 2) {
//                        wallO = neighbors.get(i).isSolid();
//                        System.out.println(wallO);
//                    } else if (i == 3) {
//                        wallU = neighbors.get(i).isSolid();
//                        System.out.println(wallU);
//                        System.out.println("WALLS_END");
//                    } else if (i == 4 && wallO == true && wallL == true) {
//                        neighbors.get(i).setSolid();
//                    } else if (i == 5 && wallO == true && wallR == true) {
//                        neighbors.get(i).setSolid();
//                    } else if (i == 6 && wallU == true && wallL == true) {
//                        neighbors.get(i).setSolid();
//                    } else if (i == 7 && wallU == true && wallR == true) {
//                        neighbors.get(i).setSolid();
//                    }
//                }
                //System.out.println(neighbors.size());
                if (!temptile.isClosed() && !temptile.isHalfSolid()
                ) {


                    if (current.getTposX() == temptile.getTposX() || current.getTposY() == temptile.getTposY()) {
                        addGCost = 10;
                    } else {
                        addGCost = 14;
                    }
                    tempGCost = (current.getgCost() + addGCost);

                    int mathfunction = 1;
                    if (mathfunction == 0) {
                        dx = end.getTposX() - temptile.getTposX();                      //pythagoras
                        dy = end.getTposY() - temptile.getTposY();                      //pythagoras
                        tempHCost = ((int) (10 * Math.sqrt((dx * dx) + (dy * dy))));  //pythagoras
                    } else if (mathfunction == 1) {
                        dx = abs(end.getTposX() - temptile.getTposX()); //fast distance
                        dy = abs(end.getTposY() - temptile.getTposY()); //fast distance
                        tempHCost = (dx + dy) * 10;             //fast distance
                    }

                    if (tempHCost + tempGCost < temptile.getfCost() || !temptile.isVisited()) {
                        temptile.sethCost(tempHCost);
                        temptile.setgCost(tempGCost);

                        temptile.setParent(current);
                        if (!temptile.isVisited()) {
                            //System.out.println("helloOoOoo");
                            openlist.add(temptile);
                            temptile.setVisited(true);
                            temptile.setColor(Color.blue);
                        }
                    }

                }
                //if(temptile.isClosed() || temptile.isSolid()){
                //    neighbors.remove(temptile);
                //}
            }
            //render(g);
        }
    }
    public void neighborsadd(int x, int y){

        if(x < 64 * world.getMapsize() && x > -1 && y < 36 * world.getMapsize() && y > -1){
            neighbors.add(world.getTiles()[x][y]);
            //System.out.println("neigbours added");
        }else{
            //System.out.println("LESS THAN 8 NEIGHBOURS");
        }
    }
    public void die(){
        item.drop(this);
        this.hitbox = null;
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
        if(handler.getMouseManager().isRightPressed())
            findpath(world.getTiles(((int) ((posX +CREATURESIZE/2) / 30)), ((int) ((posY+CREATURESIZE/2) / 30))), world.getTiles((int) ((world.getPlayer().getX()+CREATURESIZE/2) / 30), (int) ((world.getPlayer().getY()+CREATURESIZE/2) / 30)));

        //findpath(world.getTiles(3, 3), world.getTiles(30, 30));
        if(item.getAmmo()==0&&this.active){
            item.reload();
            return;
        }
        if(active) {
            hitbox.setLocation(((int) (posX + CREATURESIZE / 2 - 25)), ((int) (posY + CREATURESIZE / 2 - 25)));
            //System.out.println(hitbox);
            if (lineOfSight()) {
                trace.clear();
                dir = (float) (180 + Math.toDegrees(Math.atan2(posY - world.getPlayer().getY(), posX - world.getPlayer().getX() )));
                if (item != null){
                    item.activate(this);
                }
            }else{
                followTrace(trace);
            }
        }
        activeAnimation.tick();
    }
    @Override
    public void render(Graphics g) {
        //g.drawLine((int) (world.getPlayer().getX()- handler.getxOffset())+CREATURESIZE/2, (int) (world.getPlayer().getY()+CREATURESIZE/2- handler.getyOffset()), (int) (posX+CREATURESIZE/2- handler.getxOffset()), (int) (posY+CREATURESIZE/2- handler.getyOffset()));
        //System.out.println("Position"+posX+"\t"+posY);
        //System.out.println("Offset"+handler.getxOffset()+"\t"+ handler.getyOffset());
//        if(hitbox != null){
//            g.drawRect((int) ((int) hitbox.getX()- handler.getxOffset()), (int) ((int) hitbox.getY()- handler.getyOffset()), ((int) hitbox.getWidth()), ((int) hitbox.getHeight()));
//        }
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform reset = g2d.getTransform();
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.rotate(Math.toRadians(dir), posX+CREATURESIZE/2-handler.getxOffset(), posY+CREATURESIZE/2-handler.getyOffset());

        g2d.drawImage(activeAnimation.getCurrentFrame(), (int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE, null);

        g2d.setTransform(reset);

        //g.fillRect((int)(posX-handler.getxOffset()), (int)(posY-handler.getyOffset()), Entity.CREATURESIZE, Entity.CREATURESIZE);
    }

    //Getters
    public Rectangle getHitbox(){
        return hitbox;
    }
    public Item getItem(){
        return item;
    }

    public String getData(){
        return ((int)posX+","+(int)posY+","+(int)dir+","+item.getType()+","+item.getAmmo());
    }
}
