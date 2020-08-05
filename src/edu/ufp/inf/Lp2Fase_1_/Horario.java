package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;

public class Horario implements Serializable
{

  //Atributos
  private Data incio;
  private Data fim;
  private SeparateChainingHashST<String,Turma> turmas=new SeparateChainingHashST<>();             //turmas que têm aulas neste horario
  private SeparateChainingHashST<String,Professor> professores=new SeparateChainingHashST<>();   //professore com horarios de atendimento neste horario
  private SeparateChainingHashST<Integer,Sala> salas=new SeparateChainingHashST<>();         //salas que estão a ser utilizadas neste horario

  //Constructor
  public Horario(Data incio, Data fim)
  {
    this.incio = incio;
    this.fim = fim;
  }

  //Gets and Sets
  public Data getIncio() {
    return incio;
  }

  public void setIncio(Data incio) {
    this.incio = incio;
  }
  public Data getFim() {
    return fim;
  }
  public void setFim(Data fim) {
    this.fim = fim;
  }

  public String getDataInicio()
  {
    return this.getIncio().paraString();
  }
  public String getDataFim()
  {
    return this.getFim().paraString();
  }

  public SeparateChainingHashST<String, Turma> getTurmas() {
    return turmas;
  }

  public SeparateChainingHashST<String, Professor> getProfessores() {
    return professores;
  }

  public SeparateChainingHashST<Integer, Sala> getSalas() {
    return salas;
  }

  public String paraStringtxt(){
    return (this.getIncio().toString()+"\n"+this.getFim()+"\n->\n");
  }


  @Override
  public String toString() {
    String dia="a";

    switch(this.getIncio().getDia_semana()) {
      case 2:
        dia="Segunda-feira";
        break;
      case 3:
        dia="Terça-feira";
        break;
      case 4:
        dia="Quarta-feira";
        break;
      case 5:
        dia="Quinta-feira";
        break;
      case 6:
        dia="Sexta-feira";
        break;
    }
    String hora_inicio=String.valueOf(this.getIncio().getHora())+":"+String.valueOf(this.getIncio().getMinuto());
    String hora_fim=String.valueOf(this.getFim().getHora())+":"+String.valueOf(this.getFim().getMinuto());
    return (dia+"\t"+ hora_inicio+"-"+hora_fim);
  }

  public int compareTo(Horario h)
  {
    if(this.getIncio().compareTo(h.getIncio())>0 && this.getIncio().compareTo(h.getFim()) <0)//Horario inicial esteja no meio do horario pretendido
    {
      return -1; //a sala não vai ser usada pois tem hum horario usado dentro do horario pretendido
    }

    if(this.getFim().compareTo(h.getIncio())>0 && this.getFim().compareTo(h.getFim()) <0)//Horario final esteja no meio do horario pretendido
    {
      return -1; //a sala não vai ser usada pois tem hum horario usado dentro do horario pretendido
    }
    if(this.getFim().compareTo(h.getFim())==0 && this.getIncio().compareTo(h.getIncio())==0) //caso sejam iguais retorna 0
    {
      return 0; //iguais logo não pode ser
    }
    return  1;
  }
}