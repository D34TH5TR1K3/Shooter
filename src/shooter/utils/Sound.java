package shooter.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Sound {
    //saves the currently playing Clip and its length
    private static Clip BgClip;
    private static short BgClipLen;
    //indicates the Max, Min and actual Volume of the Music
    private static float BgVolMax = -15f, BgVolMin = -35f, BgVol = -40f;
    //indicates the Max, Min and actual Volume of SFX
    private static float SFXVolMax = -15f, SFXVolMin = -35f, SFXVol = -20f;
    //indicates whether Background music is active
    private static boolean BgActive = false;
    //sounds distributes the Files with SFX
    private static final Map<String,File> sounds = Map.ofEntries(
        Map.entry("Shotgun", new File("res/sound/Shotgun.wav")),
        Map.entry("RocketExplode", new File("res/sound/RocketExplode.wav")),
        Map.entry("RocketLaunch", new File("res/sound/RocketLaunch.wav")),
        Map.entry("Uzi", new File("res/sound/Uzi.wav")),
        Map.entry("Ak", new File("res/sound/AK.wav")),
        Map.entry("Machete", new File("res/sound/sound_machete.wav")),
        Map.entry("Knife", new File("res/sound/sound_knife.wav")),
        Map.entry("LoadGun", new File("res/sound/sound_loadGun.wav")),
        Map.entry("InsertShell", new File("res/sound/sound_insertShell.wav")),
        Map.entry("Hit1", new File("res/sound/sound_hit_1.wav")),
        Map.entry("Hit2", new File("res/sound/sound_hit_2.wav")),
        Map.entry("Hit3", new File("res/sound/sound_hit_3.wav")),
        Map.entry("Hit4", new File("res/sound/sound_hit_4.wav")),
        Map.entry("Hit5", new File("res/sound/sound_hit_5.wav")),
        Map.entry("DrawKnife", new File("res/sound/sound_drawKnife.wav"))

    );
    //songs distributes the Files with Background music
    private static final File[] songs = {
            new File("res/sound/bensound-house.wav"),
            new File("res/sound/bensound-scifi.wav"),
    };

    //empty constructor
    public Sound() {

    }

    //initializes the Settings
    public static void init() {
        Writer writer = new Writer();
        if(writer.getSettingValue("Music") == 1)
            Sound.playBackgroundMusic();
        float volume = writer.getSettingValue("Music Volume");
        Sound.setBgVol(Sound.getBgVolMin() + (Sound.getBgVolMax() - Sound.getBgVolMin()) * volume / 100f);
        if(writer.getSettingValue("SFX") == 0)
            Sound.toggleSFX(false);
        volume = writer.getSettingValue("SFX Volume");
        Sound.setSFXVol(Sound.getSFXVolMin() + (Sound.getSFXVolMax() - Sound.getSFXVolMin()) * volume / 100f);
    }
    //ticks whether a new Clip needs to be played
    public static void tick(){
        if (BgClip!=null && (float) BgClip.getFramePosition()/ BgClipLen > 0.99f)
            playBackgroundMusic();
    }

    //static method to play SFX
    public static void play(String Name){
        try {
            if(SFXVol==-0.0f)
                return;
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sounds.get(Name)));

            ((FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(SFXVol);

            clip.start();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    //method to toggle SFX
    public static void toggleSFX(boolean value) {
        SFXVol = (value)?SFXVol:-0.0f;
    }
    //method to play Background music
    public static void playBackgroundMusic(){
        if(BgClip!=null)
            BgClip.stop();
        try {
            BgClip = AudioSystem.getClip();
            BgClip.open(AudioSystem.getAudioInputStream(songs[(int)(Math.random()*songs.length)]));

            ((FloatControl) BgClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(BgVol);

            BgClip.start();
            BgActive = true;
            BgClipLen = (short)BgClip.getFrameLength();

        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    //method to toggle Sound
    public static void toggleBackgroundMusic(boolean val){
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
    public static void setBgVol(float value) {
        if(BgClip != null) ((FloatControl) BgClip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(value);
        BgVol = Math.min(value, BgVolMax);
    }
    public static float getBgVolMax() { return BgVolMax; }
    public static float getBgVolMin() { return BgVolMin; }
    public static void setSFXVol(float value) { SFXVol = Math.min(value, SFXVolMax); }
    public static float getSFXVolMax() { return SFXVolMax; }
    public static float getSFXVolMin() { return SFXVolMin; }
}
