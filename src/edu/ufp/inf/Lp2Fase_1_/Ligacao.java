package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.DirectedEdge;

import java.io.Serializable;

public class Ligacao extends DirectedEdge implements Serializable {
    private final Double tempo;

    public Ligacao(int v, int w, double weight) {
        super(v, w, weight);
        this.tempo = weight *1.5;
    }

    @Override
    public String toString() {
        return "\n"+super.from() + "->" + super.to() +
                " - [Distance: " + super.weight() + " , Time: " + this.tempo + "]\n";
    }
    @Override
    public double weight() {
        if(Infra.time)
            return this.tempo;
        return super.weight();
    }

    public double gettime()
    {
        return this.tempo;
    }

}
