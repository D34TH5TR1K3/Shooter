package shooter.utils;

import shooter.Handler;
import shooter.entities.Enemy;
import shooter.entities.Entity;
import shooter.entities.Player;
import shooter.gfx.ImageLoader;
import shooter.world.World;
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
    FileWriter writer;
    Scanner scanner;
    //the Files for reading and writing Settings and GameSaves
    File settingFile = new File("res/settings/settings.txt");
    File gameSaveFile = new File("res/gameSaves/gameSave.txt");
    //settings saves all Settings in the program
    ArrayList<Setting> settings = new ArrayList<>();

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
    public float GetSettingValue(String name){
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
            Level level = new Level(new BufferedImage[]{ImageLoader.loadImage(path + "Map.png"), ImageLoader.loadImage(path + "Layout.png")},handler);
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
            return level;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    /*
    //method to create a game from a file
    public World createGame(Handler handler){
        try{
            scanner = new Scanner(gameSaveFile);
            int enemyCount = Integer.parseInt(scanner.nextLine());
            World world = new World(handler);
            if(enemyCount==-1){
                world.fillWorld();
            }else{
                int[] pd = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                //pd = playerData
                Player createdPlayer = new Player(pd[0],pd[1],(float)pd[2],handler,world);
                createdPlayer.getItem().setAmmo(pd[3]);
                ArrayList<Entity> createdEnemies = new ArrayList<>();
                for(;enemyCount>0;enemyCount--){
                    int[] ed = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
                    //ed = EnemyData
                    Enemy createdEnemy = new Enemy(ed[0],ed[1],ed[2],ed[3],handler,world);
                    createdEnemy.getItem().setAmmo(ed[4]);
                    createdEnemies.add(createdEnemy);
                }
                world.fillWorld(createdPlayer,createdEnemies);
            }
            return world;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    */
    //method for wiping the gameSave from a file
    public void wipeGame() {
        try{
            writer = new FileWriter(gameSaveFile,false);
            writer.write("-1");
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    //method to save the game to a file
    public void writeGameSave(World world) {
        GameSave gameSave = new GameSave(world);
        try{
            writer = new FileWriter(gameSaveFile,false);
            writer.write(gameSave.enemies.size()+"\n");
            writer.write(gameSave.getPlayer().getData()+"\n");
            for(Entity e: gameSave.enemies){
                writer.write(e.getData()+"\n");
            }
            writer.flush();
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
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

        /*
        public void setName(String name) {
            this.name = name;
        }
        */

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
    //subclass for easier saving of the game
    public static class GameSave {
        private World world;
        private Player player;
        private ArrayList<Entity> enemies;

        public GameSave(World world){
            this.world = world;
            player = world.getPlayer();
            enemies = world.getEntityManager().getEnemies();
        }

        public World getWorld(){
            return world;
        }
        public void setWorld(World world){
            this.world = world;
            player = world.getPlayer();
            enemies = world.getEntityManager().getEnemies();
        }
        public Player getPlayer(){
            return player;
        }
        public ArrayList<Entity> getEnemies(){
            return enemies;
        }
    }
}
