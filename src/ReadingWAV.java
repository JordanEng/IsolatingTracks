
import java.io.*;
import javax.sound.sampled.*;

public class ReadingWAV {

    private AudioInputStream audioInputStream;
    private AudioFormat format;
    private byte[] audioBytes;

    public ReadingWAV(File file)
            throws UnsupportedAudioFileException, IOException {
//        audioInputStream = AudioSystem.getAudioInputStream(file);
//        format = audioInputStream.getFormat();
//    }
//
//    // Return audio format, and through it, most properties of
//    // the audio file: sample size, sample rate, etc.
//    public AudioFormat getFormat() {
//        System.out.println(audioInputStream.toString());
//        return format;
//    }
//
//    public byte[] toByteArray() throws IOException {
//        InputStream is = audioInputStream;
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//        int nRead;
//        byte[] data = new byte[32768];
//
//        while ((nRead = is.read(data, 0, data.length)) != -1) {
//            buffer.write(data, 0, nRead);
//        }
//
//        try {
//            buffer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return buffer.toByteArray();
//    }
        int totalFramesRead = 0;

        // somePathName is a pre-existing string whose value was
        // based on a user selection.
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerFrame =
                    audioInputStream.getFormat().getFrameSize();
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
                // Handle the error...
            }
        } catch (Exception e) {
            // Handle the error...
        }
    }

    public byte[] toByteArray(){
        return audioBytes;
    }
}
