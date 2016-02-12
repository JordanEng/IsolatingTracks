
import java.io.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("done.wav"));
       // AudioFormat audioFormat = audioRead.getFormat();
        byte[] timeFrequencyArrayX = audioRead.toByteArray();
        testFft(timeFrequencyArrayX);
    }

    private static void testFft(byte[] timeFrequencyArrayX) {
        int arraySize = 32768;
        double[] inputImag = new double[arraySize];
        double[] inputReal = new double[arraySize];

        for(int i = 0; i < arraySize; i++){
            inputReal[i] = 0.0;
            inputImag[i] = 0.0;
        }

        for(int i = 0; i < toDoubleArray(timeFrequencyArrayX).length; i++){
            inputImag[i] = toDoubleArray(timeFrequencyArrayX)[i];
        }

        double[] newArray = FFT.fft(inputReal, inputImag, true);

        // make it transform new array the other to see if it works.
        for(double i: newArray) {
           System.out.println(i);
        }

        double[] meow = FFT.fft(inputReal, newArray, false);
        if (inputImag == meow){
            System.out.println();
            System.out.println("yay");
        } else{
            System.out.println("boo");
        }
    }

    // This isn't converting the array exactly right. :(
    public static double[] toDoubleArray(byte[] byteArray){
        int times = Double.SIZE / Byte.SIZE;
        double[] doubles = new double[byteArray.length / times];
        for(int i = 0; i < doubles.length; i++){
            doubles[i] = ByteBuffer.wrap(byteArray, i*times, times).getDouble();
        }
        return doubles;
    }
}
