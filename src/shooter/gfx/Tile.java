package shooter.gfx;

import java.awt.*;

public class Tile {
    private Tile parent;
    private int hCost = 0;
    private int gCost = 0;
    private int fCost = hCost + gCost;
    private boolean visited = false;
    private boolean closed = false;

    private boolean isSolid = false;
    private boolean isHalfSolid = false;
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

    public boolean isHalfSolid() {
        return isHalfSolid;
    }

    public void setHalfSolid(boolean halfSolid) {
        isHalfSolid = halfSolid;
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

    public int getfCost() {
        return hCost + gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public int getgCost() {
        return gCost;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setParent(Tile parent) {
        this.parent = parent;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isClosed() {
        return closed;
    }

    public Tile getParent() {
        return parent;
    }

    public void setfCost(int i) {
        fCost = i;
    }
}
