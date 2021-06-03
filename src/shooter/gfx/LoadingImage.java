package shooter.gfx;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class LoadingImage {
    //loadingImage saves the Image to be rendered while the game is starting
    private static final BufferedImage loadingImage = ImageLoader.loadImage("/textures/loadingImage.png");
    private static final BufferedImage deathScreenImage = loadingImage;
    private static Display display;
    private static Graphics g;

    //render renders the loadingImage to the Screen
    public static void render() {
        BufferStrategy bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.drawImage(loadingImage,0,0,null);
        bs.show();
        g.dispose();
    }

    //renders the Image 3 Times to bypass the BufferStrategy
    public static void initialRender(Display display) {
        LoadingImage.display = display;
        for(int i = 0; i < 3 ; i++)
            render();
    }
    //renders the DeathScreen
    public static void renderDeathScreen() {
        for(int i = 0; i < 3 ; i++)
            g.drawImage(deathScreenImage,0,0,null);
    }
}
