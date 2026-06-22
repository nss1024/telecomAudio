package org.nss1024;

import org.nss1024.customexceptions.ByteBufferContentExceprion;
import org.nss1024.wavProcessor.ParserContext;
import org.nss1024.wavProcessor.ParserState;
import org.nss1024.wavProcessor.ParserUtils;
import org.nss1024.wavProcessor.WavLoader;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws ByteBufferContentExceprion {
        //short sample = -1;
        //PcmuEncoder pcmuEncoder = new PcmuEncoder();
        //byte pcmuByte = pcmuEncoder.encode(sample);
        //System.out.println("Sample: "+sample+"\t"+"PCMU byte: "+pcmuByte+"\t"+"Encoded bits:"+Integer.toBinaryString(pcmuByte).substring(24));
        ParserContext pc = new ParserContext();
        pc.setBytesRemaining(12);
        WavLoader wavLoader = new WavLoader();
        ByteBuffer bb = wavLoader.loadHeader("C:\\dev\\FileStore\\audio\\test.wav");

        String s = new String(bb.array());

        ParserState parserState = ParserState.OK;

        String riff = new String(s.substring(0,4).getBytes());
        System.out.println("Riff: "+riff);
        System.out.println("Size: "+ bb.position(4).getInt());
        System.out.println("Wav: "+ new String(s.substring(8,12).getBytes()));
        System.out.println(ParserUtils.getFirstSubChunk(bb));
        System.out.println(ParserUtils.getChunkSize(bb,16));
        System.out.println(ParserUtils.getChunkId(bb,60));
        System.out.println(ParserUtils.findFmtSubChunk(bb,pc));
        int dataStart = ParserUtils.findDataSubChunk(bb,pc);
        System.out.println(dataStart);
        System.out.println(Arrays.toString(ParserUtils.readData(dataStart+4,bb,pc)));


       int fmtLocation = ParserUtils.findFmtSubChunk(bb,pc);
        System.out.println(fmtLocation);
       int fmtSize = bb.position(fmtLocation+4).getInt();
        System.out.println(fmtSize);
       int fmtPayloadStart = fmtLocation+8;
       pc.setFmt(ParserUtils.readPayload(bb,fmtPayloadStart,fmtSize,pc));

        System.out.println(Arrays.toString(pc.getFmt()));


    }
}