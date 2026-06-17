package org.nss1024.wavProcessor;

public class ParserContext {
    private ParserState parserState;
    private int bytesRemaining;

    public ParserContext(){}

    public ParserState getParserState() {
        return parserState;
    }

    public void setParserState(ParserState parserState) {
        this.parserState = parserState;
    }
}
