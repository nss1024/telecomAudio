package org.nss1024.wavProcessor;

public enum ParserState {

    OK,
    WAIT_FOR_DATA,
    SEARCHING_FOR_CHUNK,
    READING_CHUNK_HEADER,
    SKIPPING_PAYLOAD,
    READING_DATA;

    public void getP() {
    }
}
