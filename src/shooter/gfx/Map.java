package shooter.gfx;

import java.awt.*;

import shooter.entities.EntityManager;
import shooter.entities.Player;
import shooter.gfx.Tile;

public class Map {

    private Tile[][] tiles;
    private int mapsize = 2; //TODO set size of map
    private int tilesize = 30;
    private Player player;

    private Color tileColor;

    private EntityManager entityManager;
    public Map() {
        tiles = new Tile[64 * mapsize][36 * mapsize];
        entityManager = new EntityManager();
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

                Color mycolor = new Color(Assets.map_temp.getRGB(x, y));
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
        System.out.println("hello there");
        entityManager.addEntity(new Player(100, 100));
        //TODO find spot to make new player
    }
}
