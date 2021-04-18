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
            return 1.5f;
        }else{
            return 1f;
        }
    }

    public void readFromFile(){
        try {
            scanner = new Scanner(settingFile);
            String fileRead = scanner.nextLine();   //read 1st line
            while(fileRead != null){    //loop until no next line found
                String[] tokenize = fileRead.split(","); // spilt line at each comma and store individual strings in array

                String tempName = tokenize[0];
                float tempValue = Float.parseFloat(tokenize[1]);
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

            for(Setting setting : settings){ //print out each setting
                System.out.println(setting.getName() + "   " + setting.getValue());
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

                Setting tempSetting = new Setting(tempName, tempValue);

                settings.add(tempSetting);  //add new Setting with values from array
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

    public void writeToFile(String name, String value){
        try {
            writer = new FileWriter(settingFile, true);
            writer.write(name + ",");
            writer.write(value + "\n");

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

        public void setValue(int value) {
            this.value = value;
        }
    }
}