package org.nss1024.wavProcessor;

import org.nss1024.customexceptions.ByteBufferContentExceprion;
import org.nss1024.customexceptions.UnsupportedFileException;


import java.nio.ByteBuffer;

public class ParserUtils {

    private ParserUtils(){}

    public static String getRiff(ByteBuffer bb) throws UnsupportedFileException {
        byte[] r = new byte[4];
        r[0]=bb.get();
        r[1]=bb.get();
        r[2]=bb.get();
        r[3]=bb.get();

        String result = new String(r);
        if(result.equalsIgnoreCase("riff")){
            return result;
        }else{
            throw new UnsupportedFileException("Unsupported file "+result+" cao only process RIFF");
        }

    }

    public static int getFileSize(ByteBuffer bb){
        bb.position(4);
        return bb.getInt();
    }

    public static String getFileType (ByteBuffer bb) throws UnsupportedFileException, ByteBufferContentExceprion {
        if(bb.limit()<12){
            throw new ByteBufferContentExceprion("Not enough data in byte buffer! ByteBuffer limit: "+bb.limit());
        }
        bb.position(8);
        byte[] r = new byte[4];
        r[0]=bb.get();
        r[1]=bb.get();
        r[2]=bb.get();
        r[3]=bb.get();

        String result = new String(r);
        if(result.equalsIgnoreCase("WAVE")){return result;}
        else{
            throw new UnsupportedFileException("Unsupported file format "+result+" ony WAVE supported!");
        }
    }

    public static int getChunkSize (ByteBuffer bb, int position){
        if(bb.limit()<(position+4)){return -1;}
        bb.position(position);
        return bb.getInt();
    }



    public static String getChunkId(ByteBuffer bb, int position)throws ByteBufferContentExceprion{
        if(bb.limit()<(position+4)){throw new ByteBufferContentExceprion("Data in byte buffer less than position and retrievablt data!");}
        byte[] r = new byte[4];
        bb.position(position);
        r[0]=bb.get();
        r[1]=bb.get();
        r[2]=bb.get();
        r[3]=bb.get();
        return new String(r);
    }

    /*Assuming we have a RIFF structure and the correct WAVE file type, this should be at position 12;
    * RIFF bytes 0-3
    * Size bytes 4-7
    * WAVE bytes 8-11
    * first chunk ID 12-15
     */
    public static String  getFirstSubChunk(ByteBuffer bb) throws ByteBufferContentExceprion {
        return getChunkId(bb,12);
    }

}
