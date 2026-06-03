package org.nss1024.wavProcessor;
/* standard WAV file is entirely little-endian.
 *
 */


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WavLoader {

    private WavData wavData;

    public WavLoader (){

    }
    //C:\dev\FileStore\audio\test.wav
    public ByteBuffer loadHeader(String fileName){
        Path path = Paths.get(fileName);
        File file = new File(path.toUri());
        System.out.println(file.getAbsolutePath());
        byte[] data = new byte[4096];

        try(DataInputStream in = new DataInputStream(new FileInputStream(file));){
            in.readFully(data);
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        return ByteBuffer.wrap(data.clone()).order(ByteOrder.LITTLE_ENDIAN);
    }

    public WavData getWavData() {
        return wavData;
    }

}
