package shooter.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    //saves all keys
    private final boolean[] keys;
    //saves all functional keys
    public boolean up, left, down, right;
    public boolean esc, reload;

    //this constructor initializes the values
    public KeyManager() {
        keys = new boolean[256];
    }

    //ticks the values of the functional keys
    public void tick() {
        up = keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_A];
        down = keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_D];
        esc = keys[KeyEvent.VK_ESCAPE];
        reload = keys[KeyEvent.VK_R];
    }

    //methods required for the KeyListener logic to work properly
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() <= 255)
            keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() <= 255)
            keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
