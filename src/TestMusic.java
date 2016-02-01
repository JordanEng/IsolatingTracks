/**
 * Created by ros_jheng on 1/22/2016.
 */

import java.io.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.Random;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("done.wav"));
       // AudioFormat audioFormat = audioRead.getFormat();
        byte[] timeFrequencyArrayX = audioRead.toByteArray();

        testFft(timeFrequencyArrayX);
    }

    private static void testFft(byte[] timeFrequencyArrayX) {

        // need to change these so my arrays are used
        double[] wavReals = toDoubleArray(timeFrequencyArrayX);
        int size = wavReals.length;

        double[] inputreal = wavReals;
            double[] inputimag = wavReals;

            double[] refoutreal = new double[size];
            double[] refoutimag = new double[size];

            double[] newArray = FFT.fft(inputreal, inputimag, false);

            System.out.println(newArray.toString());
        }

    public static double[] toDoubleArray(byte[] byteArray){
        int times = Double.SIZE / Byte.SIZE;
        double[] doubles = new double[byteArray.length / times];
        for(int i=0;i<doubles.length;i++){
            doubles[i] = ByteBuffer.wrap(byteArray, i*times, times).getDouble();
        }
        return doubles;
    }
}
