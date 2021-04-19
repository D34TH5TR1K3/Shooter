package shooter.gfx;

import shooter.Handler;
import shooter.states.MenuState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu {

    private ArrayList<Point> points; //Point for rectangle that makes a Button
    private ArrayList<Button> buttons; //store rectangles
    private ArrayList<Slider> sliders;
    private ArrayList<Rectangle> renderRects;
    Handler handler;
    BufferedImage menu, menu_layout;
    String[] actionButtons, actionSliders;

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

    public void readMenu(){ // reads the menu and places rectangles on the buttons
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

    public boolean funcActive(String func){
        for(Button button : buttons){
            if(button.getFunc() == func && button.isActive()){
                return true;
            }
        }
        return false;
    }

    public void tick(){
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
                System.out.println(slider.getValue());
                slider.setValuePixel(handler.getMouseManager().getMouseX());
                //slider.setValuePixel(800);
                //System.out.print(handler.getMouseManager().getMouseX()+"    ");
                //System.out.println(handler.getMouseManager().getMouseY());
                //System.out.println(slider.getValue());
                //System.out.println("intersect");
            }
        }
    }

    public void render(Graphics g){
        g.drawImage(menu, 0, 0, 1920, 1080, null);

        Color color = new Color(100, 100, 100, 180);
        g.setColor(color);
        for(Rectangle rect : renderRects){
            g.fillRect((int)(rect.getX()), (int)(rect.getY()), (int)(rect.getWidth()), (int)(rect.getHeight()));
        }
        for(Button button : buttons){
            g.setColor(Color.green);
            g.setFont(new Font("Monospaced", Font.PLAIN, 36));
            g.drawString(String.valueOf(button.getIndex()), button.getXo()*10, button.getYo()*10);
        }
        g.setColor(Color.cyan);
        for(Slider slider : sliders){
            g.setColor(Color.green);
            g.setFont(new Font("Monospaced", Font.PLAIN, 36));
            g.drawString(String.valueOf(slider.getIndex()), slider.getXo()*10, slider.getYo()*10);
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
            g.setColor(Color.cyan);
            g.setFont(new Font("Monospaced", Font.PLAIN, 36));
            g.drawString(String.valueOf(slider.getValue()), slider.getXu()*10, slider.getYu()*10 - 15);
        }
        //for(Button button : buttons){
        //    g.fillRect((int)(10* button.getRect().getX()), (int)(10* button.getRect().getY()), (int)(10* button.getRect().getWidth()), (int)(10* button.getRect().getHeight()));
        //}
    }

    public void saveSettings(){
        handler.getGame().getWriter().readFromFile(false);
        for(Slider slider : sliders){
            handler.getGame().getWriter().changeSetting(slider.getFunc(), slider.getValue());
        }
        for(Button button : buttons){
            if(button.getValue() != -1.0f){
                handler.getGame().getWriter().changeSetting(button.getFunc(), button.getValue());
            }
        }
        handler.getGame().getWriter().writeToFile();
    }

    public void toggleButton(String func){
        for(Button button : buttons){
            if(button.getFunc() == func){
                button.toggle();
            }
        }
    }

    public class Slider{  //stores sliders of menu
        boolean active = false;
        String func;
        int index;
        float xc, yc;
        float  value, min, max, def;
        int color;
        int x, y, width, height;
        int xo, yo, xu, yu; //x unten y unten x oben y oben
        Rectangle rect;
        public Slider(int index, int x1, int y1, int x2, int y2, int color){
            this.index = index;
            this.color = color;
            createSlider(x1, y1, x2, y2);
        }

        public void setValuePixel(int pixel){
            int tempvalue = (int)((float)(pixel - xo*10) / (float)(xu*10 - xo*10) * max);
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

        public void createSlider(int x1, int y1, int x2, int y2){
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

        public float getXc() {
            return ((float)(value) / (float)(max)) * (xu*10 - xo*10) + xo*10; // get center of slider
        }

        public float getYc() {
            return ((yu*10 - yo*10) / 2) + yo*10;
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

        public int getXu() {
            return xu;
        }

        public int getYu() {
            return yu;
        }
    }

    public class Button{ // Stores rectangle of every button
        float value = -1.0f;//  default value -1.0f if button isn't used to toggle stuff (i.e. change menu)
                            //  Button value will only be written to settings.txt if value != -1.0f
        boolean highlighted = false;
        boolean active = false;
        String func;
        int color;
        int index;
        int x, y, width, height;
        int xo, yo, xu, yu; //x unten y unten x oben y oben
        Rectangle rect;
        public Button(int index, int x1, int y1, int x2, int y2, int color){
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

        public void createButton(int x1, int y1, int x2, int y2){
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

        public Rectangle getRect() {
            return rect;
        }

        public int getIndex() {
            return index;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
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

    public class Point{ // stores color and coordinates of points

        int red, green, blue;
        int X, Y;
        int color;

        public Point(int red, int green, int blue, int X, int Y, int color){
            this.color = color;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.X = X;
            this.Y = Y;
        }

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
