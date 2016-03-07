
import java.io.*;
import javax.sound.sampled.*;
import java.nio.ByteBuffer;

public class TestMusic {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        ReadingWAV audioRead = new ReadingWAV(new File("magic.wav"));
        byte[] timeFrequencyArrayX = audioRead.toByteArray();

//        for(byte i: timeFrequencyArrayX){
//            System.out.println(i);
//        }

        double[] doubles = toDoubleArray(timeFrequencyArrayX);
//          for(double i: doubles){
//              System.out.println(i);
//          }

        double[] forwardTransformation = forwardFFT(doubles);
//          for (double i: forwardTransformation ){
//              System.out.println(i);
//          }

        double[] backwardTransformation = backwardFFT(forwardTransformation);
//          for (double i : backwardTransformation){
//              System.out.println(i);
//          }

        byte[] bytes = toByteArray(forwardTransformation);
//          for(byte i: bytes){
//              System.out.println(i);
//          }

        //AudioInputStream endStream = audioRead.writeBytesBackToStream(bytes);

        outWavFile("meow", audioRead.format);
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

    public static void outWavFile(String exportFileName, AudioFormat mainFormat)throws java.io.IOException {
        {
            File f = new File(exportFileName + ".wav");
            File f2 = new File(exportFileName);
            long l = f.length();
            FileInputStream fi = new FileInputStream(f);
            AudioInputStream ai = new AudioInputStream(fi,mainFormat,l/4);
            AudioSystem.write(ai, AudioFileFormat.Type.WAVE, f2);
            fi.close();
            f.delete();
        }
    }

}
