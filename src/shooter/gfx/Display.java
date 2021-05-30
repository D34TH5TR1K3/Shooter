package shooter.gfx;

import shooter.utils.Writer;

import java.awt.*;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public class Display {

    private JFrame frame;           //hier wird der JFrame gespeichert, der ein Fenster erzeugt
    private Canvas canvas;          //hier wird der Canvas gespeichert, auf dem das Graphics-Objekt Dinge projezieren kann
    private final Writer writer;    //hier wird der Writer gespeichert
    public static Font fraktur;     //hier wird der Font gespeichert

    //this constructor initializes the values
    public Display(Writer writer) { //im Konstruktor wird das Display erzeugt und der Font initialisiert
        this.writer = writer;
        createDisplay();
        try{
            // load a custom font in your project folder
            fraktur = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/fraktur.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/fraktur.ttf")));
        }
        catch(IOException | FontFormatException e){
            System.exit(2);
        }
    }

    public void createDisplay() {   //hier wird das Fenster und der Canvas erzeugt und zusammengeführt

        //set window scaling independently from windows scaling
        //100% for 1080p; 150% for 1440p;
        //float scale = writer.GetSettingValue("Scale");
        //System.out.println(writer.getScale());
        if(writer.getScale() != 1){
            System.setProperty("sun.java2d.uiScale", String.valueOf(writer.getScale()));
        }
        //System.setProperty("sun.java2d.uiScale", "1.333333333333333");
        //writer.changeSetting("Scale", 1.2f);
        //writer.writeToFile();
        //writer.readFromFile(true);

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
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    //Getters
    public JFrame getFrame() { return frame; }
    public Canvas getCanvas() { return canvas; }
}
