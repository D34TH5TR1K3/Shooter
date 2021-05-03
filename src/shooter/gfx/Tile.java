package shooter.gfx;

import java.awt.*;

public class Tile {

    private boolean isSolid = false;
    private int width = 30, height = 30;
    private int TposX, TposY; // T... means tile coordinate; T... * tilesize (30) = normal coordinate
    private Rectangle hitbox;
    private Color color = Color.green;

    public Tile(int x, int y, boolean isSolid){
        this.TposX = x;
        this.TposY = y;
        this.isSolid = isSolid;
        hitbox = new Rectangle(TposX * 30, TposY * 30, width, height);
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

    public int getTposX() {
        return TposX;
    }

    public int getTposY() {
        return TposY;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
