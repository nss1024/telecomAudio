package org.nss1024.wavProcessor;

import java.nio.ByteBuffer;

public class ParserContext {
    private ParserState parserState;
    private int bytesRemaining;
    private final ByteBuffer partialChunk;
    private boolean havePartialHeader = false;


    public ParserContext(){
        parserState=ParserState.OK;
        bytesRemaining=0;
        partialChunk = ByteBuffer.allocate(3);

    }

    public int getBytesRemaining() {
        return bytesRemaining;
    }

    public void setBytesRemaining(int bytesRemaining) {
        this.bytesRemaining = bytesRemaining;
    }

    public ParserState getParserState() {
        return parserState;
    }

    public void setParserState(ParserState parserState) {
        this.parserState = parserState;
    }

    public ByteBuffer getPartialChunk() {
        return partialChunk;
    }

    public void addToPartialChunk(byte b){
        partialChunk.put(b);
    }

    public boolean isHavePartialHeader() {
        return havePartialHeader;
    }

    public void setHavePartialHeader(boolean havePartialHeader) {
        this.havePartialHeader = havePartialHeader;
    }
}
