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
        byte result=0;
        int sign = getSign(input);
        int exponent = 0;
        int mantissa = 0;
        int sample = 0;
        if(input<0){sample=-sample;}
        else{sample = input;}
        sample=clip(sample);
        sample = sample+bias;
        exponent=getExponent(sample);
        mantissa = getMantissa(sample);
        result=assembleByte(sign,exponent,mantissa);
        result=invertByte(result);

        return result;
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

    public int getExponent(int sample){

        return getSegment(sample);
    }

    private int getMantissa(int sample){
        return sample >> (getSegment(sample)+3)&0x0F;
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
            if(sample>8191)return 7;
        return 0;
    }

    private int getMostSignificantBit(int sample){
        int msb = 0;
        if(sample==0){return 0;}
        if(sample==255){return 7;}

        if(sample>255){msb=7;sample=sample>>8;}
        while(sample>0){
            sample=sample>>1;
            msb++;
        }
        return msb;
    }

    private byte assembleByte(int sign, int exponent, int mantissa){
        int result = 0;
        result = sign;
        result=result|(exponent<<4);
        result=result|mantissa;
        return (byte)result;
    }

    private byte invertByte(byte b){
        return (byte)~b;
    }

}
