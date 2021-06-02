package shooter.gfx;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class LoadingImage {
    //loadingImage saves the Image to be rendered while the game is starting
    private static final BufferedImage loadingImage = ImageLoader.loadImage("/textures/loadingImage.png");

    //render renders the loadingImage to the Screen
    public static void render(Display display) {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(loadingImage,0,0,null);
        bs.show();
        g.dispose();
    }
}
