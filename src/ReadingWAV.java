
import java.io.*;
import javax.sound.sampled.*;

public class ReadingWAV {

    private AudioInputStream audioInputStream;
    private AudioFormat format;
    private byte[] audioBytes;

    public ReadingWAV(File file){

        int totalFramesRead = 0;

        // somePathName is a pre-existing string whose value was
        // based on a user selection.
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
            if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                // some audio formats may have unspecified frame size
                // in that case we may read any amount of bytes
                bytesPerFrame = 1;
            }
            // Set an arbitrary buffer size of 1024 frames.
            int numBytes = 1024 * bytesPerFrame;
            audioBytes = new byte[numBytes];
            try {
                int numBytesRead = 0;
                int numFramesRead = 0;
                // Try to read numBytes bytes from the file.
                while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
                    // Calculate the number of frames actually read.
                    numFramesRead = numBytesRead / bytesPerFrame;
                    totalFramesRead += numFramesRead;
                    // Here, do something useful with the audio data that's
                    // now in the audioBytes array...
                }
            } catch (Exception ex) {
                System.err.print(ex);
            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public byte[] toByteArray(){
        return audioBytes;
    }

    public static AudioInputStream writeBytesBackToStream(byte[] bytes) {
        ByteArrayInputStream baiut = new ByteArrayInputStream(bytes);
        AudioInputStream stream = null;
        try {
            stream = AudioSystem.getAudioInputStream(baiut);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        if(stream.equals(null) || stream == null) {
            System.out.println("WARNING: Stream read by byte array is null!");
        }
        return stream;
    }
}
