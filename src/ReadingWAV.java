/**
 * Created by ros_jheng on 1/20/2016.
 */
import java.io.*;
import javax.sound.sampled.*;

public class ReadingWAV {

    private AudioInputStream audioInputStream;
    private AudioFormat format;

    public ReadingWAV(File file)
            throws UnsupportedAudioFileException, IOException {
        audioInputStream = AudioSystem.getAudioInputStream(file);
        format = audioInputStream.getFormat();
    }

    // Return audio format, and through it, most properties of
    // the audio file: sample size, sample rate, etc.
    public AudioFormat getFormat() {
        System.out.println(audioInputStream.toString());
        return format;
    }

    // Return the number of samples of all channels
    public long getSampleCount() {
        long total = (audioInputStream.getFrameLength() *
                format.getFrameSize() * 8) / format.getSampleSizeInBits();
        return total / format.getChannels();
    }
}
