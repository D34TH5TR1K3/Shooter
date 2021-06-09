package shooter.states;

import shooter.Game;
import shooter.Handler;
import shooter.gfx.Assets;
import shooter.gfx.Menu;
import shooter.utils.Sound;
import shooter.utils.Timer;
import shooter.utils.Writer;
import shooter.world.World;

import java.awt.*;

import static shooter.gfx.Display.fraktur;

public class MenuState extends State {
    //instances of menus
    public Menu menu1, menu2;
    //activeMenu saves the active Menu
    private Menu activeMenu;
    //saves the World to access Saves and Levels
    private final World world;
    //timer to make text disappear
    Timer timer_save;
    //this constructor initializes the values
    public MenuState(Game game, Handler handler){
        super(game,handler);
        world = handler.getWorld();
        menu1 = new Menu(new String[]{"New Game", "Load Game", "Options", "Save Game", "Exit", "Credits"}, new String[]{}, handler, Assets.menu1, Assets.menu_layout1);
        menu2 = new Menu(new String[]{"Back", "Friendly Fire", "SFX", "Music"}, new String[]{"Music Volume", "SFX Volume", "Player Movement Speed", "Player Bullet Speed", "Enemy Movement Speed", "Enemy Bullet Speed", "Enemy Reload Speed", "Enemy Line Of Sight"}, handler, Assets.menu2, Assets.menu_layout2);

        activeMenu = menu1;
    }

    //ticks the activeMenu
    @Override
    public void tick() {
        activeMenu.tick();
        if(activeMenu.funcActive("New Game")) {
            Writer.wipeGame();
            world.reloadLevel(1);
            world.setLevel(1);
            State.setState(game.gameState);
        } else if(activeMenu.funcActive("Load Game")) {
            world.reloadLevel(0);
            world.setLevel(0);
            State.setState(game.gameState);
        } else if(activeMenu.funcActive("Options")) {
            activeMenu = menu2;
        } else if(activeMenu.funcActive("Save Game")) {
            Writer.writeGameSave(world.getActiveLevel());
            timer_save = new Timer(2000);
        } else if(activeMenu.funcActive("Exit")) {
            System.exit(0);
        } else if(activeMenu.funcActive("Credits")){
            handler.getGame().credits = true;
        } else if(activeMenu.funcActive("Back")) {
            activeMenu = menu1;
        } else if(activeMenu.funcActive("Friendly Fire")) {
            menu2.toggleButton("Friendly Fire");
            shooter.entities.Bullet.friendlyFire ^= menu2.getButtonValue("Friendly Fire")>0;
        } else if(activeMenu.funcActive("SFX")) {
            menu2.toggleButton("SFX");
            Sound.toggleSFX(menu2.getButtonValue("SFX")>0);
        } else if(activeMenu.funcActive("Music")) {
            menu2.toggleButton("Music");
            Sound.toggleBackgroundMusic(menu2.getButtonValue("Music")>0);
        } else if(activeMenu.funcActive("Music Volume")) {
            float volume = menu2.getSliderValue("Music Volume");
            Sound.setBgVol(Sound.getBgVolMin() + (Sound.getBgVolMax() - Sound.getBgVolMin()) * volume / 100f);
        } else if(activeMenu.funcActive("SFX Volume")) {
            float volume = menu2.getSliderValue("SFX Volume");
            Sound.setSFXVol(Sound.getSFXVolMin() + (Sound.getSFXVolMax() - Sound.getSFXVolMin()) * volume / 100f);
        } else if(activeMenu.funcActive("Player Movement Speed")) {
            shooter.entities.Player.speed = menu2.getSliderValue("Player Movement Speed");
        } else if(activeMenu.funcActive("Player Bullet Speed")) {
            shooter.entities.Bullet.playerBulletSpeed = menu2.getSliderValue("Player Bullet Speed");
        } else if(activeMenu.funcActive("Enemy Movement Speed")) {
            shooter.entities.Enemy.speed = menu2.getSliderValue("Enemy Movement Speed") / 10;
        } else if(activeMenu.funcActive("Enemy Bullet Speed")) {
            shooter.entities.Bullet.enemyBulletSpeed = menu2.getSliderValue("Enemy Bullet Speed");
        } else if(activeMenu.funcActive("Enemy Reload Speed")) {
            shooter.entities.Enemy.reloadspeed = menu2.getSliderValue("Enemy Reload Speed") / 10;
        } else if(activeMenu.funcActive("Enemy Line Of Sight")) {
            shooter.entities.Enemy.LOSdist = menu2.getSliderValue("Enemy Line Of Sight") * 10;
        } else{
            return;
        }
        menu2.saveSettings();
    }
    //renders the activeMenu
    @Override
    public void render(Graphics g){
        activeMenu.render(g);
        if(timer_save != null && timer_save.valid()){
            g.setColor(Color.cyan);
            g.setFont(fraktur);
            g.drawString("Current level saved",1550,300);
        }else{
            timer_save = null;
        }
        if(game.getTimer_save() != null && game.getTimer_save().valid()){
            g.setColor(Color.cyan);
            g.setFont(fraktur);
            g.drawString("Current level saved",1550,300);
        }else{
            game.setTimer_save(null);
        }
    }
}
