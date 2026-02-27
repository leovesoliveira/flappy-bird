package com.leoves.main;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

  public static final Sound FLY = new Sound("/fly.wav");
  public static final Sound SCORE = new Sound("/score.wav");
  public static final Sound HIT = new Sound("/hit.wav");
  private final Clip clip;

  private Sound(String name) {
    try {
      InputStream is = Sound.class.getResourceAsStream(name);
      assert is != null;
      InputStream bufferedIn = new BufferedInputStream(is);
      AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);

      clip = AudioSystem.getClip();
      clip.open(ais);
    } catch (Exception e) {
      System.err.println("Error on load sound: " + name);
      throw new RuntimeException(e);
    }
  }

  public void play() {
    if (clip == null) return;

    if (clip.isRunning()) {
      clip.stop();
    }
    clip.setFramePosition(0);
    clip.start();
  }

  public void loop() {
    if (clip == null) return;
    clip.setFramePosition(0);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }

  public boolean isPlaying() {
    return clip != null && clip.isRunning();
  }
}
