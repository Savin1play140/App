package app.sound;

import app.Main;
import app.utils.Localization;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundManager {
  public class Sound implements AutoCloseable {
    private boolean released = false;
    
    private AudioInputStream stream = null;
    
    private Clip clip = null;
    
    private FloatControl volumeControl = null;
    
    private boolean playing = false;
    
    public Sound(File f) {
      try {
        this.stream = AudioSystem.getAudioInputStream(f);
        this.clip = AudioSystem.getClip();
        this.clip.open(this.stream);
        this.clip.addLineListener(new Listener());
        this.volumeControl = (FloatControl)this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        this.released = true;
      } catch (Exception exc) {
        exc.printStackTrace();
        this.released = false;
        close();
      } 
    }
    
    public boolean isReleased() {
      return this.released;
    }
    
    public boolean isPlaying() {
      return this.playing;
    }
    
    public void play(boolean breakOld) {
      if (this.released)
        if (breakOld) {
          this.clip.stop();
          this.clip.setFramePosition(0);
          this.clip.start();
          this.playing = true;
        } else if (!isPlaying()) {
          this.clip.setFramePosition(0);
          this.clip.start();
          this.playing = true;
        }  
    }
    
    public void play() {
      play(true);
    }
    
    public void stop() {
      if (this.playing)
        this.clip.stop(); 
    }
    
    public void close() {
      if (this.clip != null)
        this.clip.close(); 
      if (this.stream != null)
        try {
          this.stream.close();
        } catch (IOException exc) {
          exc.printStackTrace();
        }  
    }
    
    public void setVolume(float x) {
      if (x < 0.0F)
        x = 0.0F; 
      if (x > 1.0F)
        x = 1.0F; 
      float min = this.volumeControl.getMinimum();
      float max = this.volumeControl.getMaximum();
      this.volumeControl.setValue((max - min) * x + min);
    }
    
    public float getVolume() {
      float v = this.volumeControl.getValue();
      float min = this.volumeControl.getMinimum();
      float max = this.volumeControl.getMaximum();
      return (v - min) / (max - min);
    }
    
    public void join() {
      if (!this.released)
        return; 
      synchronized (this.clip) {
        try {
          while (this.playing)
            this.clip.wait(); 
        } catch (InterruptedException interruptedException) {}
      } 
    }
    
    public static Sound playSound(String path) {
      File f = new File(path);
      (new SoundManager()).getClass();
      Sound snd = new Sound(f);
      snd.play();
      return snd;
    }
    
    private class Listener implements LineListener {
      public void update(LineEvent ev) {
        if (ev.getType() == LineEvent.Type.STOP) {
          SoundManager.Sound.this.playing = false;
          synchronized (SoundManager.Sound.this.clip) {
            SoundManager.Sound.this.clip.notify();
          } 
        } 
      }
    }
  }
  
  public static void PlaySound(String path, boolean autojoin) {
    if (!Main.soundsEnable)
      return; 
    Localization.sendText("sound.wait", "info");
    Localization.sendText("sound.play", "info");
    (new Main()).getClass();
    Sound snd = null;
    (new SoundManager()).getClass();
    snd = new Sound(new File(path));
    snd.play();
    if (autojoin)
      snd.join(); 
    snd.stop();
    snd = null;
    Localization.sendText("sound.played", "info");
  }
  
  public static void PlaySound(String path) {
    PlaySound(path, true);
  }
}
