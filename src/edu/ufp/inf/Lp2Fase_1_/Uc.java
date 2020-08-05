package edu.ufp.inf.Lp2Fase_1_;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;


public class Uc implements Serializable {
  private String nome;
  private Integer ects;
  private String id;
  private String tipo;
  private SeparateChainingHashST<String,Turma> turmas=new SeparateChainingHashST<>();

  public SeparateChainingHashST<String, Turma> getTurmas() {
    return turmas;
  }

  //TODO: get turmas e set
  public Uc(String nome, Integer ects,String tipo, String id) {
    this.nome = nome;
    this.ects = ects;
    this.id = id;
    this.tipo = tipo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getEcts() {
    return ects;
  }

  public void setEcts(Integer ects) {
    this.ects = ects;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  //métodos
  public ArrayList<Professor> pesquisarProfsdeUc()
  {
    ArrayList<Professor>profs=new ArrayList<>(); //Array list de profs
    SeparateChainingHashST<String, Turma> turmas= this.getTurmas(); //turmas presentes no sistema
    SeparateChainingHashST<String,Professor> profsAux;
    Turma taux;

    for(String id: turmas.keys()) //itera as turmas da uc
    {
      taux=turmas.get(id);
      profsAux=taux.getProfessores();
      for(String prof_id: profsAux.keys())
      {
        profs.add(profsAux.get(prof_id));
      }
    }
    return profs;
  }
  public String paraStringTxt()
  {
    return(""+this.getNome()+"/"+this.getEcts()+"/"+this.getTipo()+"/"+this.getId()+"\n->\n");
  }
  @Override
  public String toString()
  {
    return("Nome da UC:"+this.getNome()+"\tEcts:"+this.getEcts()+"\tTipo:"+this.getTipo()+"\tId:"+this.getId());
  }
}

