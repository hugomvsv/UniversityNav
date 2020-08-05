package edu.ufp.inf.Lp2Fase_1_;

import edu.princeton.cs.algs4.SeparateChainingHashST;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Professor extends Pessoa implements Serializable {

  //Atributos
  private String id_prof;
  private String email;
  private SeparateChainingHashST<String,Turma> turmas=new SeparateChainingHashST<>();
  private Horario horario_atend;


  //Constructor
  public Professor(String nome, String cc, String id_prof, String email, Horario horario_atend)
  {
    super(nome, cc);
    this.id_prof = id_prof;
    this.email = email;
    this.horario_atend=horario_atend;
  }

  //TODO: gets and sets para as ST gets retornam arraylist sets--> funcao que vai adicionar
  //Gets and Sets
  public SeparateChainingHashST<String,Turma> getTurmas() {
    return turmas;
  }

  public String getId_prof() {
    return id_prof;
  }

  public void setId_prof(String id_prof) {
    this.id_prof = id_prof;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Horario getHorario_atend() {
    return horario_atend;
  }

  public void setHorario_atend(Horario horario_atend) {
    SeparateChainingHashST<String,Turma> turmas =this.getTurmas();
    for(String id: turmas.keys())
    {
      if(turmas.get(id).getHorario_aula().compareTo(horario_atend)==0 || turmas.get(id).getHorario_aula().compareTo(horario_atend)==-1)//caso sejam iguais ou estive no meio
      {
        System.out.println("impossivel de adicionar horario de atendimento ao professor visto que já se encontra preenchido!");
        return;
      }
    }
    System.out.println("Horario de atendimento adicionado !");
    this.horario_atend = horario_atend;
  }





  public String paraStringTxt()
  {
    return(""+this.getNome()+"/"+this.getCc()+"/"+this.id_prof+"/"+this.getEmail()+"\n"+this.getHorario_atend().getIncio().toString()+"\n->\n");
  }
  @Override
  public String toString()
  {
    return("Nome:"+this.getNome()+"\tCC:"+this.getCc()+"\tNr professor:"+this.id_prof+"\tEmail:"+this.getEmail()+"\tHorario:"+this.getHorario_atend());
  }

  /**
   * inscreve o professor numa Uc
   * @param uc unidade curricular a ser inscrita
   * @param u universidade
   */
  public void inscreverProfemUc(Uc uc,Uni u) {
    SeparateChainingHashST<String,Turma> turmas=u.getTurmas();  //ST auxiliar de turmas
    Turma auxturma;                               // turma auxiliar
    for(String turma_id:turmas.keys())            //percorre as turmas e vê qual delas está atribuida a Uc desejada
    {
      if((auxturma=turmas.get(turma_id)).getUc().getId().compareTo(uc.getId())==0)        //caso a turma tenha a uc o professor adiciona a turma á sua ST
      {
        this.turmas.put(auxturma.getTurma_id(),auxturma);
        auxturma.getProfessores().put(this.getId_prof(),this);
      }
    }
  }

  /**
   * lista as turmas do professor
   */
  public void listarTurmasProfessor() {
    System.out.println("Turmas do Professor:");
    for (String t:turmas.keys())
    {
      System.out.println(this.turmas.get(t).toString());
    }
  }

  /**
   * pesquisa todas as turmas do professor
   * @return array list com as turmas do professor
   */
  public ArrayList<Turma> pesquisarTurmasProf() {
    SeparateChainingHashST<String,Turma> turmasAux=this.getTurmas();
    ArrayList<Turma> turmas =new ArrayList<>();
    for(String id: turmasAux.keys())
    {
      turmas.add(turmasAux.get(id));
    }
    return turmas;
  }

  public void horario_professor()
  {
    System.out.println("Horario Do Professor "+this.getNome()+": ");
    SeparateChainingHashST<String,Turma> turmas =this.getTurmas();
    Horario aux;
    for(String id: turmas.keys())
    {
      aux=turmas.get(id).getHorario_aula();
      System.out.println(aux);
    }

  }

}