package org.nss1024.wavProcessor;

import org.nss1024.customexceptions.ByteBufferContentExceprion;
import org.nss1024.customexceptions.UnsupportedFileException;


import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ParserUtils {

    private ParserUtils(){}

    public static String getRiff(ByteBuffer bb) throws UnsupportedFileException {
        byte[] r = new byte[4];
       bb.get(r);
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
        bb.get(r);
        String result = new String(r);
        if(result.equalsIgnoreCase("WAVE")){return result;}
        else{
            throw new UnsupportedFileException("Unsupported file format "+result+" ony WAVE supported!");
        }
    }

    public static int getChunkSize (ByteBuffer bb, int position){
        if((position+4)>bb.limit()){return -1;}

        return bb.getInt(position);
    }



    public static String getChunkId(ByteBuffer bb, int position)throws ByteBufferContentExceprion{
        if(bb.limit()<(position+4)){throw new ByteBufferContentExceprion("Data in byte buffer less than position and retrievablt data!");}
        byte[] r = new byte[4];
        bb.position(position);
        bb.get(r);
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

    public static int findFmt(ByteBuffer bb){
        byte[] chunkId = new byte[4];
        bb.position(12);
        while(true){
            bb.get(chunkId);
            if(new String(chunkId, StandardCharsets.US_ASCII).equals("fmt ")){
                return bb.position()-4;
            }
            if(bb.position()+4>=bb.limit()){return -1;}//can I read an int
            int p = bb.getInt();
            if(bb.position()+p>=bb.limit()){return -1;}//can I jump to next location
            bb.position(bb.position()+p);
        }
    }

    public static int findData(){
        return 0;
    }

    public static int findSubChunk(ByteBuffer bb,String s){
        byte[] chunkId = new byte[4];
        bb.position(12);
        while(true){
            bb.get(chunkId);
            if(new String(chunkId, StandardCharsets.US_ASCII).equals(s)){
                return bb.position()-4;
            }
            if(bb.position()+4>=bb.limit()){return -1;}//can I read an int
            int p = bb.getInt();
            if(bb.position()+p>=bb.limit()){return -1;}//can I jump to next location
            bb.position(bb.position()+p);
        }
    }

    public static int getData(byte[] dst){
        byte [] result = null;

        return 0;
    }


}
