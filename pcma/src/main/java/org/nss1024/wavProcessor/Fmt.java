package org.nss1024.wavProcessor;

import org.nss1024.customexceptions.WavFmtInvalidException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Fmt implements WavHeader{
    private int fmtLocation;
    private int fmtSize;
    private byte[] fmtPayload;

    private String subchunkId;//12-15 "fmt" Format chunk marker. Includes trailing null
    private int subchunkSize; // 16-19 Length of format data
    private int audioFormat; // 20-21 Type of format (1 is PCM) - 2 byte integer
    private int numChannels; // 22-23 Number of Channels - 2 byte integer
    private int sampleRate; // 24-27 Sample Rate - 32 bit integer. Common values are 44100 (CD), 48000 (DAT). Sample Rate = Number of Samples per second, or Hertz.
    private int byteRate; // 28-31 (Sample Rate * BitsPerSample * Channels) / 8.
    private int blockAlign; // 32-33 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo
    private int bitsPerSample; // 34-35 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo

    public Fmt(){}

    @Override
    public String headerName(){
        return "fmt ";
    }

    @Override
    public void setSubchunkLocation(int location) {
        this.fmtLocation = location;
    }

    @Override
    public void setSubchunkId(ByteBuffer bb, int subChunkStartPosition) {
        byte[] subchunkId = new byte[4];
        bb.position(subChunkStartPosition);
        bb.get(subchunkId);
        this.subchunkId = new String(subchunkId, StandardCharsets.UTF_8);
    }

    @Override
    public void setSubchunkSize(ByteBuffer bb, int subChunkStartPosition) {
        bb.position(subChunkStartPosition+4);//skip the subchunk Id

        this.subchunkSize = bb.getInt();
    }


    public void setFmtPayload(byte[] fmtPayload) {
        this.fmtPayload = fmtPayload;
    }

    public String getSubchunkId() {
        return subchunkId;
    }



    public int getSubchunkSize() {
        return subchunkSize;
    }

    public void setValues(){
        if(fmtPayload!=null){
            ByteBuffer bb = ByteBuffer.wrap(fmtPayload).order(ByteOrder.LITTLE_ENDIAN);
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
