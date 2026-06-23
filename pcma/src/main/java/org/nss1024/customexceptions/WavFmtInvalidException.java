package org.nss1024.customexceptions;

import org.nss1024.wavProcessor.WavData;

public class WavFmtInvalidException extends Exception{

    public WavFmtInvalidException(String message){
        super(message);
    }

}
