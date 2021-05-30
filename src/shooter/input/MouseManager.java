package shooter.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    //saves the pressed buttons of the mouse
    private boolean leftPressed, rightPressed;
    //saves the position of the mouse
    private int mouseX, mouseY;

    //empty constructor
    public MouseManager() { }

    //getters and setters
    public boolean isLeftPressed() { return leftPressed; }
    public boolean isRightPressed() { return rightPressed; }
    public int getMouseX() { return mouseX; }
    public int getMouseY() { return mouseY; }

    //methods required for the MouseListener and MouseMotionListener logic to work properly
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = true;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            leftPressed = false;
        else if(e.getButton() == MouseEvent.BUTTON3)
            rightPressed = false;
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) { }
}
