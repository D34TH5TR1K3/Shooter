package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.Menu;

import java.awt.Graphics;

public class MenuState extends State {

    public Menu menu1, menu2;
    private Menu activeMenu;

    public MenuState(Game game, Handler handler){
        super(game,handler);
        menu1 = new Menu(new String[]{"options", "", "", ""}, new String[]{"volume", "", "", ""}, this, handler, Assets.menu1, Assets.menu_layout1);
        menu2 = new Menu(new String[]{"back", "", "", ""}, new String[]{"scale", "", "", ""}, this, handler, Assets.menu2, Assets.menu_layout2);

        activeMenu = menu1;
    }

    @Override
    public void tick() {
        activeMenu.tick();
    }
    @Override
    public void render(Graphics g){
        activeMenu.render(g);
    }

    public Menu getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(Menu activeMenu) {
        this.activeMenu = activeMenu;
    }
}
