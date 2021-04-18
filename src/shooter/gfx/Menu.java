package shooter.gfx;

import java.awt.*;
import java.util.ArrayList;

public class Menu {

    private ArrayList<Point> points; //Point for rectangle that makes a Button
    private ArrayList<Button> buttons; //store rectangles

    public Menu(){
        points = new ArrayList<Point>();
        buttons = new ArrayList<Button>();
        readMenu();
    }

    public void readMenu(){ // reads the menu and places rectangles on the buttons
        for(int x = 0; x < 192; x++){ // iterates through every pixel
            for(int y = 0; y < 108; y++){
                Color color = new Color(Assets.menu_layout1.getRGB(x, y)); // gets color of every pixel
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if(red > 5 && green > 5 && blue > 5){ // if color != black -> create new point with color and coordinates
                    Point point = new Point(red, green, blue, x, y);
                    points.add(point);
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
                    Button button1 = new Button(point1.getX(), point1.getY(), point2.getX(), point2.getY());
                    buttons.add(button1);
                    points.remove(point2);
                    System.out.println(points.size());
                    break;
                }
            }
        }
        System.out.println(buttons.size());
    }
    public void tick(){

    }
    public void render(Graphics g){
        g.drawImage(Assets.menu1, 0, 0, 1920, 1080, null);
        //for(Button button : buttons){
        //    g.fillRect((int)(10* button.getRect().getX()), (int)(10* button.getRect().getY()), (int)(10* button.getRect().getWidth()), (int)(10* button.getRect().getHeight()));
        //}
    }

    public class Button{ // Stores rectangle of every button

        int x, y, width, height;
        int xo, yo, xu, yu; //x unten y unten x oben y oben
        Rectangle rect;
        public Button(int x1, int y1, int x2, int y2){
            createButton(x1, y1, x2, y2);
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
            rect = new Rectangle(xo, yo, xu - xo, yu - yo);
        }

        public Rectangle getRect() {
            return rect;
        }
    }

    public class Point{ // stores color and coordinates of points

        int red, green, blue;
        int X, Y;

        public Point(int red, int green, int blue, int X, int Y){
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
    }
}
