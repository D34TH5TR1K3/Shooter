package shooter.gfx;

import shooter.entities.*;
import shooter.Handler;

import java.awt.*;
import java.util.ArrayList;

public class World {
    //saves all of the Tiles and the size of the map
    private final Tile[][] tiles;
    private final int mapsize = 2;
    //player saves the player
    private Player player;
    //handler distributes variables
    private final Handler handler;
    //entityManager organizes the Entities
    private final EntityManager entityManager;

    //this constructor initializes the values
    public World(Handler handler) {
        tiles = new Tile[64 * mapsize][36 * mapsize];
        this.handler = handler;
        entityManager = new EntityManager(this);
        fillTiles();
        fillHalfSolidTiles();
    }

    //ticks the entityManager
    public void tick(){
        entityManager.tick();
    }
    //renders the map and the entityManager
    public void render(Graphics g){
        g.drawImage(Assets.map_1, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
        entityManager.render(g);
    }

    //method to fill the world with default information
    public void fillWorld(){
        this.player = new Player(100, 100, 45, handler, this);
        entityManager.addEnemy(new Enemy(700,700,0,2, handler, this));
        entityManager.addEnemy(new Enemy(1200,1800,180,2, handler, this));
    }
    //method to fill the world with information from the GameSave File
    public void fillWorld(Player player, ArrayList<Entity> enemies){
        this.player = player;
        for(Entity e:enemies){
            entityManager.addEnemy(e);
        }
    }
    //method to check if the Rectangle collides with a Tile
    public boolean collisionCheck(Rectangle rect){
        for(int y = (int) (0 + rect.getY()/30); y < 4 + rect.getY()/30; y++)
            for(int x = (int) (0 + rect.getX()/30); x < 4 + rect.getX()/30; x++)
                if(x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length){
                    Tile temptile = tiles[x][y];
                    if(temptile.isSolid() && temptile.getHitbox().intersects(rect))
                        return true;
                }
        return false;
    }
    //method to check if the Rectangle collides with an Enemy
    public boolean checkEnemyCollision(Rectangle rect) {
        for (Entity e : entityManager.getEnemies()) {
            System.out.println("start");
            System.out.println(rect);
            System.out.println(((Enemy) e).getHitbox());
            if (((Enemy) e).getHitbox() != null && ((Enemy) e).getHitbox().intersects(rect)) {
                ((Enemy) e).die();
                return true;
            }
        }
        return false;
    }
    //method to render all Tiles
    public void renderTiles(Graphics g){
        int tilesize = 30;
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                g.setColor(tiles[x][y].getColor());
                g.fillRect(((int) (tilesize * x - handler.getxOffset())), ((int) (tilesize * y - handler.getyOffset())), tilesize *x+ tilesize, tilesize *y+ tilesize);
            }
        }
    }
    //method to make according Tiles half solid
    public void fillHalfSolidTiles(){
        for(int x = 0; x < 64 * mapsize; x++) {
            for (int y = 0; y < 36 * mapsize; y++) {
                if(tiles[x][y].isSolid()){
                    for(int X = -1; X < 2; X++){
                        for(int Y = -1; Y < 2; Y++){
                            if(x+X >= 0 && x+X < 64 * mapsize && y+Y >= 0 && y+Y < 36 * mapsize)
                            tiles[x+X][y+Y].setHalfSolid(true);
                        }
                    }
                }
            }
        }
    }
    //method to print the Tiles to console
    public void printTiles(Graphics g){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                System.out.print("");
            }
        }
    }
    //method to create the Tiles and set whether they are solid
    public void fillTiles() {
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                Color mycolor = new Color(Assets.map_1layout.getRGB(x, y));
                boolean solid = false;
                int red = mycolor.getRed();
                int green = mycolor.getGreen();
                int blue = mycolor.getBlue();
                if(red < 200 && green < 200 && blue < 200)
                    solid = true;
                tiles[x][y] = new Tile(x, y, solid);
            }
        }
    }

    //getters and setters
    public Tile getTiles(int x, int y) {
        if(x >= 0 && x < 64 * mapsize && y >= 0 && y < 36 * mapsize) {
            //System.out.println(x + "   " + y);
            return tiles[x][y];
        }else
            return tiles[0][0];
    }
    public Tile[][] getTiles() {
        return tiles;
    }
    public void setAllTiles(Color color){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                tiles[x][y].setColor(color);
            }
        }
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public Player getPlayer() {
        return player;
    }
    public int getMapsize(){
        return mapsize;
    }
}
