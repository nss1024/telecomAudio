package org.nss1024;

public class Main {
    public static void main(String[] args) {
        byte sample = -1;
        PcmuDecoder pcmaDecoder = new PcmuDecoder();
        System.out.println("Input: "+sample);
        short s =  pcmaDecoder.decodeSample(sample);
        System.out.println("Sample: "+s);
    }
}