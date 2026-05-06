package org.nss1024;

public class PcmuDecoder {
    private final int bias = 0x84;
    private final int offset = 8;


    public short decodeSample(byte b){
        int intSample = (~b) & 0xFF;
        int sign = getSign(intSample);
        System.out.println("Sign: "+sign);
        int exponent = getExponent(intSample);
        System.out.println("Exponent: "+exponent);
        int mantissa = getMantissa(intSample);
        System.out.println("Mantissa: "+mantissa);

        int value = ((mantissa << 4) + offset) << exponent;
        value=value-bias;
        if(sign==1){value=-value;}
        return (short)value;
    }



    private int getSign(int b){
        int result = b & 0x80;
        result = result >> 7;
        return result;
    }

    private int getExponent(int b){
        int result = b & 0x70;
        result = result >> 4;
        return result;
    }

    private int getMantissa(int b){
        return b & 0x0F;
    }

}
