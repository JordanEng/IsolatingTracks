/**
 * Created by ros_jheng on 1/22/2016.
 */
import java.io.*;
import javax.sound.sampled.*;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV meow = new ReadingWAV(new File("done.wav"));
        meow.getFormat();
    }
}
