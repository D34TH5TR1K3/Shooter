package shooter.world;

import shooter.Handler;
import shooter.entities.Enemy;
import shooter.entities.Entity;
import shooter.entities.EntityManager;
import shooter.entities.Player;
import shooter.gfx.Assets;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static shooter.entities.Enemy.LOSdist;
import static shooter.gfx.Display.fraktur;

public class Level {
    //indicates the number of the level
    private final int levelNumber;
    //saves all of the Tiles and the size of the map
    private final Tile[][] tiles;
    private final byte mapsize = 2;
    //handler distributes variables
    private final Handler handler;
    //entityManager organizes the Entities
    private EntityManager entityManager;
    //saves the map and its layout
    private final BufferedImage[] map;
    //coordinates of the extraction point
    private final int posX, posY;

    //this constructor initializes the values
    public Level(int levelNumber, BufferedImage[] map, Handler handler,int posX, int posY) {
        this.levelNumber = levelNumber;
        tiles = new Tile[64*mapsize][36*mapsize];
        this.handler = handler;
        entityManager = new EntityManager();
        this.map = map;
        this.posX = posX;
        this.posY = posY;
        fillTiles();
        fillHalfSolidTiles();
    }
    //ticks the entityManager and checks the condition of the level
    public void tick() {
        entityManager.tick();
        checkLevelCondition();
        if((entityManager.getPlayer().isMoveA() || entityManager.getPlayer().isMoveD() || entityManager.getPlayer().isMoveS() || entityManager.getPlayer().isMoveW())
                && Math.abs(entityManager.getPlayer().getX()-posX)>100&& Math.abs(entityManager.getPlayer().getY()-posY)>100){
            entityManager.getPlayer().resetMove();
            handler.getWorld().nextLevel();
        }else{

        }
    }
    //renders the map and the entityManager
    public void render(Graphics g) {
        g.drawImage(map[0],(int)(0-handler.getGameCamera().getxOffset()),(int)(0-handler.getGameCamera().getyOffset()),null);
        //renderTiles(g);
        entityManager.render(g);
        g.drawImage(map[2],(int)(0-handler.getGameCamera().getxOffset()),(int)(0-handler.getGameCamera().getyOffset()),null);
        g.drawImage(Assets.overlay, 1920 - 239, 1080 - 92,null);
        if(entityManager.getPlayer().getItem() != null) {
            int x = 1820;
            int y = 995;
            int x1 = 1780;
            int y1 = 1010;
            switch (entityManager.getPlayer().getItem().getType()) {
                case 1:
                    g.drawImage(Assets.item_knife, x, y, 80, 80, null);
                    break;
                case 2:
                    g.drawImage(Assets.item_machete, x, y, 70, 70, null);
                    break;
                case 3:
                    g.drawImage(Assets.item_handgun, x, y, 100, 100, null);
                    g.drawImage(Assets.bullet_img, x1, y1, null);
                    break;
                case 4:
                    g.drawImage(Assets.item_silencer, x, y, 100, 100, null);
                    g.drawImage(Assets.bullet_img, x1, y1, null);
                    break;
                case 5:
                    g.drawImage(Assets.item_uzi_full, x, y, 100, 100, null);
                    g.drawImage(Assets.bullet_img, x1, y1, null);
                    break;
                case 6:
                    g.drawImage(Assets.item_rifle_full, x, y, 80, 80, null);
                    g.drawImage(Assets.bullet_img, x1, y1, null);
                    break;
                case 7:
                    g.drawImage(Assets.item_shotgun, x, y, 80, 80, null);
                    g.drawImage(Assets.buckshot_img, x1, y1, null);
                    break;
                case 8:
                    g.drawImage(Assets.item_rpg_full, x, y, 120, 120, null);
                    break;
                default:
                    break;
            }
            float percentage = (float)entityManager.getPlayer().getItem().getAmmo() / (float)entityManager.getPlayer().getItem().getAmmoMax() * 69;
            g.setColor(new Color(100, 67, 28));
            g.fillRect(x1, y1, 46, (int) (69 - percentage));
            g.setColor(Color.gray);
            g.setFont(fraktur);
            g.drawString(Integer.toString(entityManager.getPlayer().getItem().getAmmo()),1750,1070);
        }else{
            g.setColor(Color.gray);
            g.setFont(fraktur);
            g.drawString("unarmed",1750,1070);
        }
    }
    //makes player animation to walk to another level
    public void vanish(){

        entityManager.getPlayer().setMoveTrue("A");

    }
    //method to fill the world with information from a LevelFile
    public void fillWorld(Player player, ArrayList<Entity> enemies) {
        entityManager.setPlayer(player);
        for(Entity e:enemies) {
            entityManager.addEntity(e);
        }
    }
    //method to create the Tiles and set whether they are solid
    public void fillTiles() {
        for(short x=0;x<64*mapsize;x++)
            for(short y=0;y<36*mapsize;y++){
                Color myColor = new Color(map[1].getRGB(x,y));
                int red = myColor.getRed();
                int green = myColor.getGreen();
                int blue = myColor.getBlue();
                tiles[x][y] = new Tile(x,y,red < 200 && green < 200 && blue < 200);
            }
    }
    //method to make Tiles half solid
    public void fillHalfSolidTiles() {
        for(int x = 0; x < 64 * mapsize; x++)
            for (int y = 0; y < 36 * mapsize; y++)
                if(tiles[x][y].isSolid())
                    for(int X = -1; X < 2; X++)
                        for(int Y = -1; Y < 2; Y++)
                            if(x+X >= 0 && x+X < 64 * mapsize && y+Y >= 0 && y+Y < 36 * mapsize)
                                tiles[x+X][y+Y].setHalfSolid(true);
    }
    //method to check if the Rectangle collides with a Tile
    public boolean collisionCheck(Rectangle rect) {
        for(int y = (int) (0 + rect.getY()/30); y < 4 + rect.getY()/30; y++)
            for(int x = (int) (0 + rect.getX()/30); x < 4 + rect.getX()/30; x++)
                if(x >= 0 && x < getTiles().length && y >= 0 && y < getTiles()[0].length){
                    Tile temptile = getTiles(x,y);
                    if(temptile.isSolid() && temptile.getHitbox().intersects(rect))
                        return true;
                }
        return false;
    }
    //method to check if the Rectangle collides with an Enemy
    public boolean checkEnemyCollision(Rectangle rect, int type) {
        for (Entity e : getEntityManager().getEnemies()) {
            //System.out.println("start");
            //System.out.println(rect);
            //System.out.println(((Enemy) e).getHitbox());
            if (((Enemy) e).getHitbox() != null && ((Enemy) e).getHitbox().intersects(rect)) {
                ((Enemy) e).die(type);
                return true;
            }
        }
        return false;
    }
    public boolean lineCollision(int x1, int y1, int x2, int y2){
        ArrayList<Tile> tempTiles = new ArrayList<>();
        //world.setAllTiles(Color.green);
        Line2D line = new Line2D.Float(x1,y1,x2,y2);
        //System.out.println(Math.toDegrees(Math.PI + Math.atan2(world.getPlayer().getY() - posY, world.getPlayer().getX() - posX)));
        float tempDir = (float) (Math.PI + Math.atan2(y1 - y2, x1 - x2));
        float tempX = x2;
        float tempY = y2;
        while(Math.abs(x1 - tempX) > 40 || Math.abs(y1 - tempY) > 40) {
            tempX = tempX + (float) (Math.cos(tempDir + Math.PI) * 30);
            tempY = tempY + (float) (Math.sin(tempDir + Math.PI) * 30);
            for(int x = 0; x < 3; x++){
                for(int y = 0; y < 3; y++){
                    Tile tempT = getTiles((int) (x+tempX / 30 - 1), (int) (y+tempY / 30 - 1));
                    if(!tempTiles.contains(tempT))
                        tempTiles.add(tempT);
                }
            }

        }
        for(Tile t : tempTiles){
            if(line.intersects(t.getHitbox()) && t.isSolid())
                return true;
        }
        return false;
    }
    //method to check and load next level
    public void checkLevelCondition(){
        boolean nextLevel = true;
    for (Entity e : getEntityManager().getEnemies()){
        if(((Enemy) e).getActive()) {
            nextLevel = false;
            break;
        }
    }
    if(nextLevel && Math.abs(entityManager.getPlayer().getX()-posX)<600&& Math.abs(entityManager.getPlayer().getY()-posY)<600)
    if(nextLevel)
        vanish();
    }
    //TODO: change distance to extractionpoint
    //method to check if the Rectangle collides with the Player
    public void checkPlayerCollision(Rectangle rect) {
        Player p = getEntityManager().getPlayer();
        if(p.getHitbox().intersects(rect))
            p.die();
    }
    //Getters
    public Tile getTiles(int x, int y) {
        if(x >= 0 && x < 64 * mapsize && y >= 0 && y < 36 * mapsize)
            return tiles[x][y];
        return tiles[0][0];
    }
    //debugging
    public void renderTiles(Graphics g){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                if(tiles[x][y].isHalfSolid()){
                    g.setColor(Color.red);
                }else if(tiles[x][y].isSolid()){
                    g.setColor(Color.green);
                }
                g.setColor(tiles[x][y].getColor());
                g.fillRect(((int) (30 * x - handler.getxOffset())), ((int) (30 * y - handler.getyOffset())), 30*x+30, 30*y+30);
            }
        }
    }
    public Tile[][] getTiles() { return tiles; }
    public EntityManager getEntityManager() { return entityManager; }
    public void resetEntityManager() { this.entityManager = new EntityManager(); }
    public int getMapsize() { return mapsize; }
    public int getLevelNumber() { return levelNumber; }
}
