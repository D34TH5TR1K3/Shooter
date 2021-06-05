package shooter.world;

import shooter.Handler;
import shooter.entities.Enemy;
import shooter.entities.Entity;
import shooter.entities.EntityManager;
import shooter.entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    //this constructor initializes the values
    public Level(int levelNumber, BufferedImage[] map, Handler handler) {
        this.levelNumber = levelNumber;
        tiles = new Tile[64*mapsize][36*mapsize];
        this.handler = handler;
        entityManager = new EntityManager();
        this.map = map;
        fillTiles();
        fillHalfSolidTiles();
    }

    //ticks the entityManager
    public void tick() {
        entityManager.tick();
    }
    //renders the map and the entityManager
    public void render(Graphics g) {
        g.drawImage(map[0],(int)(0-handler.getGameCamera().getxOffset()),(int)(0-handler.getGameCamera().getyOffset()),null);
        //renderTiles(g);
        entityManager.render(g);
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
    public boolean checkEnemyCollision(Rectangle rect) {
        for (Entity e : getEntityManager().getEnemies()) {
            //System.out.println("start");
            //System.out.println(rect);
            //System.out.println(((Enemy) e).getHitbox());
            if (((Enemy) e).getHitbox() != null && ((Enemy) e).getHitbox().intersects(rect)) {
                ((Enemy) e).die();
                return true;
            }
        }
        return false;
    }
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
