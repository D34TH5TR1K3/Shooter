package shooter.gfx;

import shooter.entities.Entity;

public class GameCamera {
    private float xOffset, yOffset;                     //hier wird gespeichert, wie weit die Welt verschoben ist

    public GameCamera(float xOffset, float yOffset){    //im Konstruktor werden die Offsets initialisiert
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnEntity(Entity e){               //hier wird die Welt soweit verschoben, dass es aussieht als würde sich der Viewport bewegen und nicht die Welt um den Spieler herum
        xOffset = e.getX() - 960 + Entity.CREATURESIZE / 2;
        yOffset = e.getY() - 540 + Entity.CREATURESIZE / 2;
    }

    public void move(float xAmt, float yAmt){           //mit move könnte man die Kamera bewegen, ohne dass sich der Spieler in der Mitte befindet
        xOffset += xAmt;
        yOffset += yAmt;
    }

    //Getters
    public float getxOffset(){ return xOffset; }
    public float getyOffset(){ return yOffset; }
}
