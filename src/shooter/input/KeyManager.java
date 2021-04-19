package shooter.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private boolean[] keys;
    public boolean up, left, down, right, esc;

    public KeyManager() { keys = new boolean[256]; }

    public void tick() {
        //System.out.println(esc);
        up = keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_A];
        down = keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_D];
        esc = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    @Override
    public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
    @Override
    public void keyTyped(KeyEvent e) { }
}
