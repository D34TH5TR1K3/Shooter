package shooter.gfx;

import java.awt.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Display {

    private JFrame frame;
    private Canvas canvas;

    public Display() { createDisplay(); }

    public void createDisplay() {
        //set window scaling independently from windows scaling
        //100% for 1080p; 150% for 1440p;
        //System.setProperty("sun.java2d.uiScale", "1.0");
        /*GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        System.out.println("Screen Width: " + gd.getDisplayMode().getWidth());
        System.out.println("Screen Height: " + gd.getDisplayMode().getHeight());
        if(gd.getDisplayMode().getWidth() == 2560 && gd.getDisplayMode().getHeight() == 1440){
            System.out.print("hello");
        }
        */
        System.setProperty("sun.java2d.uiScale", "1.5");


        frame = new JFrame("Shooter");  //create new JFrame with title: title
        //TODO: Change name later
        int width = 1920, height = 1080;
        frame.setSize(width, height);   //set size to width and height
        frame.setMaximumSize(new Dimension(width, height));
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // on program exit
        frame.setResizable(false);  //not resizable
        frame.setUndecorated(true); //borderless
        frame.setLocationRelativeTo(null); //center window
        frame.setVisible(true); //set display visible

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));

        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame() { return frame; }
    public Canvas getCanvas() { return canvas; }
}
