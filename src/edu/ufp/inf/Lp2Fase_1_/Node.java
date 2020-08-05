package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class Node implements Comparable, Serializable

{
    private String id; // vai ser o nome do objecto em si mais o piso em que está provavelmente
    private Ponto ponto;
    private String tipo;
    private Integer piso;
    private Color color;
    private Boolean indoor; //verdadeiro está indoor false outdoor
    private Boolean outdoor;
    private Boolean state; //verdadeiro está ativo falso inativo
    private SeparateChainingHashST<Node,Double> pesos =new SeparateChainingHashST<>(); //ST que irá conter os peso atribuido aos nó ligado a  este nó
    //vamos ter 4 tipos sala/topo escadas/botom escadas/acesso exterior/all/

    /**
     * contrutor
     * @param id id vai ser o nome da sala hall corredor + node
     * @param ponto vai ser o ponto onde está
     * @param tipo se é sala escada hall ... etc
     */
    public Node(String id, Ponto ponto, String tipo) {
        this.id = id;
        this.ponto = ponto;
        this.tipo = tipo;
        this.state=true;
        this.indoor=true;
        this.outdoor=false;
        setColorToNode(tipo);
        //todo: acrescentar mais tipos e atribuir mais cores
    }

    public void setColorToNode(String tipo)
    {
        if(tipo.compareTo("SALA")==0) //caso seja do tipo sala vai ter a cor azul claro
        {
            this.color=Color.CYAN;


        }
        if(tipo.compareTo("HALL")==0) //caso seja do tipo  hall vai ter cor amarela
        {
            this.color=Color.YELLOW;

        }
        if(tipo.compareTo("ENTRADA")==0)//caso seja do tipo entrada vai ter cor laranja
        {
            this.color=Color.ORANGE;
           this.outdoor=true;
        }
        if(tipo.compareTo("ESCADABOT")==0 || this.tipo.compareTo("ESCADATOPO")==0)
        {
            this.color=Color.GREEN;

        }
        if(tipo.compareTo("PP")==0)
        {
            this.color=Color.MAGENTA;

        }
        if(tipo.compareTo("ACESS")==0)
        {
            this.color=Color.RED;
            this.outdoor=true;

        }
    }

    public Boolean getIndoor() {
        return indoor;
    }

    public void setIndoor(Boolean indoor) {
        this.indoor = indoor;
    }

    public Boolean getOutdoor() {
        return outdoor;
    }

    public void setOutdoor(Boolean outdoor) {
        this.outdoor = outdoor;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public SeparateChainingHashST<Node, Double> getPesos() {
        return pesos;
    }

    public void setPesos(SeparateChainingHashST<Node, Double> pesos) {
        this.pesos = pesos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ponto getPonto() {
        return ponto;
    }

    public void setPonto(Ponto ponto) {
        this.ponto = ponto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getPiso() {
        return this.getPonto().getZ();
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", ponto=" + ponto +
                ", tipo='" + tipo + '\'' +
                ", piso=" + getPiso() +
                ", color=" + color +
                ", pesos=" + pesos +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this.getId().compareTo(((Node) o).getId())==0)
            return 0;
        return 1;
    }
}
