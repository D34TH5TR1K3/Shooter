package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.Menu;

import java.awt.Graphics;

public class MenuState extends State {

    public Menu menu1, menu2;                       //hier werden die beiden Menüs gespeichert
    private Menu activeMenu;                        //hier wird gespeichert, welchers Menü gerade aktiv ist

    public MenuState(Game game, Handler handler){   //hier wird der MenuState und mit ihm seine Menüs initialisiert
        super(game,handler);
        menu1 = new Menu(new String[]{"StartGame", "exit", "ToMenu2", ""}, new String[]{"", "", "", ""}, handler, Assets.menu1, Assets.menu_layout1);
        menu2 = new Menu(new String[]{"ToMainMenu", "VolumeToggle", "GodmodeToggle", "FriendlyFireToggle"}, new String[]{"Volume", "Difficulty", "BulletSpeed", ""}, handler, Assets.menu2, Assets.menu_layout2);

        activeMenu = menu1;
    }

    @Override
    public void tick() {                            //hier wird die Logik des MenuState getickt und entschieden, ob der Nutzer mit dem Menü interagiert
        activeMenu.tick();  // tick active menu and check buttons
        if(activeMenu.funcActive("ToMainMenu")){
            menu2.saveSettings();
            activeMenu = menu1;
        }else if(activeMenu.funcActive("ToMenu2")){
            activeMenu = menu2;
        }else if(activeMenu.funcActive("StartGame")){
            State.setState(handler.getGame().gameState);

        }else if(activeMenu.funcActive("VolumeToggle")){
           menu2.toggleButton("VolumeToggle");
           game.getSound().toggleSound(menu2.getButtonValue("VolumeToggle"));
        }else if(activeMenu.funcActive("GodmodeToggle")){
            menu2.toggleButton("GodmodeToggle");

        }else if(activeMenu.funcActive("FriendlyFireToggle")){
            menu2.toggleButton("FriendlyFireToggle");

        }else if(activeMenu.funcActive("Volume")){
            float volume = menu2.getSliderValue("Volume");
            //System.out.println(game.getSound().getBackgroundMinVolume() + (game.getSound().getBackgroundMaxVolume() - game.getSound().getBackgroundMinVolume()) * volume / 100f);
            game.getSound().setBackgroundVolume(game.getSound().getBackgroundMinVolume() + (game.getSound().getBackgroundMaxVolume() - game.getSound().getBackgroundMinVolume()) * volume / 100f);
        }else if(activeMenu.funcActive("exit")){
            System.exit(0);
        }
    }

    @Override
    public void render(Graphics g){                 //hier wird das aktive Menü gerendert und angezeigt
        activeMenu.render(g);
    }

    //Getters und Setters
    public Menu getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(Menu activeMenu) {
        this.activeMenu = activeMenu;
    }
}
