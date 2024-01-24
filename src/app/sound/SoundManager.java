package app.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import app.Main;
import app.logger.Logger;
import app.utils.Localization;

public class SoundManager {
	public class Sound implements AutoCloseable {
		private boolean released = false;
		private AudioInputStream stream = null;
		private Clip clip = null;
		private FloatControl volumeControl = null;
		private boolean playing = false;
		
		public Sound(java.io.File f) {
			try {
				stream = AudioSystem.getAudioInputStream(f);
				clip = AudioSystem.getClip();
				clip.open(stream);
				clip.addLineListener(new Listener());
				volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				released = true;
			} catch (Exception exc) {
				exc.printStackTrace();
				released = false;
				
				close();
			}
		}

		public boolean isReleased() {
			return released;
		}
		
		public boolean isPlaying() {
			return playing;
		}

		public void play(boolean breakOld) {
			if (released) {
				if (breakOld) {
					clip.stop();
					clip.setFramePosition(0);
					clip.start();
					playing = true;
				} else if (!isPlaying()) {
					clip.setFramePosition(0);
					clip.start();
					playing = true;
				}
			}
		}
		
		public void play() {
			play(true);
		}
		
		public void stop() {
			if (playing) {
				clip.stop();
			}
		}
		
		public void close() {
			if (clip != null)
				clip.close();
			
			if (stream != null)
				try {
					stream.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
		}

		public void setVolume(float x) {
			if (x<0) x = 0;
			if (x>1) x = 1;
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			volumeControl.setValue((max-min)*x+min);
		}
		
		public float getVolume() {
			float v = volumeControl.getValue();
			float min = volumeControl.getMinimum();
			float max = volumeControl.getMaximum();
			return (v-min)/(max-min);
		}

		public void join() {
			if (!released) return;
			synchronized(clip) {
				try {
					while (playing)
						clip.wait();
				} catch (InterruptedException exc) {}
			}
		}
		
		public static Sound playSound(String path) {
			java.io.File f = new java.io.File(path);
			Sound snd = (new SoundManager()).new Sound(f);
			snd.play();
			return snd;
		}

		private class Listener implements LineListener {
			public void update(LineEvent ev) {
				if (ev.getType() == LineEvent.Type.STOP) {
					playing = false;
					synchronized(clip) {
						clip.notify();
					}
				}
			}
		}
	}
	public static void PlaySound(String path, boolean autojoin) {
		if (!Main.soundsEnable) return;
		Logger.info(Localization.getText("sound.wait"));
		Logger.info(Localization.getText("sound.play"));

		(new Main()).getClass();
		Sound snd = null;
		snd = (new SoundManager()).new Sound(new java.io.File(path));
		snd.play();
		if (autojoin) snd.join(); 
		snd.stop();
		snd = null;
		Logger.info(Localization.getText("sound.played"));
	}

	public static void PlaySound(String path) { PlaySound(path, true); }
	private SoundManager() {}
}
