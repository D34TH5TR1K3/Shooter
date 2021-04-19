package shooter.sound;

import java.io.File;
import java.io.IOException;



import javax.sound.sampled.*;

public class Sound {
    static File soundResult;
    private Clip Bclip; //Backgroundclip
    private int LengthBClip;

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

    public void playBackgroundMusic(){              //Method to play backgroundmusic
        int number = (int)(Math.random()*4.0f);
        System.out.println(number);
        switch (number){
            case 0:
                soundResult = new File("res/sound/sxtn.wav");
                break;
            case 1:
                soundResult = new File("res/sound/Vermissen.wav");
                break;
            case 2:
                soundResult = new File("res/sound/110.wav");
                break;
            case 3:
                soundResult = new File("res/sound/Reihe.wav");
                break;
            default:
                soundResult = new File("res/sound/sxtn.wav");
                break;
        }
        System.out.println("Result"+soundResult);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundResult);

             Bclip = AudioSystem.getClip();
            Bclip.open(audioInputStream);

            FloatControl volume = (FloatControl) Bclip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-30f);

            Bclip.start();
            LengthBClip = Bclip.getFrameLength();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        checkPlaying();
    }


    public void setBackgroundVolume(Float value) {        //Set Background Volume
        FloatControl volume =  (FloatControl) Bclip.getControl(FloatControl.Type.MASTER_GAIN);
       volume.setValue(value);
    }

    public void checkPlaying(){                                             //Check if Backgroundmusic is playing TODO:Smoother?!
        if((float)(Bclip.getFramePosition())/(float)(LengthBClip)>0.98f){
            Bclip.stop();
            playBackgroundMusic();
        }

    }

}
