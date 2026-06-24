package org.nss1024.wavProcessor;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class ParserContext {
    private ParserState parserState;
    private int bytesRemaining;
    private final ByteBuffer partialChunk;
    private ByteBuffer partialPayload;
    private boolean havePartialHeader = false;
    private byte[] fmt;
    ArrayList<WavHeader> requiredHeaders = new ArrayList<>();


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

    public byte[] getFmt() {
        return fmt;
    }

    public void setFmt(byte[] fmt) {
        this.fmt = fmt;
    }

    public ByteBuffer getPartialPayload() {
        return partialPayload;
    }

    public void setPartialPayload(ByteBuffer partialPayload) {
        this.partialPayload = partialPayload;
    }

    public ArrayList<WavHeader> getRequiredHeaders() {
        return requiredHeaders;
    }

    public void addRequiredHeaders(WavHeader header) {
        this.requiredHeaders.add(header);
    }
}
