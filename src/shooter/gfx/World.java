package shooter.gfx;

import shooter.entities.Entity;
import shooter.entities.EntityManager;
import shooter.entities.Player;
import shooter.Handler;

import java.awt.*;

public class World {

    private Tile[][] tiles;
    private int mapsize = 2; //TODO set size of map
    private int tilesize = 30;
    private Player player;

    private Color tileColor;

    private Handler handler;
    private EntityManager entityManager;

    public World(Handler handler) {
        tiles = new Tile[64 * mapsize][36 * mapsize];
        this.handler = handler;
        entityManager = new EntityManager();
        fillTiles();
    }

    public void tick(){
        entityManager.tick();
    }

    public void render(Graphics g){
        g.drawImage(Assets.map_1, (int)(0 - handler.getGameCamera().getxOffset()), (int)(0 - handler.getGameCamera().getyOffset()), null);
        //System.out.println((int)(0 - handler.getGameCamera().getxOffset())+"   "+(int)(0 - handler.getGameCamera().getyOffset()));
        //renderTiles(g);
        entityManager.render(g);
    }

    public void renderTiles(Graphics g){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){
                if(tiles[x][y].isSolid()){
                    g.setColor(Color.red);
                }else{
                    g.setColor(Color.cyan);
                }
                //g.setColor(tiles[x][y].getTileColor());
                g.fillRect(tilesize*x, tilesize*y, tilesize*x+tilesize, tilesize*y+tilesize);
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

    public void fillTiles() {
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

        entityManager.addEntity(new Player(100, 100, 45, Entity.CREATURESIZE, Entity.CREATURESIZE, handler, this));
        //TODO find spot to make new player
    }

    public Tile getTiles(int x, int y) {
        return tiles[x][y];
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
