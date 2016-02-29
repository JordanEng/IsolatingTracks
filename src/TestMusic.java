
import java.io.*;
import javax.sound.sampled.*;


public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("doorbell.wav"));
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
}
