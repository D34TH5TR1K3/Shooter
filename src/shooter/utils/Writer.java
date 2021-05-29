package shooter.utils;

import shooter.Handler;
import shooter.entities.Enemy;
import shooter.entities.Entity;
import shooter.entities.Player;
import shooter.gfx.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Writer {

    FileWriter writer;                                                      //die Instanz eines FileWriters, der es ermöglicht, Daten und Werte in eine Datei zu speichern
    Scanner scanner;                                                        //die Instanz eines Scanners, der es ermöglicht, Daten und Werte aus einer Datei auszulesen
    File settingFile = new File("res/settings/settings.txt");     //die Datei, in der Einstellungen gespeichert werden
    File gameSaveFile = new File("res/gameSaves/gameSave.txt");   //die Datei, in der der Spielstand gespeichert wird
    ArrayList<Setting> settings = new ArrayList<>();                        //eine Arraylist, in der kurzfristig Die Einstellungen gespeichert werden

    public Writer(){                                                        //ein leerer Konstruktor für Writer

    }

    public float getScale(){                                                //eine Methode um die Skalierung des Spiels auf die Auflösung von Moritz' Laptop anzupassen
        File scaleFile = new File("C://Users//morit//OneDrive//Documents//GitHub//scale.txt");
        if(scaleFile.exists()){
            return 1.33333333333333f;
        }else{
            return 1f;
        }
    }

    public void changeSetting(String name, float value){                    //die Methode um eine Einstellung in der ArrayList zu verändern
        //readFromFile(false); //call readfromfile before changing settings to update settings ArrayList
        for(Setting setting : settings){
            if(name.equals(setting.getName())){
                setting.setValue(value);
            }
        }
    }

    public void readSettingsFromFile(boolean print){                        //die Methode um alle Einstellungen aus der Datei in die ArrayList einzulesen
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

    public float GetSettingValue(String name){                              //die Methode um den Wert einer einzelnen Einstellung aus der Datei auszulesen
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

    public void writeSettingsToFile(){                                      //die Methode um alle Einstellungen aus der ArrayList in die Datei zu speichern
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

    public static class Setting{                                                   //eine Subklasse um Einstellungen leichter speichern zu können

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

    public World createGame(Handler handler){                               //eine Methode um ein Spiel mithilfe der Datei mit dem Spielstand zu erstelle/laden
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
                for(int i=0;i<enemyCount;i++){
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

    public void wipeGame(){                                                 //eine Methode um den Spielstand zu löschen (beim erneuten starten des Spiels wird eine neue Instanz erschaffen
        try{
            writer = new FileWriter(gameSaveFile,false);
            writer.write("-1");
            writer.flush();
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void writeGameSave(World world){                                 //eine Methode um den Spielstand in die Datei zu speichern
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

    public static class GameSave{                                                  //eine Subklasse um den Spielstand leichter speichern zu können
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
