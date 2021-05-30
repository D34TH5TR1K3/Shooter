package shooter.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    //saves the SpriteSheet
    private final BufferedImage sheet;

    //this constructor initializes the values
    public SpriteSheet(BufferedImage sheet) {               //im Konstruktor wird das SpriteSheet initialisiert
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int w, int h) { //crop gibt ein aus dem SpriteSheet herausgeschnittenes Bild zurück
        return sheet.getSubimage(x,y,w,h);
    }
}
