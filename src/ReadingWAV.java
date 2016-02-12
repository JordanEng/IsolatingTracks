
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


    public byte[] toByteArray() throws IOException {
        InputStream is = audioInputStream;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[32768];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        try {
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }
}
