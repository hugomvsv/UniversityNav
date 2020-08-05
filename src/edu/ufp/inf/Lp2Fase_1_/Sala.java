package edu.ufp.inf.Lp2Fase_1_;


import edu.princeton.cs.algs4.RedBlackBST;

import java.io.Serializable;
import java.util.ArrayList;

public class Sala extends Node implements Serializable
{
  //Atributos
  private Integer nr_sala;
  private Integer lotacao_max;
  private RedBlackBST<Data,Horario> horario_sala=new RedBlackBST<>();

  //Constructor
  public Sala(Integer nr_sala, Integer lotacao_max, Ponto local) {
    super(nr_sala.toString(),local,"SALA");
    this.nr_sala = nr_sala;
    this.lotacao_max = lotacao_max;
  }

  //TODO: gets and sets para as ST gets retornam arraylist sets--> funcao que vai adicionar
  public RedBlackBST<Data, Horario> getHorario_sala() {
    return horario_sala;
  }

  public void setHorario_sala(RedBlackBST<Data, Horario> horario_sala) {
    this.horario_sala = horario_sala;
  }

  public Integer getNr_sala() {
    return nr_sala;
  }

  public void setNr_sala(Integer nr_sala) {
    this.nr_sala = nr_sala;
  }

  public Integer getLotacao_max() {
    return lotacao_max;
  }

  public void setLotacao_max(Integer lotacao_max) {
    this.lotacao_max = lotacao_max;
  }

  public Double getPiso(){
    return this.getPonto().getZ();
  }



  public String paraStringTxt() {
    String horarios = null;
    RedBlackBST<Data, Horario> horariosAux = this.getHorario_sala();
    Horario aux;
    Data id;
    if (!(this.getHorario_sala().isEmpty())) {
      for (Data ini : horariosAux.keys())//percorre RB de horarios e controi a string de hoarios
      {
        aux = horariosAux.get(ini); //um horario
        id = aux.getIncio();
        if (horarios == null) {
          horarios = "" + id.getDia_semana() + "/" + id.getHora() + "/" + id.getMinuto() + "\n"; //só datas iniciais! KEYS de HORARIOS
        } else {
          horarios = horarios + id.getDia_semana() + "/" + id.getHora() + "/" + id.getMinuto() + "\n";
        }
      }
    }
    if(horarios==null)
      return (this.getNr_sala() + "/" + this.getLotacao_max()
              + "/" + this.getPonto().getX() + "/" + this.getPonto().getY() +"/" + this.getPonto().getZ()+ "\n" + horarios + "\n->\n");

    return (this.getNr_sala() + "/" + this.getLotacao_max()
            + "/" + this.getPonto().getX() + "/" + this.getPonto().getY() +"/" + this.getPonto().getZ()+ "\n" + horarios + "->\n");

  }

  public ArrayList<String> pesquisarOcupacaoSala(Horario h) {
    ArrayList<String> ocupacao=new ArrayList<>(); //array list
    RedBlackBST<Data,Horario> horarios=this.getHorario_sala();  //horario completo da sala
    Horario horario; //horario auxiliar
    for (Data ini:horarios.keys()) //percorre o horario completa da sala
    {
      horario=horarios.get(ini); //um horario

      if((horario.getIncio().compareTo(h.getIncio())) >= 0  && (horario.getIncio().compareTo(h.getFim()))<=0)
      {
        ocupacao.add(horario.toString());
      }
    }
 return ocupacao;
  }

  @Override
  public String toString(){
    return("Nr da sala:"+this.getNr_sala()+"\tPiso:"+this.getPonto().getZ()+"\tLotação máxima:"+this.getLotacao_max()+"\tCoordenadas:"+this.getPonto());

  }
}