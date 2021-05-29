package shooter.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private final boolean[] keys;                             //hier werden alle Tasten gespeichert
    public boolean up, left, down, right, esc, save;    //hier werden die Werte (gedrückt/nicht gedrückt) der Tasten gespeichert, mit denen wir das Spiel steuern

    public KeyManager() { keys = new boolean[256]; }    //im Konstruktor wird das Array mit den keys initialisiert, auf die der KeyListener hört

    public void tick() {                                //in der tick-Methode wird in unseren Funktionstasten gespeichert, ob sie gedrückt sind oder nicht
        up = keys[KeyEvent.VK_W];
        left = keys[KeyEvent.VK_A];
        down = keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_D];
        esc = keys[KeyEvent.VK_ESCAPE];
        save = keys[KeyEvent.VK_COMMA];
    }

    @Override
    public void keyPressed(KeyEvent e) {                //hier wird darauf reagiert, dass eine Taste gedrückt wird
        if(e.getKeyCode() >= 0 && e.getKeyCode() <=255)
            keys[e.getKeyCode()] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {               //hier wird darauf reagiert, dass eine Taste losgelassen wird
        if(e.getKeyCode() >= 0 && e.getKeyCode() <=255)
            keys[e.getKeyCode()] = false;
    }
    @Override
    public void keyTyped(KeyEvent e) { }                //die keyTyped Methode wird, weil erforderlich überschrieben aber erhält keine Funktion
}
