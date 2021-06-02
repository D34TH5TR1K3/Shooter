package shooter.gfx;

import shooter.input.KeyManager;
import shooter.input.MouseManager;
import shooter.utils.Writer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Display {
    //saves the JFrame and Canvas for further distribution
    private JFrame frame;
    private Canvas canvas;
    //saves a writer to read from Files
    private final Writer writer;
    //saves a static font to be used with everything
    public static Font fraktur;

    //this constructor initializes the values
    public Display(Writer writer,KeyManager keyManager,MouseManager mouseManager) {
        this.writer = writer;
        createDisplay();
        addManagers(keyManager, mouseManager);
        try{
            fraktur = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/fraktur.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/fraktur.ttf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(2);
        }
    }

    //method to create a Display
    public void createDisplay() {
        if(writer.getScale() != 1)
            System.setProperty("sun.java2d.uiScale", String.valueOf(writer.getScale()));
        frame = new JFrame("Shooter");
        int width = 1920, height = 1080;
        frame.setSize(width, height);
        frame.setMaximumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }
    //method to add the Managers to the Display
    public void addManagers(KeyManager keyManager, MouseManager mouseManager) {
        frame.addKeyListener(keyManager);
        frame.addMouseListener(mouseManager);
        frame.addMouseMotionListener(mouseManager);
        canvas.addMouseListener(mouseManager);
        canvas.addMouseMotionListener(mouseManager);
    }

    //getters
    public JFrame getFrame() { return frame; }
    public Canvas getCanvas() { return canvas; }
}
