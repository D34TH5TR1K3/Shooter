package shooter.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean leftPressed, rightPressed;                  //hier wird gespeichert, ob die linke oder die rechte Maustaste gerade gedrückt sind
    private int mouseX, mouseY;                                 //hier wird die Position des Mauszeigers gespeichert
    
    public MouseManager() { }                                   //ein leerer Konstruktor

    //Getters und Setters
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    @Override
    public void mousePressed(MouseEvent e) {                    //hier wird geprüft ob die Linke oder Rechte Maustaste gerade gedrückt werden
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {                   //hier wird geprüft ob die Linke oder Rechte Maustaste losgelassen wurden
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {                      //hier wird ausgelesen, wo der Mauszeiger sich gerade befindet
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {                    //hier wird ausgelesen, wohin sich die Maus bewegt hat, während eine der Maustasten gedrückt war
        mouseX = e.getX();
        mouseY = e.getY();
    }

    //Methoden, die, weil erforderlich überschrieben werden aber die hier keine Funktion haben
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
