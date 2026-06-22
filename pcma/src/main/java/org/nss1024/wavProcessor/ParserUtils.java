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

    public static int findFmtSubChunk(ByteBuffer bb,ParserContext p){
        return findSubChunk(bb,"fmt ",p);
    }

    public static int findDataSubChunk(ByteBuffer bb, ParserContext p){
        return findSubChunk(bb,"data", p);
    }

    public static int findSubChunk(ByteBuffer bb,String s,ParserContext context){
        byte[] chunkId = new byte[4];
        if(context.getBytesRemaining()>0 && !context.isHavePartialHeader()){
            bb.position(context.getBytesRemaining());
        }

        while(true){
            //check if we can get a chunk id from the remaining data
            if(bb.position()+4>bb.limit()){
                //calculate remaining data to read and process what we have
                int remainingBytes=bb.limit()-bb.position();
                context.setBytesRemaining(4-remainingBytes);
                //store what bytes are available and move on
                for(int j =0;j<remainingBytes;j++){
                    context.addToPartialChunk(bb.get());
                }
                context.setHavePartialHeader(true);
                bb.compact();
                context.setParserState(ParserState.WAIT_FOR_DATA);
                return -1;
            }
            //get 4 byte chunk ID
            bb.get(chunkId);
            context.setParserState(ParserState.READING_CHUNK_HEADER);
            if(new String(chunkId, StandardCharsets.US_ASCII).equals(s)){
                return bb.position()-4;
            }

            context.setParserState(ParserState.SEARCHING_FOR_CHUNK);
            //check if we can get the size of the retrieved subchunk, if not, return -1
            if(bb.position()+4>=bb.limit()){
                bb.compact();
                context.setParserState(ParserState.WAIT_FOR_DATA);
                return -1;
            }
            //get subchunk size
            int payloadSize = bb.getInt();
            //if current position + size of data
            //is greater than the available data, compact and return
            if((bb.position()+payloadSize)>=bb.limit()){
                //calculate number of remaining bytes after we move to end of bb
                context.setBytesRemaining(payloadSize-(bb.limit()-bb.position()));
                bb.position(bb.limit());
                bb.compact();
                context.setParserState(ParserState.WAIT_FOR_DATA);
                return -1;
            }
            //jump to next location
            bb.position(bb.position()+payloadSize);
            context.setParserState(ParserState.SKIPPING_PAYLOAD);
        }
    }

    public static byte[] readData(int payloadStart, ByteBuffer bb, ParserContext context){
        byte[] result = new byte[bb.limit()-payloadStart];
        bb.get(result);
        context.setParserState(ParserState.READING_DATA);
        return result;
    }

    public static byte[] readPayload(ByteBuffer bb, int startPosition,int size, ParserContext context){
        //allocate array based on data that can be read
        byte[] payload;
        if((startPosition+size)>bb.limit()){
            payload = new byte[bb.limit()-startPosition];
            context.setBytesRemaining(bb.limit()-startPosition);
            bb.position(startPosition);
            bb.get(payload);
            context.setPartialPayload(ByteBuffer.wrap(payload));
        }else{
            payload = new byte[size];
            bb.position(startPosition);
            bb.get(payload);
        }
        return payload;
    }


}
