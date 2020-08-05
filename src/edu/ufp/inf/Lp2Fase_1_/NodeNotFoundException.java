package edu.ufp.inf.Lp2Fase_1_;

import java.io.Serializable;

public class NodeNotFoundException extends Exception implements Serializable {
    public NodeNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}
