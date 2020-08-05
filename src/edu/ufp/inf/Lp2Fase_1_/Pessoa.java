package edu.ufp.inf.Lp2Fase_1_;


import java.io.Serializable;

public class Pessoa implements Serializable {

  //Atributos
  private String nome;
  private String cc;

  //Constructor
  public Pessoa(String nome, String cc) {
    this.nome = nome;
    this.cc = cc;
  }


  //gets and set
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCc() {
    return cc;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }
}