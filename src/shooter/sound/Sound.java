package shooter.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import java.util.Map;

public class Sound {
    private Clip BgClip;                                            //hier wird der Clip gespeichert, der gerade im Hintergrund spielt
    private int BgClipLen;                                          //hier wird die Zahl der Frames im Clip als Länge gespeichert
    private float BgVolMax = -15f, BgVolMin = -35f, BgVol = -40f;   //hier sind die Presets und die Variablen für den Wert und Min und Max der Lautstärke der Hnitergrundmusik
    private boolean BgActive = false;                               //hier wird gespeichert, ob gerade Hintergrundmusik spielt
    private static final Map<String,File> sounds = Map.of(
            "Shotgun", new File("res/sound/Shotgun.wav"),
            "RocketExplode", new File("res/sound/RocketExplode.wav"),
            "RocketLaunch", new File("res/sound/RocketLaunch.wav"),
            "Uzi", new File("res/sound/Uzi.wav"),
            "Ak", new File("res/sound/AK.wav")
    );                                                              //hier werden die Sfx-Files gespeichert
    private static final File[] songs = {
            new File("res/sound/110.wav"),
            new File("res/sound/Abyss.wav"),
            new File("res/sound/HotlineTheme.wav"),
            new File("res/sound/Reihe.wav"),
            new File("res/sound/sxtn.wav"),
            new File("res/sound/Vermissen.wav")
    };                                                              //hier werden die Dateien mit der Hintergrundmusik gespeichert
    public Sound() {                                                // ein leerer Konstruktor

    }
    public static void play(String Name){                           //eine statische Methode um das Abspielen von Sfx zu ermöglichen
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sounds.get(Name)));

            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-20f);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic(){                              //eine Methode um das Abspielen von Hintergrundmusik zu ermöglichen
        if(BgClip!=null) BgClip.stop();
        try {
            BgClip = AudioSystem.getClip();
            BgClip.open(AudioSystem.getAudioInputStream(songs[(int)(Math.random()*songs.length)]));

            ((FloatControl) BgClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(BgVol);

            BgClip.start();
            BgActive = true;
            BgClipLen = BgClip.getFrameLength();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){                                             //in dieser tick-Methode wird geprüft, ob das Ende eines Liedes erreicht wurde und ggf. ein neues gestartet wird
        if (BgClip!=null && (float) BgClip.getFramePosition()/ BgClipLen > 0.99f) playBackgroundMusic();
    }

    public void toggleSound(boolean val){                           //eine Methode, die ermöglicht den Ton mithilfe eines Knopfes im Menü aus- und einzuschalten
        if(val && BgClip == null) {
            playBackgroundMusic();
            return;
        }
        if(val && !BgActive) BgClip.start();
        if(!val && BgActive) BgClip.stop();
        BgActive ^= true;
    }

    //Getters und Setters
    public void setBgVol(Float value) {
        if(value > BgVolMax) {
            System.out.println("VOLUME EXCEEDS MAX_VOLUME");
            return;
        }
        else if(BgClip != null) ((FloatControl) BgClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(value);
        BgVol = value;
    }

    public float getBgVolMax() {
        return BgVolMax;
    }

    public void setBgVolMax(float bgVolMax) {
        BgVolMax = bgVolMax;
    }

    public float getBgVolMin() {
        return BgVolMin;
    }

    public void setBgVolMin(float bgVolMin) {
        BgVolMin = bgVolMin;
    }
}
