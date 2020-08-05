package edu.ufp.inf.Lp2Fase_1_;

import java.io.Serializable;

public class PisoIndisponivelException extends Exception implements Serializable {
    public PisoIndisponivelException(String errorMessage)
    {
        super(errorMessage);
    }
}
