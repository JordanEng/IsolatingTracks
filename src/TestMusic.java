
import java.io.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        ReadingWAV audioRead = new ReadingWAV(new File("done.wav"));
        byte[] timeFrequencyArrayX = audioRead.toByteArray();
//
////        for(byte i: timeFrequencyArrayX){
////            System.out.println(i);
////        }
//
//        double[] doubles = toDoubleArray(timeFrequencyArrayX);
////          for(double i: doubles){
////              System.out.println(i);
////          }
//
//        double[] forwardTransformation = forwardFFT(doubles);
////          for (double i: forwardTransformation ){
////              System.out.println(i);
////          }
//
//        double[] backwardTransformation = backwardFFT(forwardTransformation);
////          for (double i : backwardTransformation){
////              System.out.println(i);
////          }
//
//        byte[] bytes = toByteArray(forwardTransformation);
////          for(byte i: bytes){
////              System.out.println(i);
////          }

//        byte[] bytes = new byte[32768];
//
//        Scanner timeSignal = new Scanner(new File("signal.txt"));
//        int i = 0;
//
//        while(timeSignal.hasNextDouble()){
//            bytes[i] = (byte)timeSignal.nextDouble();
//            timeSignal.nextDouble();
//            i++;
//        }

        outWavFile(timeFrequencyArrayX);
}
    public static double[] forwardFFT(double[] doubles) throws FileNotFoundException {
        int arraySize = 32768;
        double[] inputImag = new double[arraySize];
        double[] inputReal = new double[arraySize];

        for (int i = 0; i < arraySize; i++) {
            inputReal[i] = 0.0;
            inputImag[i] = 0.0;
        }

        System.arraycopy(doubles, 0, inputImag, 0, doubles.length);

//        for (double i: inputImag){
//            System.out.println(i);
//        }

        return FFT.fft(inputReal, inputImag, true);
    }

    public static double[] backwardFFT(double[] doubles){
        int arraySize = 32768;

        // Test code below to reverse the transformation back to the original.

        double[] newInputReal = new double[arraySize];
        double[] newInputImag = new double[arraySize];
        int ii = 0;
        for (int i = 0; i < arraySize; i++){
            newInputReal[i] = 0.0;
            newInputImag[i] = 0.0;

            newInputReal[ii] = doubles[i];
            i++;
            newInputImag[ii] = doubles[i];
            ii++;
        }

        return FFT.fft(newInputReal, newInputImag, false);
    }

    public static byte[] toByteArray(double[] doubleArray){
        int times = Double.SIZE / Byte.SIZE;
        byte[] bytes = new byte[doubleArray.length * times];
        for(int i=0;i<doubleArray.length;i++){
            ByteBuffer.wrap(bytes, i*times, times).putDouble(doubleArray[i]);
        }
        return bytes;
    }

    public static double[] toDoubleArray(byte[] byteArray){
        double[] doubles = new double[byteArray.length / 3];
        for (int i = 0, j = 0; i != doubles.length; ++i, j += 3) {
            doubles[i] = (double)( (byteArray[j] & 0xff) |
                    ((byteArray[j+1] & 0xff) <<  8) |
                    ( byteArray[j+2]         << 16));
        }
        return doubles;
    }

    public static void outWavFile(byte[] totalByteArray)throws java.io.IOException {
        {
            BufferedOutputStream bos = null;
            try {
                //create an object of FileOutputStream
                FileOutputStream fos = new FileOutputStream(new File("Transformed Output.txt"));

                //create an object of BufferedOutputStream
                bos = new BufferedOutputStream(fos);

/*
* To write byte array to file use,
* public void write(byte[] b) method of BufferedOutputStream
* class.
*/
                System.out.println("Writing byte array to file");

//                for(byte i: totalByteArray){
//                    bos.write(i);
//                }

                bos.write(totalByteArray);

                System.out.println("File written");
            } catch (FileNotFoundException fnfe) {
                System.out.println("Specified file not found" + fnfe);
            } catch (IOException ioe) {
                System.out.println("Error while writing file" + ioe);
            } finally {
                if (bos != null) {
                    try {

                        //flush the BufferedOutputStream
                        bos.flush();

                        //close the BufferedOutputStream
                        bos.close();

                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }
}
