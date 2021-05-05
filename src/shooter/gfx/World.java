package shooter.gfx;

import shooter.entities.*;
import shooter.Handler;

import java.awt.*;

public class World {

    private Tile[][] tiles;
    private int mapsize = 2; //TODO set size of map
    private int tilesize = 30;
    private Player player;

    private Color tileColor;

    private Handler handler;
    private EntityManager entityManager;    //holds all entities of the world and ticks and renders them

    public World(Handler handler) {
        tiles = new Tile[64 * mapsize][36 * mapsize];
        this.handler = handler;
        entityManager = new EntityManager(this);
        fillTiles();
        fillHalfSolidTiles();
        this.player = new Player(100, 100, 45, handler, this);
        //entityManager.addEntity(new Enemy(200,200,135,2, handler, this));
        entityManager.addEnemy(new Enemy(700,700,0,2, handler, this));
        entityManager.addEnemy(new Enemy(1200,1800,180,2, handler, this));
    }

    public boolean collisionCheck(Rectangle rect){
        //System.out.println(rect.getBounds());
        for(int y = (int) (0 + rect.getY()/30); y < 4 + rect.getY()/30; y++){
            for(int x = (int) (0 + rect.getX()/30); x < 4 + rect.getX()/30; x++){
                if(x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length){
                    Tile temptile = tiles[x][y];
                    if(temptile.isSolid() && temptile.getHitbox().intersects(rect)){
                        //System.out.println(temptile.getTposX() +"  "+ temptile.getTposY());
                        return true;
                    }
                    //System.out.println(temptile.getTposX()*30 + "   " + temptile.getTposY()*30+"   ");
                }
            }
            //System.out.println();
        }
        return false;
    }
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

    public void tick(){
        entityManager.tick();
    }

    public void render(Graphics g){
        g.drawImage(Assets.map_1, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
        //System.out.println((int)(0 - handler.getGameCamera().getxOffset())+"   "+(int)(0 - handler.getGameCamera().getyOffset()));

        //renderTiles(g);
        entityManager.render(g);
        //g.drawImage(Assets.Map1_walls, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
    }

    public void renderTiles(Graphics g){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                g.setColor(tiles[x][y].getColor());
//                if(tiles[x][y].isHalfSolid()){
//                    g.setColor(Color.red);
//                }else{
//                    g.setColor(Color.green);
//                }
                
                //g.setColor(tiles[x][y].getTileColor());
                g.fillRect(((int) (tilesize * x - handler.getxOffset())), ((int) (tilesize * y - handler.getyOffset())), tilesize*x+tilesize, tilesize*y+tilesize);
            }
        }
    }

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

    public void printTiles(Graphics g){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                System.out.print("");
            }
        }
    }

    public void fillTiles() {   //reads map layout and detects sold walls. Fills tiles array and sets tiles solid if necessary
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){

                Color mycolor = new Color(Assets.map_1layout.getRGB(x, y));
                boolean solid = false;
                //System.out.println(mycolor);
                int red = mycolor.getRed();
                int green = mycolor.getGreen();
                int blue = mycolor.getBlue();
                if(red < 200 && green < 200 && blue < 200){
                    solid = true;
                    tileColor = Color.cyan;
                }else{
                    tileColor = Color.green;
                }
                tiles[x][y] = new Tile(x, y, solid);
            }
        }
        //TODO find spot to make new player
    }

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
