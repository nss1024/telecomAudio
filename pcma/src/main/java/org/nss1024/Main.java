package org.nss1024;

public class Main {
    public static void main(String[] args) {
        short sample = -1;
        PcmuEncoder pcmuEncoder = new PcmuEncoder();

        byte pcmuByte = pcmuEncoder.encode(sample);
        System.out.println("Sample: "+sample+"\t"+"PCMU byte: "+pcmuByte+"\t"+"Encoded bits:"+Integer.toBinaryString(pcmuByte).substring(24));

    }
}