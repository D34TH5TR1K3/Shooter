package shooter.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Writer {

    FileWriter writer;
    Scanner scanner;
    File settingFile = new File("res/settings/settings.txt");
    ArrayList<Setting> settings = new ArrayList<Setting>();

    public Writer(){

    }

    public float getScale(){
        File scaleFile = new File("C://Users//morit//OneDrive//Documents//GitHub//scale.txt");
        if(scaleFile.exists()){
            return 1.33333333333333f;
        }else{
            return 1f;
        }
    }

    public void changeSetting(String name, float value){
        //readFromFile(false); //call readfromfile before changing settings to update settings ArrayList
        for(Setting setting : settings){
            if(name.equals(setting.getName())){
                setting.setValue(value);
            }
        }
    }

    public void readFromFile(boolean print){
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

    public void writeToFile(){
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

    public class Setting{

        String name;
        float value;

        public Setting(String name, float value){
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }
    }
}
