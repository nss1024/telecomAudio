package org.nss1024.wavProcessor;

import org.nss1024.customexceptions.ByteBufferContentExceprion;
import org.nss1024.customexceptions.WavFmtInvalidException;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Fmt implements WavHeader{
    private int fmtLocation;
    private byte[] fmtPayload;

    private String subchunkId;//12-15 "fmt" Format chunk marker. Includes trailing null
    private int subchunkSize; // 16-19 Length of format data
    private int audioFormat; // 20-21 Type of format (1 is PCM) - 2 byte integer
    private int numChannels; // 22-23 Number of Channels - 2 byte integer
    private int sampleRate; // 24-27 Sample Rate - 32 bit integer. Common values are 44100 (CD), 48000 (DAT). Sample Rate = Number of Samples per second, or Hertz.
    private int byteRate; // 28-31 (Sample Rate * BitsPerSample * Channels) / 8.
    private int blockAlign; // 32-33 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo
    private int bitsPerSample; // 34-35 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo

    private static final Logger logger = Logger.getLogger(Fmt.class.getName());

    public Fmt(){}

    @Override
    public String headerName(){
        return "fmt ";
    }

    @Override
    public void setSubchunkLocation(int location) {
        this.fmtLocation = location;
    }

    public int getFmtLocation() {
        return fmtLocation;
    }

    @Override
    public void setSubchunkId(ByteBuffer bb, int subChunkStartPosition) {
        try {
            this.subchunkId = ParserUtils.getChunkId(bb, subChunkStartPosition);
        }catch (ByteBufferContentExceprion e){
            logger.log(Level.WARNING,"Failed to get subchunk ID!");
        }
    }

    @Override
    public void setSubchunkSize(ByteBuffer bb, int subChunkStartPosition) {
        int offset = 4;
        this.subchunkSize = ParserUtils.getChunkSize(bb,subChunkStartPosition+offset);

    }

    public void setFmtPayload(ByteBuffer bb, ParserContext c) {
        this.fmtPayload = ParserUtils.readPayload(bb,fmtLocation+8,subchunkSize,c);
    }

    public String getSubchunkId() {
        return subchunkId;
    }

    public int getSubchunkSize() {
        return subchunkSize;
    }

    @Override
    public void processSubchunk(ByteBuffer bb, int subchunkStart) {
        setSubchunkValues(bb,subchunkStart);
    }

    private void setSubchunkValues(ByteBuffer bb, int subchunkStart){
        if(fmtPayload!=null){
            bb.position(subchunkStart+8);
            audioFormat = Short.toUnsignedInt(bb.getShort());
            numChannels = bb.getShort();
            sampleRate = bb.getInt();
            byteRate = bb.getInt();
            blockAlign = bb.getShort();
            bitsPerSample = bb.getShort();

        }
    }

    private void validate() throws WavFmtInvalidException{
        //TODO validate fields that can be calculated using other fields
    }

    @Override
    public String toString() {
        return "Fmt{" +
                "subchunkId=" + subchunkId + "\n"+
                "subchunkSize=" + subchunkSize + "\n"+
                "audioFormat=" + audioFormat + "\n"+
                "numChannels=" + numChannels + "\n"+
                "sampleRate=" + sampleRate + "\n"+
                "byteRate=" + byteRate + "\n"+
                "blockAlign=" + blockAlign + "\n"+
                "bitsPerSample=" + bitsPerSample + "\n"+
                '}';
    }
}
