package shooter.gfx;

import shooter.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static shooter.gfx.Display.fraktur;

public class Menu {

    private ArrayList<Point> points;            //eine ArrayList mit allen Punkten
    private ArrayList<Button> buttons;          //eine ArrayList mit allen Knöpfen
    private ArrayList<Slider> sliders;          //eine ArrayList mit allen Sliders
    private ArrayList<Rectangle> renderRects;   //eine ArrayList mit allen Rechtecken, die gerendert werden müssen
    Handler handler;                            //hier wird der Handler zwischengespeichert
    BufferedImage menu, menu_layout;            //hier wird die Struktur des Menüs gespeichert, die dann später vom Programm ausgelesen wird
    String[] actionButtons, actionSliders;      //hier werden die Funktionen für die Knöpfe gespeichert
    private final boolean debug = false;        //ob man sich im Debug-Modus befindet oder nicht

    //im Konstruktor wird das Menü und mit ihm seine Variablen initialisiert
    public Menu(String[] actionButtons, String[] actionSliders, Handler handler, BufferedImage menu, BufferedImage menu_layout){
        this.actionButtons = actionButtons;
        this.actionSliders = actionSliders;
        this.menu_layout = menu_layout;
        this.menu = menu;
        this.handler = handler;
        points = new ArrayList<Point>();
        buttons = new ArrayList<Button>();
        sliders = new ArrayList<Slider>();
        renderRects = new ArrayList<Rectangle>();
        readMenu();
    }

    //Getters
    public float getSliderValue(String func){
        for(Slider slider : sliders){
            if(slider.getFunc() == func){
                return slider.getValue();
            }
        }
        return -1;
    }

    public float getButtonValue(String func){
        for(Button button : buttons){
            if(button.getFunc() == func){
                return button.getValue();
            }
        }
        return -1;
    }

    public void readMenu(){                 //hier wird das Menü eingelesen und die Rechtecke platziert
        int indexButton = 0;
        int indexSlider = 0;
        //TODO: read slider min and max etc from txt file since we know in which order they are read in

        for(int y = 0; y < 108; y++){ // iterates through every pixel
            for(int x = 0; x < 192; x++){
                Color color = new Color(menu_layout.getRGB(x, y)); // gets color of every pixel
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int colorint = color.getRGB();
                if(red > 1 || green > 1 || blue > 1){ // if color != black -> create new point with color and coordinates
                    Point point = new Point(red, green, blue, x, y, colorint);
                    points.add(point);
                    //System.out.println(red+"\t"+green+"\t"+blue+"\t"+x+"\t"+y+"\t"+colorint);
                }
            }
        }
        while(points.size() > 0){   //Creates rectangles from points of same color
            Point point1 = points.get(0);
            points.remove(point1);
            for(Point point2 : points){
                if(point1.getRed() == point2.getRed() &&
                   point1.getGreen() == point2.getGreen() &&
                   point1.getBlue() == point2.getBlue()){
                    if(Math.abs(point1.getY() - point2.getY()) > 5){
                        Button button1 = new Button(indexButton, point1.getX(), point1.getY(), point2.getX(), point2.getY(), point2.getColor());
                        button1.setFunc(actionButtons[indexButton]);
                        button1.setValue(handler.getGame().getWriter().GetSettingValue(actionButtons[indexButton]));
                        indexButton++;
                        buttons.add(button1);
                    }else{
                        Slider slider1 = new Slider(indexSlider, point1.getX(), point1.getY(), point2.getX(), point2.getY(), point2.getColor());
                        slider1.setFunc(actionSliders[indexSlider]);
                        //  get current slider value from settings.txt
                        float deftemp = handler.getGame().getWriter().GetSettingValue(actionSliders[indexSlider]);

                        indexSlider++;
                        slider1.minMaxDef(0f, 100f, deftemp);
                        sliders.add(slider1);
                    }

                    points.remove(point2);
                    //System.out.println(points.size());
                    break;
                }
            }
        }

        //System.out.println(buttons.size());
    }

    public boolean funcActive(String func){ //hier wird geprüft, ob ein Knopf mit der übergebenen Funktion aktiv ist
        for(Button button : buttons){
            if(button.getFunc() == func && button.isActive()){
                return true;
            }
        }
        for(Slider slider : sliders){
            if(slider.getFunc() == func && slider.isActive()){
                return true;
            }
        }
        return false;
    }

    public void tick(){                     //in tick wird die Logik der Knöpfe und Slider getickt
        Rectangle rect = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);
        //System.out.print(handler.getMouseManager().getMouseX()+"    ");
        //System.out.println(handler.getMouseManager().getMouseY());

        renderRects.clear();
        for(Button button : buttons){
            button.setActive(false);
            Rectangle uprect = new Rectangle((int)(button.getRect().getX() * 10), (int)(button.getRect().getY() * 10),          //scale rects from menu to 10x
                                            (int)(button.getRect().getWidth() * 10), (int)(button.getRect().getHeight() * 10));
            if(uprect.intersects(rect)){
                renderRects.add(new Rectangle((int)(uprect.getX()), (int)(uprect.getY()), (int)(uprect.getWidth()), (int)(uprect.getHeight())));
            }
            if(uprect.intersects(rect) && handler.getMouseManager().isLeftPressed()){
                button.setHighlighted(true);
                renderRects.add(new Rectangle((int)(uprect.getX()), (int)(uprect.getY()), (int)(uprect.getWidth()), (int)(uprect.getHeight())));
            }if(uprect.intersects(rect) && !handler.getMouseManager().isLeftPressed() && button.isHighlighted()){
                button.setHighlighted(false);
                button.setActive(true);
                //System.out.println("set active menu2");
            }
        }
        for(Slider slider : sliders){
            if(!handler.getMouseManager().isLeftPressed()){
                slider.setActive(false);
            }
            if(slider.isActive() || (Math.sqrt(Math.pow(slider.getXc() - handler.getMouseManager().getMouseX(), 2) +
                    Math.pow(slider.getYc() - handler.getMouseManager().getMouseY(), 2)) < 20 || slider.getRect().intersects(rect)) && handler.getMouseManager().isLeftPressed()){
                slider.setActive(true);
                //System.out.println(slider.getValue());
                slider.setValuePixel(handler.getMouseManager().getMouseX());
                //slider.setValuePixel(800);
                //System.out.print(handler.getMouseManager().getMouseX()+"    ");
                //System.out.println(handler.getMouseManager().getMouseY());
                //System.out.println(slider.getValue());
                //System.out.println("intersect");
            }
        }
    }

    public void render(Graphics g){         //in render werden die Knöpfe, die Slider und das Hintergrundbild gerendert
        g.drawImage(menu, 0, 0, 1920, 1080, null);

        Color color = new Color(100, 100, 100, 180);
        g.setColor(color);
        for(Rectangle rect : renderRects){
            g.fillRect((int)(rect.getX()), (int)(rect.getY()), (int)(rect.getWidth()), (int)(rect.getHeight()));
        }
        for(Button button : buttons){
            if(button.getValue() != -1){
                if(button.getValue() == 1)
                    g.setColor(Color.green);
                else if(button.getValue() == 0)
                    g.setColor(Color.red);
                g.fillOval(button.getXu()*10-40-30, (button.getYo()*10 + (button.getYu()*10 - button.getYo()*10)/2)-26, 60, 60);
            }
            if(debug) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   //antialiasing for font
                g2d.setFont(fraktur);
                g2d.setColor(Color.black);
                //g.setFont(new Font("Monospaced", Font.PLAIN, 36));
                g2d.drawString(String.valueOf(button.getIndex()), button.getXo() * 10 + 5, button.getYo() * 10 + 5);
                g2d.setColor(Color.green);
                g2d.drawString(String.valueOf(button.getIndex()), button.getXo() * 10, button.getYo() * 10);
            }
        }
        g.setColor(Color.cyan);
        for(Slider slider : sliders){
            if(debug) {
                g.setColor(Color.black);
                g.drawString(String.valueOf(slider.getIndex()), slider.getXo() * 10 + 5, slider.getYo() * 10 + 5);
                g.setColor(Color.green);
                //g.setFont(new Font("Monospaced", Font.PLAIN, 36));
                g.drawString(String.valueOf(slider.getIndex()), slider.getXo() * 10, slider.getYo() * 10);
            }
            //System.out.println(slider.getXo()+"   "+slider.getXu()+"   "+slider.getYo()+"   "+slider.getYu()+"   "+slider.getValue()+"   "+slider.getMax()+"   "+slider.getMin());
            float xc = slider.getXc();
            float yc = slider.getYc();

            //System.out.println(xc);
            //System.out.println(yc);

            //  draw rectangle over slider
            //g.setColor(color);
            //Rectangle rect = slider.getRect();
            //g.fillRect((int)(rect.getX()), (int)(rect.getY()), (int)(rect.getWidth()), (int)(rect.getHeight()));

            g.drawImage(Assets.sliderKnob, (int)(xc-30), (int)(yc-30), null);
            //g.setColor(Color.white);
            //g.fillOval((int)(xc) - 22, (int)(yc) - 22, 44, 44);
            //g.setColor(Color.cyan);
            //g.fillOval((int)(xc) - 20, (int)(yc) - 20, 40, 40);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setFont(fraktur);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.drawString(String.valueOf(slider.getValue()), slider.getXu()*10 + 5, slider.getYu()*10 - 10);
            g.setColor(Color.cyan);
            //g.setFont(new Font("Monospaced", Font.PLAIN, 36));
            g.drawString(String.valueOf(slider.getValue()), slider.getXu()*10, slider.getYu()*10 - 15);
        }
        //for(Button button : buttons){
        //    g.fillRect((int)(10* button.getRect().getX()), (int)(10* button.getRect().getY()), (int)(10* button.getRect().getWidth()), (int)(10* button.getRect().getHeight()));
        //}
    }

    public void saveSettings(){             //hier werden die Änderungen an den Einstellungen gespeichert, die mit den Knöpfen und Slidern festgelegt werden
        handler.getGame().getWriter().readSettingsFromFile(false);
        for(Slider slider : sliders){
            handler.getGame().getWriter().changeSetting(slider.getFunc(), slider.getValue());
        }
        for(Button button : buttons){
            if(button.getValue() != -1.0f){
                handler.getGame().getWriter().changeSetting(button.getFunc(), button.getValue());
            }
        }
        handler.getGame().getWriter().writeSettingsToFile();
    }

    public void toggleButton(String func){  //hier wird der Wert des Knopfes invertiert
        for(Button button : buttons){
            if(button.getFunc() == func){
                button.toggle();
            }
        }
    }

    public class Slider{
        boolean active = false;                                                 //ob der Knopf gerendert und getickt werden muss
        String func;                                                            //die Funktion, die der Knopf erfüllt
        int index;                                                              //der Index, die Nummer des Knopfes
        float value, min, max, def;                                             //hier werdend er Wert des Sliders, die Grenzen des Wertes und der Standard gespeichert
        int color;                                                              //hier wird die Farbe des Sliders gespeichert
        int xo, yo, xu, yu;                                                     //die Poistionen der oberen linken und unteren rechten Ecke
        Rectangle rect;                                                         //ein Rechteck mit den Maßen des Sliders
        public Slider(int index, int x1, int y1, int x2, int y2, int color){    //im Konstruktor wird der Slider erstellt und die Werte initialisiert
            this.index = index;
            this.color = color;
            createSlider(x1, y1, x2, y2);
        }

        public void setValuePixel(int pixel){
            int tempvalue = (int)((float)(pixel - xo*10) / ((xu - xo) * 10) * max);
            if(tempvalue >= min && tempvalue <= max){
                value = tempvalue;
            }
        }

        public void minMaxDef(float min, float max, float def){
            this.min = min;
            this.max = max;
            this.def = def;
            this.value = def;
        }

        public void createSlider(int x1, int y1, int x2, int y2){               //erstellt einen neuen Slider mit den übergebenen Koordinaten
            if(x1 < x2){
                xo = x1;
                xu = x2;
            }else{
                xo = x2;
                xu = x1;
            }
            if(y1 < y2){
                yo = y1;
                yu = y2;
            }else{
                yo = y2;
                yu = y1;
            }
            //TODO: scale rect HERE! instead of in tick method
            rect = new Rectangle(xo*10, yo*10, xu*10 - xo*10, yu*10 - yo*10);
        }

        //Getters und Setters
        public Rectangle getRect() {
            return rect;
        }

        public float getValue() {
            return value;
        }

        public int getXo() {
            return xo;
        }

        public int getYo() {
            return yo;
        }

        public int getXu() {
            return xu;
        }

        public int getYu() {
            return yu;
        }

        public float getXc() {
            return value / max * (xu - xo) * 10 + xo * 10;
        }

        public float getYc() {
            return yu * 5 - yo * 5 + yo * 10;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getIndex() {
            return index;
        }

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }
    }

    public class Button{
        float value = -1.0f;//  default value -1.0f if button isn't used to toggle stuff (i.e. change menu)
                            //  Button value will only be written to settings.txt if value != -1.0f
        boolean highlighted = false;                                            //hier wird gespeichert, ob der Knopf anders gerendert werden muss
        boolean active = false;                                                 //hier wird gespeichert, ob der Knopf gerendert und getickt werden muss
        String func;                                                            //hier wird gespeichert, was für eine Funktion der Knopf erfüllt
        int color;                                                              //die Farbe des Knopfes
        int index;                                                              //der Index, die Nummer des Knopfes
        int xo, yo, xu, yu;                                                     //die Poistionen der oberen linken und unteren rechten Ecke
        Rectangle rect;                                                         //ein Rechteck mit den Maßen des Knopfes
        public Button(int index, int x1, int y1, int x2, int y2, int color){    //im Konstruktor wird ein Knopf erstellt und die Werte initialisiert
            this.index = index;
            this.color = color;
            createButton(x1, y1, x2, y2);
        }

        public void toggle(){                                                   //eine Funktion um den Wert eines Knopfes zu invertieren
            if(value == 1f)
                value = 0f;
            else if(value == 0f)
                value = 1f;
        }

        public void createButton(int x1, int y1, int x2, int y2){               //erstellt einen neuen Knopf mit den übergebenen Koordinaten
            if(x1 < x2){
                xo = x1;
                xu = x2;
            }else{
                xo = x2;
                xu = x1;
            }
            if(y1 < y2){
                yo = y1;
                yu = y2;
            }else{
                yo = y2;
                yu = y1;
            }
            rect = new Rectangle(xo + 1, yo + 1, xu - xo - 1, yu - yo - 1);
        }

        //Getters und Setters
        public Rectangle getRect() {
            return rect;
        }

        public int getIndex() {
            return index;
        }

        public int getXo() {
            return xo;
        }

        public int getYo() {
            return yo;
        }

        public int getXu() {
            return xu;
        }

        public int getYu() {
            return yu;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getFunc() {
            return func;
        }

        public void setFunc(String func) {
            this.func = func;
        }

        public boolean isHighlighted() {
            return highlighted;
        }

        public void setHighlighted(boolean highlighted) {
            this.highlighted = highlighted;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    public class Point{

        //hier werden die Werte für Farben und Position des Punktes gespeichert
        int red, green, blue;
        int X, Y;
        int color;

        //im Konstruktor werden die Werte für Farben und Position gespeichert
        public Point(int red, int green, int blue, int X, int Y, int color){
            this.color = color;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.X = X;
            this.Y = Y;
        }

        //Getters
        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }

        public int getX() {
            return X;
        }

        public int getY() {
            return Y;
        }

        public int getColor() {
            return color;
        }
    }
}
