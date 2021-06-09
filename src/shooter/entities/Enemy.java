package shooter.entities;

import shooter.Handler;
import shooter.gfx.Animation;
import shooter.gfx.Assets;
import shooter.world.Level;
import shooter.world.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Enemy extends Entity{
    //PATHFINDING
    private final ArrayList<Tile> openlist = new ArrayList<>();     //tiles that need to be visited next
    private final ArrayList<Tile> closedlist = new ArrayList<>();   //tiles that have been visited previously
    private final ArrayList<Tile> neighbors = new ArrayList<>();    //tiles that are next to the current tile
    private int addGCost, dx, dy;                                   //GCost is the distance from start to the current tile
    private int tempHCost, tempGCost;                               //HCost is the heuristic distance from current tile to end tile
    public ArrayList<Tile> trace = new ArrayList<>();               //saved the found path
    private Tile current = null;                                    //saves current tile of the pathfinding process
    private int pathfindingDelay = 30;                              //delay between calling the pathfinding functions
    private boolean playerSpotted = false;                          //can the enemy look directly at the player?
    private int[] lastCoords = new int[2];                          //last coordinates the player was spottet at
    //END PATHFINDING
    private Rectangle hitbox;
    public static float speed = new shooter.utils.Writer().getSettingValue("Enemy Movement Speed") / 10;
    private Item item;
    private final Animation legAnimation;
    private final Animation[] walkAnimations;
    private final Animation[] attackAnimations;
    private final Animation deathAnimation_knife;
    //provides a variable to change the reloadspeed of the enemy
    public static float reloadspeed = new shooter.utils.Writer().getSettingValue("Enemy Reload Speed") / 10;
    //provides a variable to change the distance to the player for Line Of Sight to work
    public static float LOSdist = new shooter.utils.Writer().getSettingValue("Enemy Line Of Sight") * 10;
    //saves how the enemy died
    public int diedByType = 0;
    //saves whether the enemy is alive
    public int alive;
    //this constructor initializes the values
    public Enemy(int posX, int posY, int dir, int gunType, Handler handler, Level level, int alive) {
        super(posX, posY, 4, dir, handler, level);
        setActive();
        hitbox = new Rectangle(posX - 35, posY - 35, 70, 70);
        item = new Item(posX, posY, gunType, handler, level);
        item.setInActive();
        level.getEntityManager().addEntity(item);

        legAnimation = new Animation(Assets.enemy_legs, 50, 16, 16);

        walkAnimations = new Animation[]{
                null,
                new Animation(Assets.enemy_walk_knife, 100, 16, 16),
                null,
                null,
                new Animation(Assets.enemy_walk_silencer,100,14, 12),
                new Animation(Assets.enemy_walk_mp,100, 10, 12),
                null,
                new Animation(Assets.enemy_walk_shotgun,100, 12, 12),
                null
        };
        attackAnimations = new Animation[]{
                null,
                new Animation(Assets.enemy_attack_knife, 100, 18, 16, true),
                null,
                null,
                new Animation(Assets.enemy_attack_silencer, 353, 14, 12,true),
                new Animation(Assets.enemy_attack_rifle, 250,10, 12,true),
                null,
                new Animation(Assets.enemy_attack_shotgun,76, 12, 12,true),
                null
        };

        deathAnimation_knife = new Animation(Assets.enemy_die_knife, 100, 30 ,20);

        activeAnimation = walkAnimations[1];

        this.alive = alive;
        if(this.alive==0) die(3);

        legAnimation.stop();
    }
    //method to follow the trace found by pathfinding
    public void followTrace(ArrayList<Tile> trace){
        if(!trace.isEmpty() && trace.size() > 1){
            Tile currentTarget = trace.get(trace.size() - 2);   //Get next tile
            //if close to target tile get next target
            if(Math.abs(posX - currentTarget.getTposX()*30-15) < 20 && Math.abs(posY - currentTarget.getTposY()*30-15) < 20){
                trace.remove(currentTarget);
            }
            //look at next tile
            dir = (float) (180 + Math.toDegrees(Math.atan2(posY - currentTarget.getTposY()*30-15, posX - currentTarget.getTposX()*30-15)));
            //move to next tile
            posX += (float) (Math.cos(Math.toRadians(dir+180) + Math.PI) * speed);
            posY += (float) (Math.sin(Math.toRadians(dir+180) + Math.PI) * speed);
        }

    }
    //method to create the trace by using the tiles parent tiles
    public void drawtrace(Tile end, Tile start){
        if(trace.isEmpty()){
            trace.add(end);
        }
        if(end != start) {
            trace.add(end.getParent());
            //end.getParent().setColor(Color.orange); //Uncomment to mark path
            drawtrace(end.getParent(), start);
        }

    }
    //resets values of the tiles for next pathfinding
    public void resettiles(){
        for(int i = 0; i < 64 * level.getMapsize(); i++){
            for(int j = 0; j < 36 * level.getMapsize(); j++){
                //level.getTiles()[i][j].setColor(Color.green);
                //if(level.getTiles()[i][j].isSolid())
                //    level.getTiles()[i][j].setColor(Color.black);
                level.getTiles()[i][j].setParent(null);
                level.getTiles()[i][j].setVisited(false);
                level.getTiles()[i][j].setfCost(0);
                level.getTiles()[i][j].sethCost(0);
                level.getTiles()[i][j].setgCost(0);
            }
        }
    }
    //find path from one tile to another
    public void findpath(Tile start, Tile end){
        resettiles();
        //clear lists
        closedlist.clear();
        openlist.clear();

        //start.setColor(Color.red);
        //end.setColor(Color.green);

        //add start tile to open list
        openlist.add(start);
        start.setVisited(true);
        //repeat until the path is completed
        while(!openlist.isEmpty()){
            //current tile is cheapest tile of open list
            //GCost + HCost = FCost;
            current = openlist.get(0);
            for(Tile t : openlist){
                if(t.getfCost() < current.getfCost()){
                    current = t;
                }
            }
            //current.setColor(Color.pink);
            openlist.remove(current);
            closedlist.add(current);
            //if end is reached stop pathfinding
            if(current == end){
                trace.clear();
                drawtrace(current, start);
                break;
            }
            //add surrounding tiles of the current tile to neighbours list
            neighbors.clear();
            neighborsadd(current.getTposX() - 1, current.getTposY());
            neighborsadd(current.getTposX() + 1, current.getTposY());
            neighborsadd(current.getTposX(),        current.getTposY() -1);
            neighborsadd(current.getTposX(),        current.getTposY() +1);
            if(current.getTposX()-1 >= 0 && current.getTposY()-1 >= 0) {
                if (!level.getTiles()[current.getTposX()][current.getTposY() - 1].isHalfSolid() || !level.getTiles()[current.getTposX() - 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() - 1, current.getTposY() - 1);// LINKS OBEN
                }
            }
            if(current.getTposX()+1 <36 * level.getMapsize() && current.getTposY()-1 >= 0) {
                if (!level.getTiles()[current.getTposX()][current.getTposY() - 1].isHalfSolid() || !level.getTiles()[current.getTposX() + 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() + 1, current.getTposY() - 1);// RECHTS OBEN
                }
            }
            if(current.getTposX()-1 >= 0 && current.getTposY()+1 < 36 * level.getMapsize()) {
                if (!level.getTiles()[current.getTposX()][current.getTposY() + 1].isHalfSolid() || !level.getTiles()[current.getTposX() - 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() - 1, current.getTposY() + 1);// LINKS UNTEN
                }
            }
            if(current.getTposX()+1 < 36 * level.getMapsize() && current.getTposY()+1 < 36 * level.getMapsize()) {
                if (!level.getTiles()[current.getTposX()][current.getTposY() + 1].isHalfSolid() || !level.getTiles()[current.getTposX() + 1][current.getTposY()].isHalfSolid()) {
                    neighborsadd(current.getTposX() + 1, current.getTposY() + 1);// RECHTS UNTEN
                }
            }
            //calculate cost of every neighbor
            for (Tile temptile : neighbors) {
                if (!temptile.isClosed() && !temptile.isHalfSolid()){
                    if (current.getTposX() == temptile.getTposX() || current.getTposY() == temptile.getTposY()) {
                        addGCost = 10;
                    } else {
                        addGCost = 14;
                    }
                    tempGCost = (current.getgCost() + addGCost);
                    int mathfunction = 1;   //use faster but less accurate distance function

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
                            openlist.add(temptile);
                            temptile.setVisited(true);
                        }
                    }

                }
            }
        }
    }
    //check if neighbors are within the map boundaries
    public void neighborsadd(int x, int y){
        if(x < 64 * level.getMapsize() && x > -1 && y < 36 * level.getMapsize() && y > -1){
            neighbors.add(level.getTiles()[x][y]);
        }
    }
    //lets enemy die
    public void die(int type){
        alive = 0;
        diedByType = type;
        item.drop(this);
        this.hitbox = null;
        this.setInActive();
        dir += 180;
        if(type != 3) {
            activeAnimation.stop();
        }else{
            activeAnimation = deathAnimation_knife;
        }
        legAnimation.stop();
    }
    //checks it the enemy can see the player
    public boolean lineOfSight(){
        if(Math.abs(level.getEntityManager().getPlayer().getX()-posX)>LOSdist||Math.abs(level.getEntityManager().getPlayer().getY()-posY)>LOSdist)
            return false;
        ArrayList<Tile> tempTiles = new ArrayList<>();
        //world.setAllTiles(Color.green);
        Line2D line = new Line2D.Float(level.getEntityManager().getPlayer().getX(),level.getEntityManager().getPlayer().getY(),posX,posY);
        //System.out.println(Math.toDegrees(Math.PI + Math.atan2(world.getPlayer().getY() - posY, world.getPlayer().getX() - posX)));
        float tempDir = (float) (Math.PI + Math.atan2(level.getEntityManager().getPlayer().getY() - posY, level.getEntityManager().getPlayer().getX() - posX));
        float tempX = posX;
        float tempY = posY;
        while(Math.abs(level.getEntityManager().getPlayer().getX() - tempX) > 40 || Math.abs(level.getEntityManager().getPlayer().getY() - tempY) > 40) {
            tempX = tempX + (float) (Math.cos(tempDir + Math.PI) * 30);
            tempY = tempY + (float) (Math.sin(tempDir + Math.PI) * 30);
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    Tile tempT = level.getTiles((int) (x+tempX / 30 - 1), (int) (y+tempY / 30 - 1));
                    if(!tempTiles.contains(tempT))
                        tempTiles.add(tempT);
                }
            }

        }
        for(Tile t : tempTiles){
            if(line.intersects(t.getHitbox()) && t.isSolid())
                return false;
        }
        return true;
    }
    @Override
    public void tick() {
        if(pathfindingDelay<1) {
            findpath(level.getTiles(((int) ((posX) / 30)), ((int) ((posY) / 30))), level.getTiles(lastCoords[0], lastCoords[1]));
            playerSpotted = false;
        }

        if(item.getAmmo()==0&&this.active){
            for(int i=(int)reloadspeed;i>0;i--) {
                legAnimation.stop();
                item.reload();
            }
            return;
        }else if(playerSpotted&&!lineOfSight())
            legAnimation.start();
        if(active) {
            hitbox.setLocation(((int) (posX - 35)), ((int) (posY - 35)));
            //System.out.println(hitbox);
            if (lineOfSight()) {
                trace.clear();
                playerSpotted = true;
                pathfindingDelay = 30;
                dir = (float) (180 + Math.toDegrees(Math.atan2(posY - level.getEntityManager().getPlayer().getY(), posX - level.getEntityManager().getPlayer().getX() )));
                if (item != null&&item.getAmmo()!=0) {
                    item.activate(this);
                    activeAnimation = attackAnimations[item.getType()];
                }
            }else{
                followTrace(trace);
                if(playerSpotted) {
                    lastCoords = new int[]{(int) ((level.getEntityManager().getPlayer().getX()) / 30), (int) ((level.getEntityManager().getPlayer().getY()) / 30)};
                    pathfindingDelay--;
                }
            }
        }
        if(activeAnimation.lastFrame()&&Arrays.asList(attackAnimations).contains(activeAnimation)) {
            activeAnimation.tick();
            activeAnimation = walkAnimations[(item==null)?0:item.getType()];
        }
        activeAnimation.tick();
        legAnimation.tick();
    }
    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform reset = g2d.getTransform();
        if(active) {
            g2d.rotate(Math.toRadians(dir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(legAnimation.getCurrentFrame(), (int) (posX - legAnimation.getxOffset() * 3 - handler.getxOffset()), (int) (posY - legAnimation.getyOffset() * 3 - handler.getyOffset()), legAnimation.getWidth() * 3, legAnimation.getHeight() * 3, null);
            g2d.setTransform(reset);
            g2d.rotate(Math.toRadians(dir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(activeAnimation.getCurrentFrame(), (int) (posX - activeAnimation.getxOffset() * 3 - handler.getxOffset()), (int) (posY - activeAnimation.getyOffset() * 3 - handler.getyOffset()), activeAnimation.getWidth() * 3, activeAnimation.getHeight() * 3, null);
        }else if(diedByType == 3){
            g2d.rotate(Math.toRadians(dir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(activeAnimation.getCurrentFrame(), (int) (posX - activeAnimation.getxOffset() * 3 - handler.getxOffset()), (int) (posY - activeAnimation.getyOffset() * 3 - handler.getyOffset()), activeAnimation.getWidth() * 3, activeAnimation.getHeight() * 3, null);
        }else{
            g2d.rotate(Math.toRadians(dir), posX - handler.getxOffset(), posY - handler.getyOffset());
            g2d.drawImage(Assets.enemy_die_shotgun, (int) (posX - 20 * 3 - handler.getxOffset()), (int) (posY - 16 * 3 - handler.getyOffset()), 50 * 3, 32 * 3, null);

        }
        g2d.setTransform(reset);
    }

    //Getters
    public Rectangle getHitbox(){ return hitbox; }
    public Item getItem(){ return item; }
    public boolean getActive(){return super.active;}
    public String getData(){ return ((int)posX+","+(int)posY+","+(int)dir+","+item.getType()+","+item.getAmmo()+","+alive); }
}
