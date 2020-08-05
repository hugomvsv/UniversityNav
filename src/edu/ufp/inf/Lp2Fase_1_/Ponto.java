package edu.ufp.inf.Lp2Fase_1_;

import java.io.Serializable;

public class Ponto implements Serializable {

  //Atributos
  private Double x;
  private Double y;
  private Double z;

  //Constructor
  public Ponto(Double x, Double y,Double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  //Gets and Sets
  public Double getX() {
    return x;
  }
  public void setX(Double x) {
    this.x = x;
  }
  public Double getY() {
    return y;
  }
  public void setY(Double y) {
    this.y = y;
  }
  public Double getZ() {
    return z;
  }
  public void setZ(Double z) {
    this.z = z;
  }

  public Double DistanceX(Ponto p)
  {
    return Math.abs(this.getX()-p.getX());
  }
  public Double DistanceY(Ponto p)
  {
    return  Math.abs(this.getY()-p.getY());
  }
  public Double DistanceZ(Ponto p)
  {
    return Math.abs(this.getZ()-p.getZ());
  }
  public double getDistanceFrom(Ponto p)
  {
    if(DistanceZ(p)!=0.0)
    {
      return Math.round(Math.sqrt(Math.pow(DistanceZ(p),2)+ Math.pow(DistanceY(p),2)));
    }
    else
    {
      return Math.round(Math.sqrt(Math.pow(DistanceX(p),2)+ Math.pow(DistanceY(p),2)));
    }
  }

  @Override
  public String toString(){
    return("X="+this.getX()+"\tY="+getY());
  }

}