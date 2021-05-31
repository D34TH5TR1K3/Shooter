package shooter.gfx;

import java.awt.*;

public class Tile {
    //logical variables belonging to pathfinding
    private Tile parent;
    private int hCost = 0, gCost = 0, fCost =0;
    private boolean visited = false;
    private boolean closed = false;
    //saves the solidity of the Tile
    private final boolean isSolid;
    private boolean isHalfSolid = false;
    //saves the size and the position of the Tile
    private final short TposX, TposY;
    //saves the hitbox of the Tile
    private final Rectangle hitbox;
    //saves the color of the Tile
    private Color color = Color.green;

    //this constructor initializes the values
    public Tile(short x, short y, boolean isSolid){
        this.TposX = x;
        this.TposY = y;
        this.isSolid = isSolid;
        hitbox = new Rectangle(TposX * 30, TposY * 30,30,30);
    }

    //getters and setters
    public boolean isSolid() { return isSolid; }
    public boolean isHalfSolid() { return isHalfSolid; }
    public void setHalfSolid(boolean halfSolid) { isHalfSolid = halfSolid; }
    public short getTposX() { return TposX; }
    public short getTposY() { return TposY; }
    public Rectangle getHitbox() { return hitbox; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public int getfCost() { return hCost + gCost; }
    public int gethCost() { return hCost; }
    public int getgCost() { return gCost; }
    public void setVisited(boolean visited) { this.visited = visited; }
    public void setParent(Tile parent) { this.parent = parent; }
    public void sethCost(int hCost) { this.hCost = hCost; }
    public void setgCost(int gCost) { this.gCost = gCost; }
    public boolean isVisited() { return visited; }
    public boolean isClosed() { return closed; }
    public Tile getParent() { return parent; }
    public void setfCost(int i) { fCost = i; }
}
