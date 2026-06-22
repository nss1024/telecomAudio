package org.nss1024.wavProcessor;

public class FMT {
    private int fmtLocation;
    private int fmtSize;
    private byte[] fmtPayload;

    private int subchunkId;//12-15 "fmt" Format chunk marker. Includes trailing null
    private int subchunkSize; // 16-19 Length of format data
    private int audioFormat; // 20-21 Type of format (1 is PCM) - 2 byte integer
    private int numChannels; // 22-23 Number of Channels - 2 byte integer
    private int sampleRate; // 24-27 Sample Rate - 32 bit integer. Common values are 44100 (CD), 48000 (DAT). Sample Rate = Number of Samples per second, or Hertz.
    private int byteRate; // 28-31 (Sample Rate * BitsPerSample * Channels) / 8.
    private int blockAlign; // 32-33 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo
    private int bitsPerSample; // 34-35 (BitsPerSample * Channels) / 8.1 - 8 bit mono2 - 8 bit stereo/16 bit mono4 - 16 bit stereo

    public FMT(){}

    public int getFmtLocation() {
        return fmtLocation;
    }

    public void setFmtLocation(int fmtLocation) {
        this.fmtLocation = fmtLocation;
    }
}
