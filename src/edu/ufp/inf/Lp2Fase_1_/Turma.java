package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.Vector;

public class Turma implements Serializable {

  //Atributos
  private String turma_id;
  private Integer nr_alunos;
  private Uc uc;
  private Sala sala;
  private Horario horario_aula;
  private SeparateChainingHashST<String,Aluno>alunos=new SeparateChainingHashST<>();
  private SeparateChainingHashST<String,Professor>professores=new SeparateChainingHashST<>();

  //Constructor
  public Turma(String turma_id, Integer nr_alunos, Uc uc, Sala sala, Horario horario_aula) {
    this.turma_id = turma_id;
    this.nr_alunos = nr_alunos;
    this.uc = uc;
    this.sala = sala;
    this.horario_aula = horario_aula;
  }
  //TODO: get alunos e get professores;
  //Gets and Sets
  public String getTurma_id() {
    return turma_id;
  }

  public void setTurma_id(String turma_id) {
    this.turma_id = turma_id;
  }

  public Integer getNr_alunos() {
    return nr_alunos;
  }

  public void setNr_alunos(Integer nr_alunos) {
    this.nr_alunos = nr_alunos;
  }

  public Uc getUc() {
    return uc;
  }

  public void setUc(Uc uc) {
    this.uc = uc;
  }

  public Sala getSala() {
    return sala;
  }

  public void setSala(Sala sala) {
    this.sala = sala;
  }

  public Horario getHorario_aula() {
    return horario_aula;
  }

  public void setHorario_aula(Horario horario_aula) {
    this.horario_aula = horario_aula;
  }

  public SeparateChainingHashST<String, Aluno> getAlunos() {
    return alunos;
  }

  public SeparateChainingHashST<String, Professor> getProfessores() {
    return professores;
  }

  public String getNomeUc()
  {
    return uc.getNome();
  }
  public String getMainProfessor()
  {
    for(String n: this.professores.keys())
      return this.professores.get(n).getNome();
    return "Não Tem";
  }

  //metodos

  public String paraString() {
    String alunos=null;
    String professores=null;
    if(!(this.alunos.isEmpty()))
    {
      for(String id:this.alunos.keys())
      {
        if(alunos==null)
        {

          alunos=this.alunos.get(id).getNr_aluno();
        }
        else
          alunos=alunos+'/'+this.alunos.get(id).getNr_aluno();
      }
    }

    if(!(this.professores.isEmpty()))
    {
      for(String id:this.professores.keys())
      {
        if(professores==null)
        {
          professores=this.professores.get(id).getId_prof();
        }
        else
          professores=professores+'/'+this.professores.get(id).getId_prof();
      }
    }
    if(getUc()!=null)
      return(""+getTurma_id()+"/"+getNr_alunos()+"/"+getUc().getId()+"/"+getSala().getNr_sala()
              +"\n"+getHorario_aula().getIncio().toString()+"\n"+alunos+"\n"+professores+"\n->");

    return(""+getTurma_id()+"/"+getNr_alunos()+"/"+getUc()+"/"+getSala()+"/"+getHorario_aula().getIncio().toString()+"\n"+alunos+"\n"+professores+"\n->\n");
  }

  @Override
  public String toString()
  {
    if(getUc()!=null)
    return("Turma id:"+getTurma_id()+"\tNr alunos:"+getNr_alunos()+"\tUC:"+getUc().getId()+"\tSala:"+getSala().getNr_sala()+"\tHorario:"+getHorario_aula());
    return("Turma id:"+getTurma_id()+"\tNr alunos:"+getNr_alunos()+"\tUC:"+getUc()+"\tSala:"+getSala()+"\tHorario:"+getHorario_aula());
  }
}