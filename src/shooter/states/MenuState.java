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
        menu1 = new Menu(new String[]{"StartGame", "exit", "ToMenu2", ""}, new String[]{"", "", "", ""}, handler, Assets.menu1, Assets.menu_layout1);
        menu2 = new Menu(new String[]{"ToMainMenu", "VolumeToggle", "GodmodeToggle", "FriendlyFireToggle"}, new String[]{"Volume", "Difficulty", "BulletSpeed", ""}, handler, Assets.menu2, Assets.menu_layout2);

        activeMenu = menu1;
    }

    @Override
    public void tick() {
        activeMenu.tick();
        if(activeMenu.funcActive("ToMainMenu")){
            menu2.saveSettings();
            activeMenu = menu1;
        }else if(activeMenu.funcActive("ToMenu2")){
            activeMenu = menu2;
        }else if(activeMenu.funcActive("StartGame")){
            State.setState(handler.getGame().gameState);
        }else if(activeMenu.funcActive("VolumeToggle")){
           menu2.toggleButton("VolumeToggle");
        }else if(activeMenu.funcActive("GodmodeToggle")){
            menu2.toggleButton("GodmodeToggle");
        }else if(activeMenu.funcActive("FriendlyFireToggle")){
            menu2.toggleButton("FriendlyFireToggle");
        }else if(activeMenu.funcActive("exit")){
            System.exit(0);
        }
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
