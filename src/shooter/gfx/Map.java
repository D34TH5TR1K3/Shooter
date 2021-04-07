package shooter.gfx;

import java.awt.*;
import shooter.gfx.Tile;

public class Map {

    private Tile[][] tiles;
    private int mapsize = 2; //TODO set size of map

    public Map() {
        tiles = new Tile[64 * mapsize][36 * mapsize];
    }

    public void fillTiles(){
        for(int x = 0; x < 64 * mapsize; x++){
            for(int y = 0; y < 36 * mapsize; y++){

                Color mycolor = new Color(Assets.levelMap.getRGB(x * 30, y * 30));
                boolean solid = false;
                //System.out.println(mycolor);
                int red = mycolor.getRed();
                int green = mycolor.getGreen();
                int blue = mycolor.getBlue();
                if(red < 200 && green < 200 && blue < 200){
                    solid = true;
                }


                tiles[x][y] = new Tile(x, y, solid);
            }
        }
    }

}
