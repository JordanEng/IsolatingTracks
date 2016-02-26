
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;
import java.nio.ByteBuffer;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("magic.wav"));
        byte[] timeFrequencyArrayX = audioRead.toByteArray();

//        for(byte i: timeFrequencyArrayX){
//            System.out.println(i);
//        }

        double[] doubles = new double[timeFrequencyArrayX.length / 3];
        for (int i = 0, j = 0; i != doubles.length; ++i, j += 3) {
            doubles[i] = (double)( (timeFrequencyArrayX[j] & 0xff) |
                    ((timeFrequencyArrayX[j+1] & 0xff) <<  8) |
                    ( timeFrequencyArrayX[j+2]         << 16));
        }

//        double[] doubles = toDoubleArray(timeFrequencyArrayX);
        for(double i: doubles){
            System.out.println(i);
        }

        testFFT(doubles);
    }

    private static void testFFT(double[] doubles) throws FileNotFoundException {
        int arraySize = 32768;
        double[] inputImag = new double[arraySize];
        double[] inputReal = new double[arraySize];

        for(int i = 0; i < arraySize; i++){
            inputReal[i] = 0.0;
            inputImag[i] = 0.0;
        }

        System.arraycopy(doubles, 0, inputImag, 0, doubles.length);

//        for (double i: inputImag){
//            System.out.println(i);
//        }

        double[] newArray = FFT.fft(inputReal, inputImag, true);


//         for (double i: newArray ){
//             System.out.println(i);
//         }


        //
        // Test code below to reverse the transformation back to the original.
        //

        double[] newInputReal = new double[arraySize];
        double[] newInputImag = new double[arraySize];
        int ii = 0;
        for (int i = 0; i < arraySize; i++){
            newInputReal[i] = 0.0;
            newInputImag[i] = 0.0;

            newInputReal[ii] = newArray[i];
            i++;
            newInputImag[ii] = newArray[i];
            ii++;
        }

        double[] meow = FFT.fft(newInputReal, newInputImag, false);
//        for(double i : meow){
//            System.out.println(i);
//        }
//
//        if (inputImag[1] == meow[1]){
//            System.out.println();
//            System.out.println("yay");
//        } else{
//            System.out.println("boo");
//        }
    }

    public static double[] toDoubleArray(byte[] byteArr){
        double[] arr=new double[byteArr.length];
        for (int i=0;i<arr.length;i++){
            arr[i]=byteArr[i];
        }
        return arr;
    }
//    public static double[] convert(byte[] in, int idx) {
//        double[] ret;
//        if (idx == 0) {
//            ret = new double[in.length];
//            ret[0] = (double)in[0];
//        }
//        else {
//            ret = convert(in, idx-1);
//            ret[idx] = (double)in[idx];
//        }
//        return ret;
//    }

//    public static double[] toDoubleArray(byte[] byteArray){
//        int times = Double.SIZE / Byte.SIZE;
//        double[] doubles = new double[byteArray.length / times];
//        for(int i = 0; i < doubles.length; i++){
//            doubles[i] = ByteBuffer.wrap(byteArray, i*times, times).getDouble();
//        }
//        return doubles;
//    }

//  public static double[] toDoubleArray(byte[] inData, boolean byteSwap) {
//      int j, upper, lower;
//      int length = inData.length / 8;
//      double[] outData = new double[length];
//      if (!byteSwap)
//          for (int i = 0; i < length; i++) {
//              j = i * 8;
//              upper = (((inData[j] & 0xff) << 24)
//                      + ((inData[j + 1] & 0xff) << 16)
//                      + ((inData[j + 2] & 0xff) << 8) + ((inData[j + 3] & 0xff)));
//              lower = (((inData[j + 4] & 0xff) << 24)
//                      + ((inData[j + 5] & 0xff) << 16)
//                      + ((inData[j + 6] & 0xff) << 8) + ((inData[j + 7] & 0xff)));
//              outData[i] = Double.longBitsToDouble((((long) upper) << 32)
//                      + (lower & 0xffffffffL));
//          }
//      else
//          for (int i = 0; i < length; i++) {
//              j = i * 8;
//              upper = (((inData[j + 7] & 0xff) << 24)
//                      + ((inData[j + 6] & 0xff) << 16)
//                      + ((inData[j + 5] & 0xff) << 8) + ((inData[j + 4] & 0xff)));
//              lower = (((inData[j + 3] & 0xff) << 24)
//                      + ((inData[j + 2] & 0xff) << 16)
//                      + ((inData[j + 1] & 0xff) << 8) + ((inData[j] & 0xff)));
//              outData[i] = Double.longBitsToDouble((((long) upper) << 32)
//                      + (lower & 0xffffffffL));
//          }
//
//      return outData;
//  }
}
