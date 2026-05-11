/*PCMU encoding steps:
 * 1 Get sign
 * 2 Take absolute value
 * 3 Clip max value
 * 4 Add bias
 * 5 Find segment / exponent
 * 6 Extract mantissa
 * 7 Assemble byte
 * 8 Invert bits
 */

package org.nss1024;

public class PcmuEncoder {
    private static final int bias = 132;
    private static final int MAX_VALUE = 32635;

    public byte encode(short input){
        int sign = getSign(input);
        int sample = Math.abs(input);
        sample=clip(sample);
        sample = sample+bias;


        return 0;
    }

    private int getSign(short input){
        return (input >> 8)&0x80;
    }

    private int clip(int input){
        if(input>MAX_VALUE){
            return MAX_VALUE;
        }
        return input;
    }

    private int getExponent(int sample){
        int msb=0;
        int result = getSegment(sample);
        result=msb-result;
        return 0;
    }

    public int getMantissa(){
        return 0;
    }

    private int getSegment(int sample){
            if((sample>=32)&&(sample<=63))return 0;
            if((sample>=64)&&(sample<=127)) return 1;
            if((sample>=128)&&(sample<=255)) return 2;
            if((sample>=256)&&(sample<=511)) return 3;
            if((sample>=512)&&(sample<=1023))return 4;
            if((sample>=1024)&&(sample<=2047))return 5;
            if((sample>=2048)&&(sample<=4095))return 6;
            if((sample>=4096)&&(sample<=8191))return 7;
        return 0;
    }

    public int getMostSignificantBit(int sample){

        return 0;
    }

}
