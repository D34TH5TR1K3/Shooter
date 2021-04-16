package shooter.gfx;

import java.awt.*;

public class Tile {

    private boolean isSolid = false;
    private int width = 30, height = 30;
    private int TposX, TposY; // T... means tile coordinate; T... * tilesize (30) = normal coordinate
    private Color tileColor;

    public Tile(int x, int y, boolean isSolid, Color tileColor){
        this.tileColor = tileColor;
        this.TposX = x;
        this.TposY = y;
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getTileColor() {
        return tileColor;
    }
}
