package org.nss1024.wavProcessor;

import org.nss1024.customexceptions.ByteBufferContentExceprion;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WavData implements WavHeader{
    int dataSubchunkLocation;
    String dataSubchunkId;
    int dataPayloadSize;
    ParserContext parserContext;

    private static final Logger logger = Logger.getLogger(WavData.class.getName());

    public WavData(){}


    @Override
    public String headerName() {
        return "data";
    }

    @Override
    public void setSubchunkLocation(int location) {
        this.dataSubchunkLocation=location;
    }

    @Override
    public void setSubchunkId(ByteBuffer bb, int subChunkStartPosition) {
        try {
            this.dataSubchunkId = ParserUtils.getChunkId(bb, subChunkStartPosition);
        }catch (ByteBufferContentExceprion e){
            logger.log(Level.WARNING,"Failed to get subchunk ID!");
        }
    }

    @Override
    public void setSubchunkSize(ByteBuffer bb, int subChunkStartPosition) {
        int offset = 4;
        this.dataPayloadSize = ParserUtils.getChunkSize(bb,subChunkStartPosition+offset);
    }

    public void setParserContext(ParserContext parserContext) {
        this.parserContext = parserContext;
    }

    @Override
    public void processSubchunk(ByteBuffer bb, int subchunkStart) {
        parserContext.setParserState(ParserState.READING_DATA);
    }
}


