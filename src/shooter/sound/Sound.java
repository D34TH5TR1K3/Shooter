package shooter.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

import java.util.Map;

public class Sound {
    //saves the currently playing Clip and its length
    private Clip BgClip;
    private int BgClipLen;
    //indicates the Max, Min and actual Volume
    private float BgVolMax = -15f, BgVolMin = -35f, BgVol = -40f;
    //indicates whether Background music is active
    private boolean BgActive = false;
    //sounds distributes the Files with SFX
    private static final Map<String,File> sounds = Map.of(
            "Shotgun", new File("res/sound/Shotgun.wav"),
            "RocketExplode", new File("res/sound/RocketExplode.wav"),
            "RocketLaunch", new File("res/sound/RocketLaunch.wav"),
            "Uzi", new File("res/sound/Uzi.wav"),
            "Ak", new File("res/sound/AK.wav")
    );
    //songs distributes the Files with Background music
    private static final File[] songs = {
            new File("res/sound/110.wav"),
            new File("res/sound/Abyss.wav"),
            new File("res/sound/HotlineTheme.wav"),
            new File("res/sound/Reihe.wav"),
            new File("res/sound/sxtn.wav"),
            new File("res/sound/Vermissen.wav")
    };

    //empty constructor
    public Sound() {

    }

    //ticks whether a new Clip needs to be played
    public void tick(){
        if (BgClip!=null && (float) BgClip.getFramePosition()/ BgClipLen > 0.99f)
            playBackgroundMusic();
    }

    //static method to play SFX
    public static void play(String Name){
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sounds.get(Name)));

            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-20f);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    //method to play Background music
    public void playBackgroundMusic(){
        if(BgClip!=null)
            BgClip.stop();
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
    //method to toggle Sound
    public void toggleSound(boolean val){
        if(val && BgClip == null) {
            playBackgroundMusic();
            return;
        }
        if(val && !BgActive)
            BgClip.start();
        if(!val && BgActive)
            BgClip.stop();
        BgActive ^= true;
    }

    //getters and setters
    public void setBgVol(Float value) {
        if(value > BgVolMax) {
            System.out.println("VOLUME EXCEEDS MAX_VOLUME");
            return;
        }
        else if(BgClip != null)
            ((FloatControl) BgClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(value);
        BgVol = value;
    }
    public float getBgVolMax() { return BgVolMax; }
    public void setBgVolMax(float bgVolMax) { BgVolMax = bgVolMax; }
    public float getBgVolMin() { return BgVolMin; }
    public void setBgVolMin(float bgVolMin) { BgVolMin = bgVolMin; }
}
