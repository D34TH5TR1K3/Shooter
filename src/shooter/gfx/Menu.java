package shooter.gfx;

import shooter.Handler;
import shooter.utils.Writer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static shooter.gfx.Display.fraktur;
import static shooter.gfx.Display.frakturBig;

public class Menu {
    //variables for title animation
    private float[] titleDir;
    private Color[] titleColor;
    private float counter = 0;
    //saves the points, buttons, sliders and Rectangles to be rendered
    private final ArrayList<Point> points = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<Slider> sliders = new ArrayList<>();
    private final ArrayList<Rectangle> renderRects = new ArrayList<>();
    //handler distributes variables
    Handler handler;
    //saves the image and the layout of the menu
    BufferedImage menu, menu_layout;
    //saves the functions of the Buttons and Sliders
    String[] actionButtons, actionSliders;
    //a Writer to handle Settings
    private final Writer writer = new Writer();
    //which menu is it?
    private int menuType;

    //this constructor initializes the values
    public Menu(String[] actionButtons, String[] actionSliders, Handler handler, BufferedImage menu, BufferedImage menu_layout){
        this.actionButtons = actionButtons;
        this.actionSliders = actionSliders;
        this.menu_layout = menu_layout;
        this.menu = menu;
        this.handler = handler;
        if(menu == Assets.menu1)
            menuType = 1;
        else
            menuType = 0;
        titleColor = new Color[100];
        titleDir = new float[100];
        readMenu();
    }

    //ticks the menus logic
    public void tick(){
        counter += 0.01f;

        if(menuType == 1) {
            for (int i = 0; i < 15; i++) {
                float temp = (float) (Math.sin(i) + 1) / 2;
                //titleColor[i] = new Color(0, (int)(temp*255), 255*i/10, 255*i/10);

                titleColor[i] = Color.getHSBColor((float) (counter / 10 + i * 0.05), 1, 1);
                int alpha = 255 * i / 10;
                if (alpha > 255)
                    alpha = 255;
                titleColor[i] = new Color(titleColor[i].getRed(), titleColor[i].getBlue(), titleColor[i].getGreen(), alpha);

                titleDir[i] = (float) (Math.sin((float) (i) / 10 + counter) + 1) * 3;
            }
        }


        Rectangle rect = new Rectangle(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 1, 1);

        renderRects.clear();
        for(Button button : buttons){
            button.setActive(false);
            Rectangle uprect = new Rectangle((int)(button.getRect().getX() * 10), (int)(button.getRect().getY() * 10),
                                            (int)(button.getRect().getWidth() * 10), (int)(button.getRect().getHeight() * 10));
            if(uprect.intersects(rect))
                renderRects.add(new Rectangle((int)(uprect.getX()), (int)(uprect.getY()), (int)(uprect.getWidth()), (int)(uprect.getHeight())));
            if(uprect.intersects(rect) && handler.getMouseManager().isLeftPressed()){
                button.setHighlighted(true);
                renderRects.add(new Rectangle((int)(uprect.getX()), (int)(uprect.getY()), (int)(uprect.getWidth()), (int)(uprect.getHeight())));
            }if(uprect.intersects(rect) && !handler.getMouseManager().isLeftPressed() && button.isHighlighted()){
                button.setHighlighted(false);
                button.setActive(true);
            }
        }
        for(Slider slider : sliders){
            if(!handler.getMouseManager().isLeftPressed())
                slider.setActive(false);
            if(slider.isActive() || (Math.sqrt(Math.pow(slider.getXc() - handler.getMouseManager().getMouseX(), 2) +
                    Math.pow(slider.getYc() - handler.getMouseManager().getMouseY(), 2)) < 20 || slider.getRect().intersects(rect)) && handler.getMouseManager().isLeftPressed()){
                slider.setActive(true);
                slider.setValuePixel(handler.getMouseManager().getMouseX());
            }
        }
    }
    //renders the menu
    public void render(Graphics g){
        g.drawImage(menu, 0, 0, 1920, 1080, null);
        Graphics2D g2d = (Graphics2D) g;
        if(menuType == 1) {
            AffineTransform reset = g2d.getTransform();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   //antialiasing for font
            g2d.setFont(frakturBig);
            int titleWidth = g2d.getFontMetrics(frakturBig).stringWidth("wolfenberg");
            for (int i = 0; i < 15; i++) {
                g2d.rotate(Math.toRadians(titleDir[i]), 1920 / 2, 150);
                g2d.setColor(titleColor[i]);
                g2d.drawString("wolfenberg", 1920 / 2 - titleWidth / 2, 150);
                g2d.setTransform(reset);
            }
        }
        Color color = new Color(100, 100, 100, 180);
        g.setColor(color);
        for(Rectangle rect : renderRects)
            g.fillRect((int)(rect.getX()), (int)(rect.getY()), (int)(rect.getWidth()), (int)(rect.getHeight()));

        boolean debug = false;
        for(Button button : buttons){
            if(button.getValue() != -1){
                if(button.getValue() == 1)
                    g.setColor(Color.green);
                else if(button.getValue() == 0)
                    g.setColor(Color.red);
                g.fillOval(button.getXu()*10-40-30, (button.getYo()*10 + (button.getYu()*10 - button.getYo()*10)/2)-26, 60, 60);
            }
            if(debug) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   //antialiasing for font
                g2d.setFont(fraktur);
                g2d.setColor(Color.black);
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
                g.drawString(String.valueOf(slider.getIndex()), slider.getXo() * 10, slider.getYo() * 10);
            }
            float xc = slider.getXc();
            float yc = slider.getYc();
            g.drawImage(Assets.sliderKnob, (int)(xc-30), (int)(yc-30), null);
            g2d.setFont(fraktur);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.drawString(String.valueOf(slider.getValue()), slider.getXu()*10 + 5, slider.getYu()*10 - 10);
            g.setColor(Color.cyan);
            g.drawString(String.valueOf(slider.getValue()), slider.getXu()*10, slider.getYu()*10 - 15);
        }
    }

    //method to read the menu from the layout and place the according objects
    public void readMenu(){
        short indexButton = 0;
        short indexSlider = 0;
        //TODO: read slider min and max etc from txt file since we know in which order they are read in

        for(short y = 0; y < 108; y++)
            for(short x = 0; x < 192; x++){
                Color color = new Color(menu_layout.getRGB(x, y));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                int colorint = color.getRGB();
                if(red > 1 || green > 1 || blue > 1){
                    Point point = new Point(red, green, blue, x, y, colorint);
                    points.add(point);
                }
            }
        while(points.size() > 0){
            Point point1 = points.get(0);
            points.remove(point1);
            for(Point point2 : points)
                if(point1.getRed() == point2.getRed() &&
                        point1.getGreen() == point2.getGreen() &&
                        point1.getBlue() == point2.getBlue()){
                    if(Math.abs(point1.getY() - point2.getY()) > 5){
                        Button button1 = new Button(indexButton, point1.getX(), point1.getY(), point2.getX(), point2.getY(), point2.getColor());
                        button1.setFunc(actionButtons[indexButton]);
                        button1.setValue(writer.getSettingValue(actionButtons[indexButton]));
                        indexButton++;
                        buttons.add(button1);
                    }else{
                        Slider slider1 = new Slider(indexSlider, point1.getX(), point1.getY(), point2.getX(), point2.getY(), point2.getColor());
                        slider1.setFunc(actionSliders[indexSlider]);
                        float deftemp = writer.getSettingValue(actionSliders[indexSlider]);

                        indexSlider++;
                        slider1.minMaxDef(1f, 100f, deftemp);
                        sliders.add(slider1);
                    }

                    points.remove(point2);
                    break;
                }
        }
    }
    //method to check whether a function exists on the current menu
    public boolean funcActive(String func){
        for(Button button : buttons)
            if(button.getFunc().equals(func) && button.isActive())
                return true;
        for(Slider slider : sliders)
            if(slider.getFunc().equals(func) && slider.isActive())
                return true;
        return false;
    }
    //method to save changed Settings
    public void saveSettings(){
        writer.readSettingsFromFile(false);
        for(Slider slider : sliders)
            writer.changeSetting(slider.getFunc(), slider.getValue());

        for(Button button : buttons)
            if(button.getValue() != -1.0f)
                writer.changeSetting(button.getFunc(), button.getValue());

        writer.writeSettingsToFile();
    }
    //method to toggle a buttons value
    public void toggleButton(String func){
        for(Button button : buttons)
            if(button.getFunc().equals(func))
                button.toggle();
    }

    //getters
    public float getSliderValue(String func){
        for(Slider slider : sliders)
            if(slider.getFunc().equals(func))
                return slider.getValue();
        return -1;
    }
    public float getButtonValue(String func){
        for(Button button : buttons)
            if(button.getFunc().equals(func))
                return button.getValue();
        return -1;
    }

    //subclass for sliders
    public static class Slider{
        boolean active = false;
        String func;
        short index;
        float value, min, max, def;
        int color;
        short xo, yo, xu, yu;
        Rectangle rect;

        public Slider(short index, short x1, short y1, short x2, short y2, int color){
            this.index = index;
            this.color = color;
            createSlider(x1, y1, x2, y2);
        }

        public void setValuePixel(int pixel){
            int tempvalue = (int)((float)(pixel - xo*10) / ((xu - xo) * 10) * max);
            if(tempvalue >= min && tempvalue <= max)
                value = tempvalue;
        }

        public void minMaxDef(float min, float max, float def){
            this.min = min;
            this.max = max;
            this.def = def;
            this.value = def;
        }

        public void createSlider(short x1, short y1, short x2, short y2){
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

        public Rectangle getRect() { return rect; }
        public float getValue() { return value; }
        public short getXo() { return xo; }
        public short getYo() { return yo; }
        public short getXu() { return xu; }
        public short getYu() { return yu; }
        public float getXc() { return value / max * (xu - xo) * 10 + xo * 10; }
        public float getYc() { return yu * 5 - yo * 5 + yo * 10; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
        public short getIndex() { return index; }
        public String getFunc() { return func; }
        public void setFunc(String func) { this.func = func; }
    }
    //subclass for buttons
    public static class Button{
        float value = -1.0f;//  default value -1.0f if button isn't used to toggle stuff (i.e. change menu)
                            //  Button value will only be written to settings.txt if value != -1.0f
        boolean highlighted = false;
        boolean active = false;
        String func;
        int color;
        short index;
        short xo, yo, xu, yu;
        Rectangle rect;

        public Button(short index, short x1, short y1, short x2, short y2, int color){
            this.index = index;
            this.color = color;
            createButton(x1, y1, x2, y2);
        }

        public void toggle(){
            if(value == 1f)
                value = 0f;
            else if(value == 0f)
                value = 1f;
        }

        public void createButton(short x1, short y1, short x2, short y2){
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

        public Rectangle getRect() { return rect; }
        public short getIndex() { return index; }
        public short getXo() { return xo; }
        public short getYo() { return yo; }
        public short getXu() { return xu; }
        public short getYu() { return yu; }
        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
        public String getFunc() { return func; }
        public void setFunc(String func) { this.func = func; }
        public boolean isHighlighted() { return highlighted; }
        public void setHighlighted(boolean highlighted) { this.highlighted = highlighted; }
        public float getValue() { return value; }
        public void setValue(float value) { this.value = value; }
    }
    //subclass for points on the layout
    public static class Point{

        int red, green, blue;
        short X, Y;
        int color;

        public Point(int red, int green, int blue, short X, short Y, int color){
            this.color = color;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.X = X;
            this.Y = Y;
        }

        public int getRed() { return red; }
        public int getGreen() { return green; }
        public int getBlue() { return blue; }
        public short getX() { return X; }
        public short getY() { return Y; }
        public int getColor() { return color; }
    }
}
