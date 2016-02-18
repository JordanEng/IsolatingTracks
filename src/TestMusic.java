
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;
import java.nio.ByteBuffer;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("done.wav"));
       // AudioFormat audioFormat = audioRead.getFormat();
        byte[] timeFrequencyArrayX = audioRead.toByteArray();

       /* for(byte i: timeFrequencyArrayX){
            System.out.println(i);
        }*/

       // double[] doubles = toDoubleArray(timeFrequencyArrayX, true);
        //for(double i: doubles){
        //    System.out.println(i);
        //}
        testFFT();
    }

   // private static void testFft(double[] doubles) {
    private static void testFFT() throws FileNotFoundException {
        int arraySize = 32768;
        double[] inputImag = new double[arraySize];
        double[] inputReal = new double[arraySize];

        for(int i = 0; i < arraySize; i++){
            inputReal[i] = 0.0;
            inputImag[i] = 0.0;
        }
        Scanner timeSignal = new Scanner(new File("signal.txt"));
        int i = 0;
        while(timeSignal.hasNextDouble()) {
            inputImag[i] = timeSignal.nextDouble();
            timeSignal.nextDouble();
            i++;
        }

        /*for(int i = 0; i < doubles.length; i++){
            inputImag[i] = doubles[i];
        }*/

        double[] newArray = FFT.fft(inputReal, inputImag, true);

        // make it transform new array the other to see if it works.
        for(double d: newArray) {
           System.out.println(d);
        }

        double[] meow = FFT.fft(inputReal, newArray, false);
        if (inputImag == meow){
            System.out.println();
            System.out.println("yay");
        } else{
            System.out.println("boo");
        }
    }

  /*  public static double[] toDoubleArray(byte[] byteArray){
        int times = Double.SIZE / Byte.SIZE;
        double[] doubles = new double[byteArray.length / times];
        for(int i = 0; i < doubles.length; i++){
            doubles[i] = ByteBuffer.wrap(byteArray, i*times, times).getDouble();
        }
        return doubles;
    }*/
  public static final double[] toDoubleArray(byte[] inData, boolean byteSwap) {
      int j = 0, upper, lower;
      int length = inData.length / 8;
      double[] outData = new double[length];
      if (!byteSwap)
          for (int i = 0; i < length; i++) {
              j = i * 8;
              upper = (((inData[j] & 0xff) << 24)
                      + ((inData[j + 1] & 0xff) << 16)
                      + ((inData[j + 2] & 0xff) << 8) + ((inData[j + 3] & 0xff) << 0));
              lower = (((inData[j + 4] & 0xff) << 24)
                      + ((inData[j + 5] & 0xff) << 16)
                      + ((inData[j + 6] & 0xff) << 8) + ((inData[j + 7] & 0xff) << 0));
              outData[i] = Double.longBitsToDouble((((long) upper) << 32)
                      + (lower & 0xffffffffl));
          }
      else
          for (int i = 0; i < length; i++) {
              j = i * 8;
              upper = (((inData[j + 7] & 0xff) << 24)
                      + ((inData[j + 6] & 0xff) << 16)
                      + ((inData[j + 5] & 0xff) << 8) + ((inData[j + 4] & 0xff) << 0));
              lower = (((inData[j + 3] & 0xff) << 24)
                      + ((inData[j + 2] & 0xff) << 16)
                      + ((inData[j + 1] & 0xff) << 8) + ((inData[j] & 0xff) << 0));
              outData[i] = Double.longBitsToDouble((((long) upper) << 32)
                      + (lower & 0xffffffffl));
          }

      return outData;
  }
}
