package org.nss1024.wavProcessor;
//wav file data for 16 bit encoded audio
public class Wav {
    //chunk desrciptor
    private int chunkId; //0-3 - "riff" Marks the file as a riff file. Characters are each 1 byte long.
    private int chunkSize;//4-7 Size of the overall file - 8 bytes, in bytes (32-bit integer).
    private int format;//8-11 "WAVE" File Type Header. Always equals “WAVE”.
    //Format of sound information in data subchunk
    Fmt fmt;
    //data subchunk
    WavData wavData;

    Wav(){    }

    public int getChunkId() {
        return chunkId;
    }

    public void setChunkId(int chunkId) {
        this.chunkId = chunkId;
    }
}
