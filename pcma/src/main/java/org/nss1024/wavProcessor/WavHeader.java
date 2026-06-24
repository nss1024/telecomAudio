package org.nss1024.wavProcessor;

import java.nio.ByteBuffer;

public interface WavHeader {

    String headerName();

    void setSubchunkLocation(int location);

    void setSubchunkId(ByteBuffer bb,int subChunkStartPosition);

    void setSubchunkSize(ByteBuffer bb, int subChunkStartPosition);

    void setSubchunkValues(ByteBuffer bb);

}
