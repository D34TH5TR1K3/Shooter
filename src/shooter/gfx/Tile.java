package shooter.gfx;

public class Tile {

    private boolean isSolid = false;
    private int width = 30, height = 30;
    private int TposX, TposY; // T... means tile coordinate; T... * tilesize (30) = normal coordinate

    public Tile(int x, int y, boolean isSolid){
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

}
