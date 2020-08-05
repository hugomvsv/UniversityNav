package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Aluno extends Pessoa implements Serializable
{
  //Atributos
  private String nr_aluno;
  private String email;
  private String curso;
  private SeparateChainingHashST<String,Turma>turmas =new SeparateChainingHashST<>();
  //private RedBlackBST<Data,Horario> horario_aulas=new RedBlackBST<>();

  //constructor
  public Aluno(String nome, String cc, String nr_aluno, String email, String curso) {
    super(nome, cc);
    this.nr_aluno = nr_aluno;
    this.email = email;
    this.curso = curso;
  }

  //TODO: gets and sets para as ST gets retornam arraylist sets--> funcao que vai adicionar
  //gets and sets

  public SeparateChainingHashST<String,Turma> getTurmas() {
    return turmas;
  }

  public String getNr_aluno()
  {
    return nr_aluno;
  }

  public void setNr_aluno(String nr_aluno)
  {
    this.nr_aluno = nr_aluno;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getCurso()
  {
    return curso;
  }

  public void setCurso(String curso)
  {
    this.curso = curso;
  }

  //métodos

  /**
   *
   * @param uc a se inscrver
   * @param u base de dados onde contem as turmas
   */
  public void inscreverEmUc(Uc uc,Uni u) {
    SeparateChainingHashST<String,Turma> turmas=u.getTurmas();  //ST auxiliar de turmas
    Turma auxturma;                               // turma auxiliar
    for(String turma_id:turmas.keys())            //percorre as turmas e vê qual delas está atribuida a Uc desejada
    {
      if((auxturma=turmas.get(turma_id)).getUc().getId().compareTo(uc.getId())==0)        //caso a turma tenha a uc o aluno adiciona a turma á sua ST
      {
        this.turmas.put(auxturma.getTurma_id(),auxturma);
        auxturma.getAlunos().put(this.getNr_aluno(),this);
        auxturma.setNr_alunos(auxturma.getNr_alunos()+1);
      }
    }
  }
  /**
   *
   * @return ST<HORARIO, INTEGER> horario-> horario da aula Integer-> nr da sala
   */
  public SeparateChainingHashST<Data,Horario> getHorario() {
    SeparateChainingHashST<Data,Horario> horario_aluno=new SeparateChainingHashST<>();
    SeparateChainingHashST<String,Turma> turmas=this.getTurmas(); //vai buscar as turmas que o aluno tem
    ArrayList<String> horario=new ArrayList<>();
    Turma t;
    Horario h;
    for(String id:turmas.keys())  //vai percorrer as turmas do aluno
    {
      t=turmas.get(id);
      h=t.getHorario_aula();
      horario_aluno.put(h.getIncio(),h);
    }
    return horario_aluno;
  }
  public ArrayList<String> getHorarioAluno() {
    SeparateChainingHashST<String,Turma> turmas=this.getTurmas(); //vai buscar as turmas que o aluno tem
    ArrayList<String> horario=new ArrayList<>();
    Turma t;
    for(String id:turmas.keys())  //vai percorrer as turmas do aluno
    {
        t=turmas.get(id);
        horario.add(t.getHorario_aula().toString()+"\tSala: "+t.getSala().getNr_sala()+"\tUC: "+t.getUc().getNome());
    }
  return horario;
  }
  public void printHorarioAulas(ArrayList<String> horario) {
    System.out.println("HORARIO DO ALUNO: "+this.getNome());
    for(String s:horario)
    {
      System.out.println(s);
    }
  }

  /**
   * funcao que determina os horarios de atendimento que o aluno consegue ir
   * @return Array list de string com o professor e o horario de atendimento do mesmo
   */
  public ArrayList<String> horariosAtendPossiveis() {
    ArrayList<String> horarios_atend =new ArrayList<>();
    SeparateChainingHashST<String,Turma> turmas= this.getTurmas();
    SeparateChainingHashST<String,Professor> profs;
    Turma taux; //turma aux
    Professor paux; //professor aux
    Horario patend; //horario de atendimento

    for(String t_id:turmas.keys()) //percorre as turmas do aluno
    {
      taux=turmas.get(t_id);//vai ter uma turma
      profs=taux.getProfessores();
      for(String p_id:profs.keys()) //itera os professores da turma
      {
        paux=profs.get(p_id); //um professor da turma
        patend=paux.getHorario_atend(); //horario de atendimento do prof
       if(testarSeExisteTempo(patend)>0)
       {
         horarios_atend.add(paux.getNome()+"\t"+patend.toString());
       }
      }
    }
    return horarios_atend;
  }

  /**
   * funcao que testa se será possivel o aluno ir ao horario de atendimento do professor
   * @param h horario a  ser testado
   * @return -1 caso o horario não esteja livre, 1  se o horario estiver livre
   */
  public Integer testarSeExisteTempo(Horario h) {
  SeparateChainingHashST<Data,Horario> horario_aluno=this.getHorario();
  int i=horario_aluno.size();
  if(horario_aluno.contains(h.getIncio()))
  {
    return -1;
  }
  else
  {
    for(Data dini: horario_aluno.keys()) //itera os horarios das aulas do aluno
    {
      if(horario_aluno.get(dini).compareTo(h)<0)//caso um horario esteje no meio do horario pertendido ele descarta logo a hipotese de match
      {
        i=i-1;
        return -1;
      }

    }
    if(i==horario_aluno.size())
    {
      return 1;
    }
  }
  return 0;
}

  public String paraStringTxt()
  {
    return(""+this.getNome()+"/"+this.getCc()+"/"+this.getNr_aluno()+"/"+this.getEmail()+"/"+this.getCurso()+"\n->\n");
  }
  @Override
  public String toString(){
    return("Nome:"+this.getNome()+"\tCC:"+this.getCc()+"\tNr aluno:"+this.getNr_aluno()+"\tEmail:"+this.getEmail()+"\tCurso:"+this.getCurso()+"\t");
}}
