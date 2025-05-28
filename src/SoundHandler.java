import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundHandler {

    private float sound;
    private FloatControl sound1Control, sound2Control, sound3Control;
    private Clip sound1, sound2, sound3;
    public SoundHandler() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        sound1 = AudioSystem.getClip();
        sound2 = AudioSystem.getClip();
        sound3 = AudioSystem.getClip();
        sound1.open(AudioSystem.getAudioInputStream(new File("sounds/blockdrop.wav").getAbsoluteFile()));
        sound2.open(AudioSystem.getAudioInputStream(new File("sounds/lineclear.wav").getAbsoluteFile()));
        sound3.open(AudioSystem.getAudioInputStream(new File("sounds/music.wav").getAbsoluteFile()));
        sound = -14.0f;
        sound1Control = (FloatControl) sound1.getControl((FloatControl.Type.MASTER_GAIN));
        sound2Control = (FloatControl) sound2.getControl((FloatControl.Type.MASTER_GAIN));
        sound3Control = (FloatControl) sound3.getControl((FloatControl.Type.MASTER_GAIN));
        update();
    }

    public void update() {
        sound1Control.setValue(sound);
        sound2Control.setValue(sound);
    }

    public void playSound1() {
        sound1.setMicrosecondPosition(0);
        sound1.start();
    }

    public void playSound2() {
        sound2.setMicrosecondPosition(0);
        sound2.start();
    }

    public void playSound3() {
        sound3.setMicrosecondPosition(0);
        sound3.start();
        sound3.loop(100);
    }
}