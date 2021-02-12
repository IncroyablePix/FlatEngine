package com.c4nn4.manager.audio;

import com.c4nn4.manager.debug.Debug;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static final AudioManager instance = new AudioManager();

    public static AudioManager get() {
        return instance;
    }

    private final Map<String, Clip> clips;
    private final int gap;

    private AudioManager() {
        clips = new HashMap<>();
        gap = 0;
    }

    public void load(String s, String n) {
        if (clips.get(n) != null)
            return;
        Clip clip;
        try(AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(this.getClass().getResourceAsStream(s)))) {
            if((clip = loadClipFromAudioStream(ais)) != null)
                clips.put(n, clip);
        }
        catch (Exception e) {
            Debug.log("Failed to load " + s);
            e.printStackTrace();
        }
    }

    private Clip loadClipFromAudioStream(AudioInputStream ais) {
        Clip clip;

        AudioFormat baseFormat = ais.getFormat();

        AudioFormat decodeFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false);

        try(AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais)) {
            clip = AudioSystem.getClip();
            clip.open(dais);
        }
        catch (IOException | LineUnavailableException e) {
            throw new AudioLoadException(e);
        }

        return clip;
    }

    public void unload(String s) {
        if (clips.containsKey(s)) {
            Clip clip = clips.get(s);

            if (clip.isRunning())
                clip.stop();

            clips.remove(s);
        }
    }

    public boolean isLoaded(String s) {
        return clips.containsKey(s);
    }

    public void play(String s) {
        play(s, gap);
    }

    public void play(String s, int i) {
        Clip c = clips.get(s);

        if (c == null) {
            Debug.log("Failed to play " + s);
        }
        else {
            if (c.isRunning())
                c.stop();

            c.setFramePosition(i);

            while (!c.isRunning()) {
                c.start();
            }
        }
    }

    public void stop(String s) {
        if (clips.get(s) == null)
            return;

        if (clips.get(s).isRunning())
            clips.get(s).stop();
    }

    public void resume(String s) {
        if (clips.get(s).isRunning())
            return;

        clips.get(s).start();
    }

    public void resumeLoop(String s) {
        Clip c = clips.get(s);

        if (c == null)
            return;

        c.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void loop(String s) {
        Clip c = clips.get(s);
        if(c == null)
            Debug.log("Failed to play " + s);
        else
            loop(s, gap, gap, c.getFrameLength() - 1);
    }

    public void loop(String s, int frame) {
        Clip c = clips.get(s);
        if(c == null)
            Debug.log("Failed to play " + s);
        else
            loop(s, frame, gap, clips.get(s).getFrameLength() - 1);
    }

    public void loop(String s, int start, int end) {
        loop(s, gap, start, end);
    }

    public void loop(String s, int frame, int start, int end) {
        Clip c = clips.get(s);

        if (c == null) {
            Debug.log("Failed to play " + s);
        }
        else {
            if (c.isRunning())
                c.stop();

            c.setLoopPoints(start, end);
            c.setFramePosition(frame);
            c.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void setPosition(String s, int frame) {
        clips.get(s).setFramePosition(frame);
    }

    public int getFrames(String s) {
        return clips.get(s).getFrameLength();
    }

    public int getPosition(String s) {
        return clips.get(s).getFramePosition();
    }

    public void close(String s) {
        stop(s);
        clips.get(s).close();
    }

    public void setVolume(String s, float f) {
        Clip c = clips.get(s);
        if (c == null)
            return;
        FloatControl vol = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
        vol.setValue(f);
    }

    public boolean isPlaying(String s) {
        Clip c = clips.get(s);
        if (c == null)
            return false;
        return c.isRunning();
    }
}
