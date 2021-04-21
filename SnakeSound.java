package snake;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SnakeSound implements AutoCloseable{

    private boolean released;
    private AudioInputStream stream;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean playing = false;

    static boolean isMute;

    public SnakeSound(InputStream is) {
        try {
            stream = AudioSystem.getAudioInputStream(is);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            if (volumeControl == null) volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            released = false;
            close();
        }
    }

    @Override
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

    public boolean isPlaying() {
        return playing;
    }

    public void play(boolean breakOld) {
        if (!isMute)
        {
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
    }

    // То же самое, что и play(true)
    public void play() {
        play(true);
    }

    public void setVolume(float x) {
        x /= 10.0;
        if (x<0) x = 0;
        if (x>1) x = 1;
        float max = volumeControl.getMaximum();
        float min = volumeControl.getMinimum() / 2;
        volumeControl.setValue((max-min)*x+min);
    }

    public void stop() {
        if (playing) {
            clip.stop();
        }
    }
}