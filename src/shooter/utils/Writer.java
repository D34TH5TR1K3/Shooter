package shooter.utils;

import shooter.Handler;
import shooter.entities.Enemy;
import shooter.entities.Entity;
import shooter.entities.Player;
import shooter.gfx.ImageLoader;
import shooter.world.Level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Writer {
    //the Utils required to read from and write to files
    private static FileWriter writer;
    private Scanner scanner;
    //the Files for reading and writing Settings and GameSaves
    private static final File settingFile = new File("res/settings/settings.txt");
    private static final File gameSaveFile = new File("res/gameSaves/gameSave.txt");
    //settings saves all Settings in the program
    private final ArrayList<Setting> settings = new ArrayList<>();

    //empty constructor
    public Writer(){

    }
    //method to get the scale of the game
    public float getScale(){
        File scaleFile = new File("C://Users//morit//OneDrive//Documents//GitHub//scale.txt");
        if(scaleFile.exists()){
            return 1.33333333333333f;
        }else{
            return 1f;
        }
    }
    //method to change a Setting
    public void changeSetting(String name, float value){
        //readFromFile(false); //call readfromfile before changing settings to update settings ArrayList
        for(Setting setting : settings){
            if(name.equals(setting.getName())){
                setting.setValue(value);
            }
        }
    }
    //method to read Settings from a file
    public void readSettingsFromFile(boolean print){
        try {
            settings.clear();
            scanner = new Scanner(settingFile);
            String fileRead = scanner.nextLine();   //read 1st line
            while(fileRead != null){    //loop until no next line found
                String[] tokenize = fileRead.split(","); // spilt line at each comma and store individual strings in array

                String tempName = tokenize[0];
                float tempValue = Float.parseFloat(tokenize[1]);//  new lines without settings in settings.txt break code!!!
                //System.out.println(tempName+tempValue+name);

                Setting tempSetting = new Setting(tempName, tempValue);

                settings.add(tempSetting);  //add new Setting with values from array
                if(scanner.hasNextLine()) { //check if there is a next line
                    fileRead = scanner.nextLine();
                }else{
                    fileRead = null;
                }
            }
            scanner.close();
            if(print) {
                for(Setting setting : settings){ //print out each setting
                    System.out.println(setting.getName() + "   " + setting.getValue());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //method to get the value of a Setting
    public float getSettingValue(String name){
        try {
            scanner = new Scanner(settingFile);
            String fileRead = scanner.nextLine();   //read 1st line
            while(fileRead != null){    //loop until no next line found
                String[] tokenize = fileRead.split(","); // spilt line at each comma and store individual strings in array

                String tempName = tokenize[0];
                float tempValue = Float.parseFloat(tokenize[1]);
                //System.out.println(tempName+tempValue+name);
                if(tempName.equals(name)){      //if current setting has same name as setting the method is looking for return value
                    scanner.close();
                    return tempValue;
                }
                if(scanner.hasNextLine()) { //check if there is a next line
                    fileRead = scanner.nextLine();
                }else{
                    fileRead = null;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //method to write Settings to a file
    public void writeSettingsToFile(){
        try {
            writer = new FileWriter(settingFile, false);
            for(Setting setting : settings){
                writer.write(setting.getName() + ",");
                writer.write(setting.getValue() + "\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method to load a level from res
    public static Level loadLevel(int number,Handler handler){
        try{
            String path = "/levels/Level_"+number+"/";
            Level level = new Level(number,new BufferedImage[]{ImageLoader.loadImage(path + "Map.png"), ImageLoader.loadImage(path + "Layout.png"), ImageLoader.loadImage(path + "Map2.png")},handler);
            Scanner scanner = new Scanner(new File("res" + path + "LevelData.txt"));
            int enemyCount = Integer.parseInt(scanner.nextLine());
            int[] playerData = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
            Player createdPlayer = new Player(playerData[0],playerData[1],(float)playerData[2],handler,level);
            createdPlayer.getItem().setAmmo(playerData[3]);
            ArrayList<Entity> createdEnemies = new ArrayList<>();
            for(;enemyCount>0;enemyCount--){
                int[] enemyData = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                Enemy createdEnemy = new Enemy(enemyData[0],enemyData[1],enemyData[2],enemyData[3],handler,level);
                createdEnemy.getItem().setAmmo(enemyData[4]);
                createdEnemies.add(createdEnemy);
            }
            level.fillWorld(createdPlayer,createdEnemies);
            scanner.close();
            return level;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    //method for wiping the gameSave from a file
    public static void wipeGame() {
        try{
            writer = new FileWriter(gameSaveFile,false);
            writer.write("0");
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //method to save a GameSave to a file
    public static void writeGameSave(Level level) {
        GameSave gameSave = new GameSave(level);
        try{
            writer = new FileWriter(gameSaveFile,false);
            writer.write(gameSave.levelNumber+"\n");
            writer.write(gameSave.enemies.size()+"\n");
            writer.write(gameSave.player.getData()+"\n");
            for(Entity e: gameSave.enemies){
                writer.write(e.getData()+"\n");
            }
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //method to read a GameSave from a file
    public static Level loadGameSave(Handler handler) {
        try{
            Scanner scanner = new Scanner(gameSaveFile);
            String levelNumber = scanner.nextLine();
            if(levelNumber.equals("0"))
                return loadLevel(1,handler);
            Level level = loadLevel(Integer.parseInt(levelNumber),handler);
            level.resetEntityManager();
            int enemyCount = Integer.parseInt(scanner.nextLine());
            int[] playerData = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
            Player createdPlayer = new Player(playerData[0],playerData[1],(float)playerData[2],handler,level);
            if(playerData[3]!=0)
                createdPlayer.getItem().setAmmo(playerData[3]);
            ArrayList<Entity> createdEnemies = new ArrayList<>();
            for(;enemyCount>0;enemyCount--){
                int[] enemyData = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                Enemy createdEnemy = new Enemy(enemyData[0],enemyData[1],enemyData[2],enemyData[3],handler,level);
                createdEnemy.getItem().setAmmo(enemyData[4]);
                createdEnemies.add(createdEnemy);
            }
            level.fillWorld(createdPlayer,createdEnemies);
            scanner.close();
            return level;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    //subclass for easier saving of settings
    public static class Setting {

        String name;
        float value;

        public Setting(String name, float value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
    //subclass for easier saving of the game
    public static class GameSave {
        private Level level;
        private int levelNumber;
        private Player player;
        private ArrayList<Entity> enemies;

        public GameSave(Level level){
            this.level = level;
            levelNumber = level.getLevelNumber();
            player = level.getEntityManager().getPlayer();
            enemies = level.getEntityManager().getEnemies();
        }

        public Player getPlayer(){ return player; }
    }
}
