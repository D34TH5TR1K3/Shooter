package shooter.sound;

import java.io.File;
import java.io.IOException;


import javax.sound.sampled.*;

public class Sound {
    static File soundResult;
    private static int i =1;

    static File url;

    public Sound() {

    }
    public static void play(String Name){       //Method to play using a name, selection via switch case
        switch (Name){
            case "SXTN":
                soundResult = new File("res/sound/sxtn.wav");  //TODO add path
                break;

            case "uzi":
                soundResult = new File("res/sound/sndUzi.wav");
                break;
            default:
                soundResult = new File("res/sound/sxtn.wav"); //TODO add default file path
                break;
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundResult);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-30f);

            clip.start();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
