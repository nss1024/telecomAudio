package org.nss1024;

public class Main {
    public static void main(String[] args) {
        short sample = 1000;

        PcmuEncoder pcmuEncoder = new PcmuEncoder();
        byte pcmuByte = pcmuEncoder.encode(sample);
        System.out.println("PCMU byte: "+pcmuByte);
        System.out.println("Encoded bits bits :"+Integer.toBinaryString(pcmuByte));
    }
}