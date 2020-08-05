package edu.ufp.inf.Lp2Fase_1_;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Stack;

import java.io.*;
import java.util.*;

//horario: os horarios são já pré defenidos, são fixos, cada horario vai ter turmas, professores e salas utilizadas ou que usam esse horario
public class Uni implements Serializable
{
    private String nome;// nome da faculdade
    private Integer nr_alunos; //numero de alunos totais na faculdade
    private Integer nr_pisos; //numero de pisos da faculdade
    private RedBlackBST<Data, Horario> horarios = new RedBlackBST<>(); //horarios da faculdade
    private SeparateChainingHashST<Integer, Sala> salas = new SeparateChainingHashST<>(); //salas da faculdade
    private SeparateChainingHashST<String, Turma> turmas = new SeparateChainingHashST<>(); //turmas da faculdade
    private SeparateChainingHashST<String, Uc> ucs = new SeparateChainingHashST<>(); //ucs da faculdade
    private SeparateChainingHashST<String, Professor> professores = new SeparateChainingHashST<>(); //professores da faculdade
    private SeparateChainingHashST<String, Aluno> alunos = new SeparateChainingHashST<>(); //alunos da faculdade
    private SeparateChainingHashST<Node,Integer> nodes=new SeparateChainingHashST<>(); //todos os nós funciona como um aux
    private Infra infra ; //grafo da infraestrrutura da faculdade
    //Constructor
    public Uni(String nome, Integer nr_alunos, Integer nr_pisos)
    {
        this.nome = nome;
        this.nr_alunos = nr_alunos;
        this.nr_pisos=nr_pisos;

    }


    //Gets and Sets
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getNr_alunos() {
        return nr_alunos;
    }
    public void setNr_alunos(Integer nr_alunos) {
        this.nr_alunos = nr_alunos;
    }
    public RedBlackBST<Data, Horario> getHorarios() {
        return horarios;
    }
    public SeparateChainingHashST<Integer, Sala> getSalas() {
        return salas;
    }
    public SeparateChainingHashST<String, Turma> getTurmas() {
        return turmas;
    }
    public SeparateChainingHashST<String, Uc> getUcs() {
        return ucs;
    }
    public SeparateChainingHashST<String, Professor> getProfessores() {
        return professores;
    }
    public SeparateChainingHashST<String, Aluno> getAlunos() {
        return alunos;
    }
    public SeparateChainingHashST<Node, Integer> getNodes() {
        return nodes;
    }
    public void setNodes(SeparateChainingHashST<Node, Integer> nodes) {
        this.nodes = nodes;
    }
    public Infra getInfra() {
        return infra;
    }
    public void setInfra(Infra infra) {
        this.infra = infra;
    }
    //-----------Métodos-------------//

    public static void main(String[] args) throws FileNotFoundException, IOException, NodeNotFoundException, PisoIndisponivelException, ClassNotFoundException {
        Uni u = new Uni("Universidade Fernando Pessoa", 0, 2);//SEDE =3 pisos
        client_r3_r4_r5(u); //insert remove edit list e testes
        //u.printNodesAux();
        //client_r6(u);         //ler toda a informação dos ficheiros txt
        //client_r7(u);           //por para txt toda a informacao
        //client_r8(u);         // pesquisas
        //client_r9(u);         //funcao now
        client_r12(u);        //inicia o grafo de nós
        //client_r13_r14(u);        //menor caminho entre salas, desaativar nós
        client_r18(u);              //guardar e carregar para bin
        //client_r13_r14(u);           //testa se realmente está tudo impeck depois de ir buscar o bin !


    }



    /**
     * funcao de teste para list remove delete e edit para todas as ST mais alguns testes feitos
     * @param u BD que é a nossa universidade
     * @return
     */
    public static Uni client_r3_r4_r5(Uni u) {
        //cria datas
        Data d1 = new Data(2, 14, 30);
        Data d2 = new Data(2, 16, 30);
        Data d3 = new Data(3, 14, 30);
        Data d4 = new Data(3, 16, 30);
        Data d5 = new Data(4, 17, 30);
        Data d6 = new Data(4, 19, 30);
        Data d7 =new Data(4,20,30);
        
        //cria horarios
        Horario h1 = new Horario(d1, d2);
        Horario h2 = new Horario(d3, d4);
        Horario h3 = new Horario(d5, d6);
        Horario h4 = new Horario(d6, d7);

        Sala s1 = new Sala( 101, 35, new Ponto(13.0, 0.0,1.0));
        Sala s2 = new Sala( 102, 25, new Ponto(23.0, 0.0,1.0));
        Sala s3 = new Sala(103, 40, new Ponto(32.0, 0.0,1.0));
        Sala s4 = new Sala( 104, 20, new Ponto(13.0, 25.0,1.0));
        Sala s5 = new Sala(105, 35, new Ponto(23.0, 25.0,1.0));
        Sala s6 = new Sala( 106, 35, new Ponto(33.0, 25.0,1.0));

        Sala s7 = new Sala( 201, 35, new Ponto(13.0, 0.0,2.0));
        Sala s8 = new Sala( 202, 25, new Ponto(23.0, 0.0,2.0));
        Sala s9 = new Sala(203, 40, new Ponto(33.0, 0.0,2.0));
        Sala s10 = new Sala( 204, 20, new Ponto(13.0, 25.0,2.0));
        Sala s11 = new Sala(205, 35, new Ponto(23.0, 25.0,2.0));
        Sala s12 = new Sala( 206, 35, new Ponto(33.0, 25.0,2.0));

        Uc u1 = new Uc("LP2", 4, "TP", "LP2-TP");
        Uc u2 = new Uc("LP2", 4, "PR", "LP2-PR");
        Uc u3 = new Uc("Hardware e Sensores", 3, "TP", "HS-TP");
        Uc u4 = new Uc("Hardware e Sensores", 3, "PR", "HS-PR");

        Turma t1 = new Turma("RAJJ", 0, null, null, null);
        Turma t2 = new Turma("HMVSV", 0, null, null, null);
        Turma t3 = new Turma("TMJJ", 0, null, null, null);
        Turma t4 = new Turma("RDDP", 0, null, null, null);
        Turma t5 = new Turma("RGPP", 0, null, null, null);

        Professor p1 = new Professor("Rui Moreira", "1239874", "RMOREIRA", "r_moreira@ufp.edu.pt", null);
        Professor p2 = new Professor("Hugo Vieira", "4293464", "HVIEIRA", "h_vieira@ufp.edu.pt", null);
        Professor p3 = new Professor("Spashiva Corega", "213124", "SCOREGA", "s_corega@ufp.edu.pt", null);
        Professor p4 = new Professor("Nuno Ribeiro", "534521", "NRIBEIRO", "n_ribeiro@ufp.edu.pt", null);
        Professor p5 = new Professor("Androleta Silva", "345676", "ASILVA", "a_silva@ufp.edu.pt", null);

        Aluno a1 = new Aluno("Ruben Jorge", "597863", "37108", "37108@ufp.eu.pt", "Engenharia Informatica");
        Aluno a2 = new Aluno("Hugo Vieira", "324765", "36078", "36078@ufp.edu.pt", "Engenharia Informatica");
        Aluno a3 = new Aluno("Gabriel Almeida", "231243", "34590", "34590@ufp.edu.pt", "Dentaria");
        Aluno a4 = new Aluno("Rita Alexandra", "334165", "37123", "37123@ufp.edu.pt", "Psicologia");
        Aluno a5 = new Aluno("Maria Albertina", "584756", "37654", "37654@ufp.edu.pt", "Terapia da Fala");
        Aluno a6 = new Aluno("Joao Feio", "567564", "35678", "35678@ufp.edu.pt", "Criminologia");

        //inserir salas
        u.insert_sala(s1);
        u.insert_sala(s2);
        u.insert_sala(s3);
        u.insert_sala(s4);
        u.insert_sala(s5);
        u.insert_sala(s6);
        u.insert_sala(s7);
        u.insert_sala(s8);
        u.insert_sala(s9);
        u.insert_sala(s10);
        u.insert_sala(s11);
        u.insert_sala(s12);

        //inserir horarios
        u.insert_horario(h1);
        u.insert_horario(h2);
        u.insert_horario(h3);
        u.listar_horario();

        //inserir turmas
        u.insert_turma(t1);
        u.insert_turma(t2);
        u.insert_turma(t3);
        u.insert_turma(t4);

        //inserir ucs
        u.insert_uc(u1);
        u.insert_uc(u2);
        u.insert_uc(u3);
        u.insert_uc(u4);

        //inserir alunos
        u.insert_aluno(a1);
        u.insert_aluno(a2);
        u.insert_aluno(a3);
        u.insert_aluno(a4);
        u.insert_aluno(a5);
        u.insert_aluno(a6);

        //inserir professores
        u.insert_professor(p1);
        u.insert_professor(p2);
        u.insert_professor(p3);
        u.insert_professor(p4);
        u.insert_professor(p5);

        //listar
        u.listar_horario();
        u.listar_salas();
        u.listar_ucs();
        u.listar_turmas();
        u.listar_alunos();
        u.listar_professores();

        //inserir Ucs randoms  nos alunos
        a1.inscreverEmUc(u1,u);
        a1.inscreverEmUc(u2,u);
        a1.inscreverEmUc(u4,u);
        a2.inscreverEmUc(u1,u);
        a1.printHorarioAulas(a1.getHorarioAluno());

        //inserir Ucs randoms nos professores
        p1.inscreverProfemUc(u1,u);
        p2.inscreverProfemUc(u2,u);
        p4.inscreverProfemUc(u2,u);
        p3.inscreverProfemUc(u3,u);
        p1.listarTurmasProfessor();
        p1.horario_professor();
        u.insert_horario(h4);
        p1.setHorario_atend(h4);
        p2.setHorario_atend(h4);
        p3.setHorario_atend(h4);
        p4.setHorario_atend(h4);
        p5.setHorario_atend(h4);

        return u;
    }

    /**
     * main func para popular as diversas sts da  nossa Base de Dados
     * @param u universidade
     * @throws IOException
     */
    public static void client_r6(Uni u) throws IOException {
        try {
            u.populateHorariosFromTxt();
            u.populateSalasFromTxt();
            u.populateUcsFromTxt();
            u.populateAlunosFromTxt();
            u.populateProfsFromTxt();
            u.populatTurmasFromTxt();
        }catch (IOException io)
        {
            System.out.println("Erro ao Ler algum Ficheiro!");
        }
    }
    /**
     * funcao que escrever o dump de todas as Sts para ficheiros para depois serem lidos para popular sts
     * @param u universidade
     * @throws FileNotFoundException
     */
    public static void client_r7(Uni u) throws FileNotFoundException {
        try {
            u.horariosToTxt();
            u.salasToTxt();
            u.UcsToTxt();
            u.AlunosToTxt();
            u.ProfessoresToTxt();
            u.TurmasToTxt();

        } catch (FileNotFoundException Exception) {
            System.out.println("Erro ao abrir alguma fila!");
        }
    }
    /**
     * main func para as pesquisas!
     * @param u universidade que representa a BD
     */
    public static void client_r8(Uni u)
    {
        //pesquisar salas disponivei num determinado horario
        Horario horarioTeste= new Horario(new Data(2,15,30),new Data(2,17,30));
        System.out.println(u.pesquisarSalaLivres(horarioTeste,true));

        //pesquisar todos os professores
        Uc uc =u.getUcs().get("LP2-PR");
        System.out.println(uc.pesquisarProfsdeUc());

        //pesquisar turmas de professor
        Professor professor= u.getProfessores().get("RMOREIRA");
        System.out.println(professor.pesquisarTurmasProf());

        //pesquisar horarios de atendimento que o aluno pode ir
        Aluno aluno= u.getAlunos().get("37108");
        System.out.println(aluno.horariosAtendPossiveis());

        //pesquisar ocupação da sala num determinado intervalo de datas no nosso caso pode ser um horario
        Horario horarioTeste2=new Horario(new Data(2,14,30),new Data(4,18,30));
        Sala sala=u.getSalas().get(204);
        System.out.println(sala.pesquisarOcupacaoSala(horarioTeste2));

        //pesquisar salas por piso
        System.out.println(u.pesquisarSalasporPiso(1.0));
    }
    /**
     * funcao teste para a funcao now
     * @param u universidade
     */
    public static void client_r9(Uni u)
    {
        Data data1= new Data();
        Data data2=new Data();
        Horario instant= u.getHorarios().get(new Data(2,14,30)); // teste
        System.out.println(instant);
        u.now(instant);
    }

    /**
     * funcao para carregar  o grafo de nodes da faculdade
     * @param u universidade
     */
    public static void client_r12(Uni u)
    {
        u.infra=new Infra(u.getSalas(),u.nr_pisos,false); //carrega o grafo
        u.nodes=u.infra.getSt();
    }

    /**
     * Funcao para as funcoes de calculo
     * @param u
     */
    public static void client_r13_r14(Uni u) throws NodeNotFoundException, PisoIndisponivelException {
        System.out.println("\nCLIENT R13");
        //nodes desativados recebe um nó para desativar
        u.desativarNo(u,true,u.salas.get(102));
        //menor caminho entre duas salas (hard coded)
        u.menorCaminhoEntreSalas(u,true);
        //caminhos de emergencia par um nó (hard coded)
        u.caminhosDeEmergencia(u,false);
        //subGrafoPorPiso
        u.subGrafoByPiso(u,1);
        //resetGrafoOriginal
        u.infra.resetOriginalGraph(u.getSalas());//--------------------------->reseta o grafo para o original
        //desativa node
        u.desativarNo(u,true,u.salas.get(102));//-----------------------> desativa a 102 logo a 101 vai ter de ir dar a volta
        //subGrafoBySet
        u.subGrafoBySet(u,u.getSalas().get(101),u.getSalas().get(204));//----------------------->cria subgrafo a partir de um set de salas
        //menor caminho entre duas salas (hard coded)
        u.menorCaminhoEntreSalas(u,false);
        //caminhos de emergencia par um nó (hard coded)
        u.caminhosDeEmergencia(u,false);
        //Conexo
        u.isconexo(u);
    }

    private static void client_r18(Uni u) throws IOException, ClassNotFoundException {
        u.UniToBin();
        u.BinToUni(u);
    }



    public void now(Horario instant)
    {
        //Salas desocupadas
        ArrayList<Sala> salasLivres =this.pesquisarSalaLivres(instant,true);
        System.out.println(salasLivres);
        //Salas ocupadas
        ArrayList<Sala> salasIndis=this.pesquisarSalaLivres(instant,false);
        System.out.println(salasIndis);
        //Ucs a serem lecionadas
        ArrayList<Turma> lecionadas=this.turmasATeremAula(instant);
        System.out.println(lecionadas.toString());
        //Professores ocupados
        ArrayList<Professor> profsOcup=this.profsOcupados(lecionadas);
        System.out.println(profsOcup);
        //Professores livres
        ArrayList<Professor>profsLivres=this.profsLivres(profsOcup);
        System.out.println(profsLivres);
    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * funcao para encontrar os professores livres, recebe um array com os professores ocupados
     * e itera os profs da faculdade a guarda todos os professores que não contem no array recebido para
     * outro array
     * @param ocupados array list de professores ocupados
     * @return array list de professores livres
     */
    public ArrayList<Professor>profsLivres(ArrayList<Professor> ocupados) {
        SeparateChainingHashST<String,Professor> profs = this.getProfessores(); //professores da faculdade
        ArrayList<Professor> profsLivres=new ArrayList<>();
        Professor paux;
        for(String id:profs.keys())//itera os professores da faculdade
        {
            paux=profs.get(id);         //fica com o professor que esta a iterar
            if(!(ocupados.contains(paux)))
            {
               profsLivres.add(paux);
            }
        }
        return profsLivres;
    }
    /**
     * funcao que retorna os professores ocupados
     * @param lecionadas turmas que estao a ter aulas
     * @return professores ocupados
     */
    public ArrayList<Professor> profsOcupados(ArrayList<Turma> lecionadas) {
        ArrayList<Professor> profs=new ArrayList<>();
        SeparateChainingHashST<String,Professor> profes;
        for(Turma t:lecionadas)//pecorre as turmas que estao a ser lecionadas
        {
          profes=t.getProfessores();
          for(String id:profes.keys()) //percorre a st de professores
          {
              if(profs.isEmpty()) //caso o array esteje vazio adiciona logo
              {
                  profs.add(profes.get(id));
              }
              if(!profs.contains(profes.get(id)))//caso nao contenha este professor adiciona
              {
                  profs.add(profes.get(id));
              }
          }
        }
        return profs;
    }
    /**
     * funcao que vai retornar um arraylist com as turmas que estao a ter aulas
     * consequentemente as Ucs que estao a ser lecionadas
     * @param h horario instant
     * @return retorna o array list com as turmas a terem aula
     */
    public ArrayList<Turma> turmasATeremAula(Horario h) {
        SeparateChainingHashST<String,Turma> turmas=this.getTurmas();
        ArrayList<Turma> lecionadas= new ArrayList<>();
        Turma taux;
        Horario thorario;
        for(String id:turmas.keys()) //vai percorrer  as turmas
        {
            taux=turmas.get(id);
            thorario=taux.getHorario_aula();
            if(thorario.getIncio().compareTo(h.getIncio())==0) //caso a data de inicio sejam iguais adiciona logo
            {
                lecionadas.add(taux);
            }
            else
            {
                if(thorario.compareTo(h)<0) //caso o horario esteje no meio adiciona tb
                {
                    lecionadas.add(taux);
                }
            }
        }
        return  lecionadas;
    }
    /**
     * funcao que pesquisa as salas de um certo piso
     * @param piso nr do piso em questao
     * @return arraylist <string> com as salas dos pisos
     */
    public ArrayList<String> pesquisarSalasporPiso(Double piso) {
        SeparateChainingHashST<Integer,Sala> salas=this.getSalas();
        ArrayList<String> salitas  = new ArrayList<>();
        Sala sala;
        for(Integer nr:salas.keys()) //itera as salas
        {
            sala=salas.get(nr);
            if(sala.getPiso().compareTo(piso)==0)
            {
                salitas.add(sala.toString());
            }
        }
        return salitas;
    }
    /**
     * função que pesquisa as salas disponiveis num determinado horario / intervalo de tempo
     * @param h
     * @return
     */
    public ArrayList<Sala> pesquisarSalaLivres(Horario h,Boolean b) {
        SeparateChainingHashST<Integer, Sala> salasAux =this.getSalas(); //vai buscar as salas da universidade para a ST auxiliar
        RedBlackBST<Data,Horario> horarioSalaAux;      // RB auxiliar
        Sala salita;        //sala auxiliar
        Horario horariozito;  //horario auxiliar
        ArrayList<Sala> salasDisp = new ArrayList<>();
        ArrayList<Sala> salaInds=new ArrayList<>();
        int i=0;

        for(Integer nr: salasAux.keys()) // percorre as salas da BD
        {
            salita=salasAux.get(nr);
            if(salita.getHorario_sala()==null)      //caso a sala não esteja a ser utilizada nunca
            {
                salasDisp.add(salita);          //adiciona a sala ao array
            }
            else
            {
                horarioSalaAux =salita.getHorario_sala();    //horarios da sala
                i=horarioSalaAux.size();
                if(horarioSalaAux.contains(h.getIncio())) //caso tenha o horario inicial passa logo para a proxima iteração logo proxima sala
                {
                    salaInds.add(salita);
                    //não faz nada
                }
                //vamos comparar cada horario
                else
                {
                    for(Data dini: horarioSalaAux.keys()) //itera os horarios da sala
                    {
                        if(horarioSalaAux.get(dini).compareTo(h)<0)//caso um horario esteje no meio do horario pertendido ele descarta logo a sala
                        {
                            salaInds.add(salita);
                            i=i-1;
                            break;
                        }

                    }
                    if(i==horarioSalaAux.size())
                    {
                        salasDisp.add(salita); //caso nao haja horarios no meio do horario pretendido
                    }
                }

            }


        }
        if(b)
        {
            return salasDisp;
        }
        return salaInds;
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * funcao vai popular os horarios da ST com os horarios do txt
     * as turmas, professores e salas das sts horarios ficaram a null só com a chave
     * depois quando se inserir vamos dar put nos horarios
     * @throws IOException caso a file não exitir
     */
    public void populateHorariosFromTxt() throws IOException {
        FileReader fp=new FileReader("Horarios.txt");
        BufferedReader br=new BufferedReader(fp);
        String Line;
        while((Line=br.readLine())!=null) //lê logo a primeira linha
        {
            String[] Tokens=Line.split("/"); //devolve os tokens par Tokens separados por "/"
            Data d1=new Data(Integer.parseInt(Tokens[0]),Integer.parseInt(Tokens[1]),Integer.parseInt(Tokens[2]));
            Line=br.readLine(); //Lê nova linha
            Tokens=Line.split("/");//devolve os tokens par Tokens separados por "/"
            Data d2=new Data(Integer.parseInt(Tokens[0]),Integer.parseInt(Tokens[1]),Integer.parseInt(Tokens[2]));
            Horario horario=new Horario(d1,d2); //criação de um horario
            Line=br.readLine(); //quebra de linha
           this.getHorarios().put(horario.getIncio(),horario); //insere horario na BD
        }

    }
    /**
     * funcao de popular salas de txt para a BD
     * @throws IOException
     */
    public void populateSalasFromTxt() throws  IOException {
        FileReader fp=new FileReader("Salas.txt");
        BufferedReader br=new BufferedReader(fp);
        double x,y,z;
        int nr_sala,piso,lotacao;
        String Line;
        Data auxdata;
        Horario auxhorario;
        while((Line=br.readLine())!=null) //ler a primeira linha
        {
            String[] Tokens=Line.split("/"); //devolve tokens para Tokens separados por "/"
            nr_sala=Integer.parseInt(Tokens[0]);
            lotacao=Integer.parseInt(Tokens[1]);
            x=Double.parseDouble(Tokens[2]);
            y=Double.parseDouble(Tokens[3]);
            z=Double.parseDouble(Tokens[4]);
            Ponto ponto=new Ponto(x,y,z);
            Sala sala= new Sala(nr_sala,lotacao,ponto);
            Line=br.readLine();//Lê nova linha pode ser horario ou pode ser null
            if(Line.compareTo("null")==0) //caso ler null lemos a quebra de linha e passamos a inserir outra Sala
            {
                this.getSalas().put(Integer.parseInt(Tokens[0]),sala);
                Line=br.readLine(); //lê a quebra "-----"
            }
            else
            {
                do {
                    Tokens = Line.split("/");//devolve tokens para Tokens separados por "/" neste caso vai ficar com uma data
                    auxdata = new Data(Integer.parseInt(Tokens[0]), Integer.parseInt(Tokens[1]), Integer.parseInt(Tokens[2]));
                    (auxhorario = this.getHorarios().get(auxdata)).getSalas().put(sala.getNr_sala(), sala); //poe esta sala na ST de salas do horario
                    sala.getHorario_sala().put(auxdata, auxhorario);  //poe o horario na sala tambem
                }while ((Line=br.readLine()).compareTo("->")!=0);
                this.getSalas().put(sala.getNr_sala(),sala);
            }

        }
    }
    /**
     * funcao que para popular as St de Ucs da base de dados
     * @throws IOException
     */
    public void populateUcsFromTxt() throws IOException {
        FileReader fr= new FileReader("Ucs.txt");
        BufferedReader br=new BufferedReader(fr);
        String Line;
        while((Line=br.readLine())!=null) //lê logo a primeira linha
        {
            String[] Tokens=Line.split("/");
            Uc uc=new Uc(Tokens[0],Integer.parseInt(Tokens[1]),Tokens[2],Tokens[3]); //cria uma uc
            this.getUcs().put(uc.getId(),uc);  //adiciona uc á BD
            Line=br.readLine(); //Lê a quebra para outra uc
        }
    }
    /**
     * funcao que vai popular os alunos para a ST de alunos
     * @throws IOException
     */
    public void populateAlunosFromTxt()throws IOException {
        FileReader fr= new FileReader("Alunos.txt");
        BufferedReader br=new BufferedReader(fr);
        String Line;
        while((Line=br.readLine())!=null) //lê logo a primeira linha
        {
            String[] Tokens=Line.split("/");
            Aluno aluno=new Aluno(Tokens[0],Tokens[1],Tokens[2],Tokens[3],Tokens[4]);
            this.getAlunos().put(aluno.getNr_aluno(),aluno);
            Line=br.readLine(); //Lê a quebra para outra uc
        }
    }
    /**
     * popular professores dos txts
     * @throws IOException
     */
    public void populateProfsFromTxt()throws IOException {
        FileReader fr= new FileReader("Professores.txt");
        BufferedReader br=new BufferedReader(fr);
        String Line;
        Horario aux;
        Data data;
        while((Line=br.readLine())!=null) //lê logo a primeira linha
        {
            String[] Tokens=Line.split("/");
            Professor professor=new Professor(Tokens[0],Tokens[1],Tokens[2],Tokens[3],null);
            Line=br.readLine(); //Lê o horario de atend
            Tokens=Line.split("/");
            data=new Data(Integer.parseInt(Tokens[0]),Integer.parseInt(Tokens[1]),Integer.parseInt(Tokens[2]));
            aux=this.getHorarios().get(data);//vai buscar o horario a horarios
            aux.getProfessores().put(professor.getId_prof(),professor);//atribui o professor aquele horario
            professor.setHorario_atend(aux); //poe o horario no professor
            Line=br.readLine();
            this.getProfessores().put(professor.getId_prof(),professor);//lê a quebra de linha
            //volta para criar mais um professor
        }
    }
    /**
     * funcao que vai popular as turmas mas tambem vai popular a ST turmas mas tambem vai completar
     * todas as outras Sts pois turmas é como se fosse o centro de toda a informação existente
     * @throws IOException
     */
    public void populatTurmasFromTxt()throws IOException {
        FileReader fr= new FileReader("Turmas.txt");
        BufferedReader br=new BufferedReader(fr);
        String Line;
        Uc auxUc;
        Sala salaAux;
        Horario horarioAux;
        Aluno alunoAux;
        Professor profAux;
        while ((Line=br.readLine())!=null)//lê logo a primeira linha
        {
            String[] Tokens=Line.split("/");
            auxUc=this.getUcs().get(Tokens[2]); //vai buscar a uc da turma á ST
            salaAux=this.getSalas().get(Integer.parseInt(Tokens[3])); //vai buscar a sala da turma á ST
            Turma turma=new Turma(Tokens[0],Integer.parseInt(Tokens[1]),auxUc,salaAux,null);

            Line=br.readLine(); //lê uma nova linha
            Tokens=Line.split("/"); //vai ler a key para o horario da turma
            horarioAux=this.getHorarios().get(new Data(Integer.parseInt(Tokens[0]),Integer.parseInt(Tokens[1]),Integer.parseInt(Tokens[2])));
            turma.setHorario_aula(horarioAux);

            Line=br.readLine();//lê nova linha que corresponde aos alunos
            if(Line.compareTo("null")==0)
            {

            }
            else
            {
                Tokens=Line.split("/");
                for (String s : Tokens) {
                    alunoAux = this.getAlunos().get(s);
                    turma.getAlunos().put(alunoAux.getNr_aluno(), alunoAux);
                    alunoAux.getTurmas().put(turma.getTurma_id(), turma);
                }
            }


            Line=br.readLine();//lê nova linha que corresponde aos professores
            if(Line.compareTo("null")==0)
            {

            }
            else
            {
                Tokens=Line.split("/");
                for (String token : Tokens) {
                    if(token==null)
                        continue;
                    profAux = this.getProfessores().get(token);
                    turma.getProfessores().put(profAux.getId_prof(), profAux);
                    profAux.getTurmas().put(turma.getTurma_id(), turma);
                }
            }
            auxUc.getTurmas().put(turma.getTurma_id(),turma);
            horarioAux.getTurmas().put(turma.getTurma_id(),turma);
            Line=br.readLine();
            this.getTurmas().put(turma.getTurma_id(),turma);
        }
    }

    /**
     * grupo de funçoes que iram escrever para o txts a informação presente nas STs
     * de froma  que seja possivel popular as mesmas com estes ficheiros
     *
     * @throws FileNotFoundException
     */
    //-------------------------------------------------------------------------------------------------------------------------------------
    public void horariosToTxt() throws FileNotFoundException {

       Horario aux;
        Formatter output = new Formatter(new File("Horarios.txt"));
        for (Data dinicio : this.horarios.keys())
        {
            aux=this.horarios.get(dinicio);
            output.format(aux.paraStringtxt());
        }
        output.close();

    }
    public void salasToTxt() throws FileNotFoundException {
        Formatter output = new Formatter(new File("Salas.txt"));
        for (Integer id : this.salas.keys())
        {
            output.format(this.salas.get(id).paraStringTxt());
        }
        output.close();
    }
    public void UcsToTxt() throws FileNotFoundException {
        Formatter output = new Formatter(new File("Ucs.txt"));
        for (String id : this.ucs.keys()) {
            output.format(this.ucs.get(id).paraStringTxt());
        }
        output.close();
    }
    public void AlunosToTxt() throws FileNotFoundException {
        Formatter output = new Formatter(new File("Alunos.txt"));
        for (String id : this.alunos.keys()) {
            output.format(this.alunos.get(id).paraStringTxt());
        }
        output.close();
    }
    public void ProfessoresToTxt() throws FileNotFoundException {
        Formatter output = new Formatter(new File("Professores.txt"));
        for (String id : this.professores.keys()) {
            output.format(this.professores.get(id).paraStringTxt());
        }
        output.close();
    }
    public void TurmasToTxt() throws FileNotFoundException {
        Formatter output = new Formatter(new File("Turmas.txt"));
        for (String id : this.turmas.keys()) {
            output.format(this.turmas.get(id).paraString() + "\r\n");
        }
        output.close();
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * @param t turma que iremos atribuir ao horario
     * @return um horario random do sistema para atribuir aquela turma
     */
    public Horario getRandomHorario(Turma t) {
        Horario h;                          //horario auxiliar
        int nrKeys = this.horarios.size();          //nr de keys na RB horarios
        int randKey = (int) (Math.random() * (nrKeys) + 1);   // nr random com o limite de keys
        Data aux = this.horarios.select(randKey - 1);           //Data na posicao  da key
        h = this.horarios.get(aux);                                   //vamos buscar o horario á RB
        h.getTurmas().put(t.getTurma_id(), t);                       //vamos por na ST de turmar atribuidas do horario a turma
        return h;                                                   //retornamos o horario
    }
    /**
     * Funcao que vai atribuir uma turma a uma uc e que ira dar horario e sala a turma
     *
     * @param u Uc a atribuir turma
     * @return turma atribuida
     */
    public Turma atribuir_turma(Uc u) {
        Turma aux;
        Sala sala;
        for (String id : this.turmas.keys()) { //percorre as turmas da uni e vê se têm a Uc a null
            if ((aux = this.turmas.get(id)).getUc() == null) //caso a Uc seja null atribui a Uc á turma e a turma na ST d eturmas da Uc
            {
                aux.setUc(u);                               //atribui a Uc á turma
                u.getTurmas().put(aux.getTurma_id(),aux);    //acrescenta a turma á Uc
                do
                {
                    aux.setHorario_aula(getRandomHorario(aux));  //atribui um horario random
                }
                while ((sala = atribuir_sala(aux.getHorario_aula())) == null);
                aux.setSala(sala);
                System.out.println("Turma atribuida!");
                return aux;
            }
        }
        System.out.println("Turmas cheias");
        return null;
    }
    /**
     * Função que vai retornar uma sala que não tenho o horario da turma
     *
     * @param h horario a não ter
     * @return sala disponivel
     */
    public Sala atribuir_sala(Horario h) {

        Sala sala;  //sala auxiliar
        RedBlackBST<Data, Horario> aux;        //RB auxiliar
        for (Integer nr : this.salas.keys()) {      //itera as salas
            sala = this.salas.get(nr);                  //pega na sala
            if ((aux=sala.getHorario_sala()).isEmpty()) //caso o horario da sala esteja vazio entao adicionamos o horario á sala e retornamos a sala
            {
                sala.getHorario_sala().put(h.getIncio(), h);
                h.getSalas().put(sala.getNr_sala(), sala);
                return sala;
            }
            if (!(aux.contains(h.getIncio()))) //caso a sala nao tenha este horario já reservado adicionamos o horario á sala e retornamos a sala
            {
                sala.getHorario_sala().put(h.getIncio(), h);
                h.getSalas().put(sala.getNr_sala(), sala);
                return sala;
            }
        }
        System.out.println("Nao existe salas disponiveis!");
        return null;
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Funcão que insere um horario na Universidade
     * @param h horario a ser inserido
     * @return retorna 1 em caso de sucesso 0 caso de insucesso
     */
    public Integer insert_horario(Horario h) {
        //caso a BD esteja vazia insere logo
        if (this.horarios.isEmpty()) {
            this.horarios.put(h.getIncio(), h);
            System.out.println("\nHorario adicionado!");
            return 1;
        }
        //caso não exista na BD insere
        if (!(this.horarios.contains(h.getIncio()))) {
            this.horarios.put(h.getIncio(), h);
            System.out.println("\nhorario adicionado!");
            return 1;
        }
        System.out.println("\nNao foi possivel adicioanr horario");
        return 0;
    }

    /**
     * remove um horario da base de dados
     * @param d é a chave para eliminar o horario
     * @return retorna 1 em caso de sucesso 0 caso de insucesso
     */
    public Integer remove_horario(Data d) {
        Horario aux;
        Professor auxprof;
        RedBlackBST<Data, Horario> auxst;
        Turma auxturma;

        //caso a BD esteja vazia não remove
        if (this.horarios.isEmpty()) {
            System.out.println("Erro ao remover o horario!");
            return 0;
        }

        //caso o horario exista na bd entao remove o horario de todos
        if ((aux = this.horarios.get(d)) != null) {
            //percorre os professores e vê se têm o horario atribuido
            for (String prof : this.professores.keys()) {   //caso o horario esteja atribuido elimina tambem do professor
                if ((auxprof = this.professores.get(prof)).getHorario_atend().equals(aux)) {
                    auxprof.setHorario_atend(null);
                    System.out.println("Horario de atendimento de um prof eliminado!");
                }
            }
            //percorre as salas e  remove o horario da RedBlack
            for (Integer sala : this.salas.keys()) {
                if ((auxst = this.salas.get(sala).getHorario_sala()).contains(d)) {
                    auxst.delete(d);
                    System.out.println("Horario removido de uma sala!");
                }
            }
            //percorre as turmas compara o horario se for igual elimina
            for (String turma : this.turmas.keys()) {
                if ((auxturma = turmas.get(turma)).getHorario_aula().equals(aux)) {
                    auxturma.setHorario_aula(null);
                    System.out.println("Horario eliminado numa turma ");
                }
            }
            //por fim da delete na redblack da Uni
            this.horarios.delete(d);
            System.out.println("Horario eliminado ");
            return 1;
        }
        System.out.println("Erro ao eliminar horario");
        return 0;
    }

    /**
     * lista todos os horarios existentes na universidade
     */
    public void listar_horario() {
        if (this.horarios.isEmpty()) {
            System.out.println("Erro ao listar o horario!");
        }
        for (Data h : this.horarios.keys()) {
            System.out.println(this.horarios.get(h));
        }

    }

    /**
     *
     * @param h horario a modificar
     * @param day caso modificar o dia
     * @param horainicio caso modificar hora do inicio
     * @param horafim caso modificar hora do fim
     * @param minutoinicio caso modificar minuto inicial
     * @param minutofim caso modificar minuto final
     */
    public void editar_horario(Horario h, int day, int horainicio, int horafim, int minutoinicio, int minutofim) {
        Horario aux;
        Data d1 = new Data(day, horainicio, minutoinicio);
        Data d2 = new Data(day, horafim, minutofim);


        //caso a BD esteja vazia não remove
        if (this.horarios.isEmpty()) {
            System.out.println("Erro ao remover o horario!");
        }
        if ((aux = this.horarios.get(h.getIncio())) != null) {
            this.horarios.delete(aux.getIncio());
            aux.setIncio(d1);
            aux.setFim(d2);
            this.horarios.put(aux.getIncio(), aux);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Inserir turma na universidade
     * @param t turma a ser inserida na universidade
     * @return retorna 1 caso de sucesso 0 em caso de insucesso
     */
    public Integer insert_turma(Turma t) {
        //caso a SCST esteja vazia adiciona logo
        if (this.turmas.isEmpty()) {
            this.turmas.put(t.getTurma_id(), t);
            System.out.println("Turma adicionada com sucesso!");
            return 1;
        }
        //caso nao contenha a turma
        if (!(this.turmas.contains(t.getTurma_id()))){
            this.turmas.put(t.getTurma_id(), t);
            System.out.println("Turma adicionada com sucesso!");
            return 1;
        }
        System.out.println("Erro ao inserir a turma!");
        return 0;
    }

    /**
     * Remover uma turma da universidade
     * @param turma_id id da turma a ser removido
     * @return retorna 1 caso de sucesso 0 em caso de insucesso
     */
    public Integer remove_turma(String turma_id) {
        Turma aux; // auxiliar do tipo turma que nos ajuda a eliminar das outras STs
        SeparateChainingHashST<String, Turma> auxst;
        //caso a bd esteje vazia retorna logo
        if (this.turmas.isEmpty()) {
            return 0;
        } else {
            //se a turma existir na bd então é removida
            if ((aux = this.turmas.get(turma_id)) != null) {
                //remover a turma das ucs da BD
                for (String uc_id : this.ucs.keys()) {
                    if ((auxst = this.ucs.get(uc_id).getTurmas()).contains(turma_id))
                        auxst.delete(turma_id);
                    System.out.println("Turma remvida de Uc");
                }
                // remover a turma dos alunos que tenham a turma
                for (String aluno : this.alunos.keys()) {
                    if ((auxst = this.alunos.get(aluno).getTurmas()).contains(turma_id))
                        auxst.delete(turma_id);
                    System.out.println("Turma remvida de aluno");
                }
                //remover a turma dos professores que tenham a turma
                for (String prof_id : this.professores.keys()) {
                    if ((auxst = this.professores.get(prof_id).getTurmas()).contains(turma_id))
                        auxst.delete(turma_id);
                    System.out.println("Turma remvida de professor");
                }
                //remover turma dos horarios que usa
                for (Data dinicio : this.horarios.keys()) {
                    if ((auxst = this.horarios.get(dinicio).getTurmas()).contains(turma_id))
                        auxst.delete(turma_id);
                    System.out.println("Turma remvida de horario");
                }

                this.turmas.delete(turma_id);
                System.out.println("Turma removida !");
                return 1;
            }
        }
        return 0;
    }

    /**
     * funcao que vai listar todas as turmas da faculdade
     */
    public void listar_turmas() {
        if (this.turmas.isEmpty()) {
            System.out.println("Erro ao listar turmas!");
        }
        for (String t : this.turmas.keys()) {
            System.out.println(this.turmas.get(t).toString());
        }

    }

    /**
     * editar um turma da universidade
     * @param t turma a ser editada
     * @param s modificar a sala da turma
     * @param h modificar horario da turma
     */
    public void editar_turmas(Turma t, Sala s, Horario h) {
        //caso a BD esteja vazia não remove
        if (this.turmas.isEmpty()) {
            System.out.println("Erro ao editar turmas!");
        }
        if (t != null) {
            t.setSala(s);
            t.setHorario_aula(h);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     *funcao que irá inserir uma Uc na universidade
     * @param u inserir uc na universidade
     * @return retorna 1 em caso de sucesso retorna 0 em caso de insucesso
     */
    //adicionar remover editar e listar UCs
    public Integer insert_uc(Uc u) {
        Turma t;
        Uc aux;
        //caso a ST esteje vazia insere logo a Uc
        if (this.ucs.isEmpty()) {
            //ao inserir uma uc vai atribuir uma turma a essa uc e essa turma vai receber uma sala e um horario
            this.ucs.put(u.getId(), u);
            aux=this.ucs.get(u.getId());
            t = atribuir_turma(aux);
            System.out.println("Uc inserida com sucesso!");
            return 1;
        }
        //caso não exista na ST
        if (!(this.ucs.contains(u.getId())))
        {
            this.ucs.put(u.getId(), u);
            aux=this.ucs.get(u.getId());
            t = atribuir_turma(aux);
            if (t != null)
            {
                u.getTurmas().put(t.getTurma_id(), t);
                System.out.println("Uc inserida com sucesso!");
                return 1;
            }
        }
        System.out.println("Uc já existe!");
        return 0;
    }

    /**
     * funcao que ira remover a uc da base de dados
     * Qunado removida a Uc tambem remove a turma atribuida a essa UC
     * @param uc_id id da uc que irá ser removiada da base de dados
     * @return 1 em caso de sucesso e 0 em caso de insucesso
     */
    public Integer remove_uc(String uc_id) {
        Turma auxturma;
        if (this.ucs.isEmpty()) {
            return 0;
        }
        if (this.ucs.contains(uc_id)) {
            //remove a uc da turma e remove a turma
            for (String turma_id : this.turmas.keys()) {
                if ((auxturma = this.turmas.get(turma_id)).getUc().getId().compareTo(uc_id) == 0) {
                    remove_turma(auxturma.getTurma_id());
                    System.out.println("removeu a turma que tinha a uc");
                }
            }
            this.ucs.delete(uc_id);
            return 1;
        }
        return 0;
    }

    /**
     * Lista todas as Ucs da universidade
     */
    public void listar_ucs() {
        if (this.ucs.isEmpty()) {
            System.out.println("Erro ao listar UCs!");
        }
        for (String t : this.ucs.keys()) {
            System.out.println(this.ucs.get(t));
        }

    }

    /**
     * Edita um Uc da universidade
     * @param u Uc a ser modificada
     * @param nome modificar nome da uc
     * @param ects modificar numero de ects
     * @param tipo modificar o tipo
     * @param id modificar id da Uc
     */
    public void editar_ucs(Uc u, String nome, int ects, String tipo, String id) {
        if (this.ucs.isEmpty()) {
            System.out.println("Erro ao editar uc!");
        }
        if (u != null) {
            u.setNome(nome);
            u.setEcts(ects);
            u.setTipo(tipo);
            u.setId(id);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Insere uma sala na universidade
     * @param s sala a ser inserida
     * @return retorna 1 em caso de sucesso 0 em caso de insucesso
     */
    //adicionar remover editar e listar SALAS
    public Integer insert_sala(Sala s) {
        //caso a ST esteja vazia adiciona logo
        if (this.salas.isEmpty()) {
            this.salas.put(s.getNr_sala(), s);
            System.out.println("Sala adicionada com sucesso!");
            return 1;
        }
        //se a ST não tiver esta sala
        if (!(this.salas.contains(s.getNr_sala()))) {
            this.salas.put(s.getNr_sala(), s);
            System.out.println("Sala adicionada com sucesso!");
            return 1;
        }
        System.out.println("Erro ao adicionar Sala!");
        return 0;
    }

    /**
     * remove uma sala da universidade
     * @param nrSala nr da sala a ser removida
     * @return 1 em caso de sucesso 0 em caso de insucesso
     */
    public Integer remove_sala(Integer nrSala) {
        SeparateChainingHashST<Integer, Sala> auxst;
        Turma auxturma;
        //caso a ST esteje vazia retorna logo
        if (this.salas.isEmpty()) {
            System.out.println("Sala nao encontrada");
            return 0;
        }
        //caso a st contem a sala entao remove a sala de tudo
        if (this.salas.contains(nrSala)) {
            for (Data d : this.horarios.keys()) {
                if ((auxst = this.horarios.get(d).getSalas()).contains(nrSala)) {
                    auxst.delete(nrSala);
                    System.out.println("Sala removida de um horario");
                }
            }
            for (String turma_id : this.turmas.keys()) {
                auxturma = this.turmas.get(turma_id);
                if (auxturma.getSala().getNr_sala().equals(nrSala)) {
                    auxturma.setSala(null);
                    System.out.println("Sala  removida de uma turma!");
                }
            }
            this.salas.delete(nrSala);
            System.out.println("Sala removida!");
            return 1;
        }
        System.out.println("Sala nao encontrada");
        return 0;
    }

    /**
     * lista todas as salas da universidade
     */
    public void listar_salas() {
        if (this.salas.isEmpty()) {
            System.out.println("Erro ao listar salas!");
        }
        for (int a : this.salas.keys()) {
            System.out.println(this.salas.get(a));
        }
    }

    /**
     * edita uma sala da universidade
     * @param s sala a  ser modificada
     * @param piso modificar piso onde se encontra a sala
     * @param nsala modificar nr da sala
     * @param lotacao modificar lotaçao máxima
     * @param p modificar ponto
     */
    public void editar_salas(Sala s, int piso, int nsala, int lotacao, Ponto p) {
        if (this.salas.isEmpty()) {
            System.out.println("Erro ao editar uc!");
        }
        if (s != null) {
            s.setPiso(piso);
            s.setLotacao_max(lotacao);
            s.setPonto(p);
            s.setNr_sala(nsala);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Inserir aluno na faculdade
     * @param a aluno a ser removido
     * @return 1 em caso de sucesso 0 em caso de insucesso
     */
    //adicionar remover editar e listar ALUNOS
    public Integer insert_aluno(Aluno a) {
        //caso a ST estiver vazia adiciona logo
        if (this.alunos.isEmpty()) {
            this.alunos.put(a.getNr_aluno(), a);
            System.out.println("Aluno adionado!");
            return 1;
        }
        //caso a ST não contenha o aluno
        if (!(this.alunos.contains(a.getNr_aluno()))) {
            this.alunos.put(a.getNr_aluno(), a);
            System.out.println("Aluno adionado!");
            return 1;
        }
        System.out.println("Erro ao inserir o aluno");
        return 0;
    }

    /**
     * funcao que remove o aluno da universidade
     * @param nr_aluno nr do aluno a remover da universidade
     * @return 1 em caso de sucesso 0 em caso de insucesso
     */
    public Integer remove_aluno(String nr_aluno) {
        SeparateChainingHashST<String, Aluno> auxst;
        //caso a St estiver vazia retorna logo
        if (this.alunos.isEmpty()) {
            System.out.println("Aluno nao encontrado");
            return 0;
        }
        //se a ST contiver o aluno entao
        if (this.alunos.contains(nr_aluno)) {
            //entao vai percorrer as turmas e ver se têm o aluno
            for (String id : this.turmas.keys()) {
                //se tiver o aluno elimina o aluno da turma
                if ((auxst = this.turmas.get(id).getAlunos()).contains(nr_aluno)) {
                    auxst.delete(nr_aluno);
                    System.out.println("Aluno removido de uma turma");
                }
            }
            //por fim remove da ST aluno
            this.alunos.delete(nr_aluno);
            System.out.println("Aluno removido!");
            return 1;
        }
        return 0;
    }

    /**
     * lista todos os alunos da universidade
     */
    public void listar_alunos() {
        if (this.alunos.isEmpty()) {
            System.out.println("Erro ao listar salas!");
        }
        for (String a : this.alunos.keys()) {
            System.out.println(this.alunos.get(a));
        }

    }

    /**
     * Edita um aluno da faculdade
     * @param a aluno a ser editado
     * @param nome modificar nome do aluno
     * @param cc modificar cc do aluno
     * @param nraluno modificar nr do aluno
     * @param email modificar email do aluno
     * @param curso modificar curso do aluno
     */
    public void editar_alunos(Aluno a, String nome, String cc, String nraluno, String email, String curso) {
        if (this.alunos.isEmpty()) {
            System.out.println("Erro ao editar alunos!");
        }
        if (a != null) {
            a.setNome(nome);
            a.setCc(cc);
            a.setCc(nraluno);
            a.setEmail(email);
            a.setCurso(curso);
        }

    }
    //adicionar remover editar e listar PROFESSOR
    //-------------------------------------------------------------------------------------------------------------------------------------
    /**
     * funcao que via inserir um professor na universidade
     * @param p professor a ser inserido
     * @return retorna 1 em caso de sucesso 0 em caso de insucesso
     */
    public Integer insert_professor(Professor p) {

        //caso a ST estiver vazia adiciona logo
        if (this.professores.isEmpty()) {
            this.professores.put(p.getId_prof(), p);
            System.out.println("Professor adicionado!");
            return 1;
        }
        //se a ST ainda não tiver essa chave adiciona
        if (!(this.professores.contains(p.getId_prof()))) {
            this.professores.put(p.getId_prof(), p);
            System.out.println("Professor adicionado!");
            return 1;
        }
        return 0;
    }

    /**
     * funcao que vai remover um professor
     * @param prof_id id do professor a ser removido
     * @return 1 em caso de sucesso 0 em caso de insucesso
     */
    public Integer remove_professor(String prof_id) {
        SeparateChainingHashST<String, Professor> auxst;
        //caso a ST estiver vazia!
        if (this.professores.isEmpty()) {
            System.out.println("Impossivel de remover o Professor!");
            return 0;
        }
        //se a ST tiver o professor entao vai remover
        if (this.professores.contains(prof_id)) {
            for (String id : this.turmas.keys()) {
                if ((auxst = this.turmas.get(id).getProfessores()).contains(prof_id)) {
                    auxst.delete(prof_id);
                    System.out.println("Professor removido de uma turma!");
                }
            }
            for (Data d_ini : this.horarios.keys()) {
                if ((auxst = this.horarios.get(d_ini).getProfessores()).contains(prof_id)) {
                    auxst.delete(prof_id);
                    System.out.println("Professor removido de uma turma!");
                }
            }
            this.professores.delete(prof_id);
            System.out.println("Professor removido com suecesso!");
            return 1;
        }
        return 0;
    }

    /**
     * lista todos os professores na universidade
     */
    public void listar_professores() {
        if (this.professores.isEmpty()) {
            System.out.println("Erro ao listar salas!");
        }
        for (String a : this.professores.keys()) {
            System.out.println(this.professores.get(a));
        }
    }

    /**
     *
     * @param p professor a ser modificado
     * @param nome modificar o nome do professor
     * @param cc modificar o cc do professor
     * @param nrprof modificar o nrdoprofessor
     * @param email modificar email do professor
     * @param h modificar horario de atendimento do professor
     */
    public void editar_professores(Professor p, String nome, String cc, String nrprof, String email, Horario h) {
        if (this.professores.isEmpty()) {
            System.out.println("Erro ao editar alunos!");
        }
        if (p != null) {
            p.setNome(nome);
            p.setCc(cc);
            p.setId_prof(nrprof);
            p.setEmail(email);
            p.setHorario_atend(h);
        }
    }
    //-------------------------------------------------------------------------------------------------------------------------------------

    public void printNodesAux()
    {
        SeparateChainingHashST<Node,Integer> aux=this.nodes;
        for(Node n: aux.keys())
        {
            System.out.println(n.getId()+"--->INDEX: "+aux.get(n).toString());
            for(Node node :n.getPesos().keys())
            {
                System.out.println(node.getId()+ " Peso-> "+n.getPesos().get(node)+" metros");
            }
            System.out.println("");
        }
    }

    public void isconexo(Uni u)
    {
        u.infra.isconnected();
    }
    /**
     * funçao que vai organizar a St dos nodes para ter tudo organizado já
     */
    public void menorCaminhoEntreSalas(Uni u,Boolean time) throws NodeNotFoundException, PisoIndisponivelException {
        Ponto ponto =new Ponto(13.0, 0.0,1.0);//ponto referente a 101
        Infra.time=time;
        System.out.println("Menor caminho entre as salas 101 a 103 é:");
        Stack<Ligacao> caminho= u.infra.stEntreSalas(ponto,u.getSalas().get(103)); //caminho mais curto entra salas
        Double distancia=u.infra.getDistanceInMeters(caminho);
        Double tempo=u.infra.getTime(caminho);

        if(Infra.time)
        {
            System.out.println("Caminho -->  ");
            this.infra.printCaminhoAux(caminho);
            System.out.println("\nTempo:"+distancia +" segundos");
            return;
        }
        System.out.println("Caminho -->  ");
        this.infra.printCaminhoAux(caminho);
        System.out.println("\nDistancia:"+distancia +" metros");
    }



    /**
     * dá output do melhor caminho de emergencia, sendo que o o plano é sair o mais rapidamente da faculdade
     * @param u universidade
     * @param time se true retorna em tempo se false retorna em distancia
     */
    public void caminhosDeEmergencia(Uni u,Boolean time) throws NodeNotFoundException, PisoIndisponivelException {
        Ponto ponto =new Ponto(20.0, 5.0,1.0);//ponto referente a 101
        Infra.time=time;
        System.out.println("\nCaminho de emergencia:");
        SeparateChainingHashST<Node,Integer> nodes =u.nodes;


        Node n=u.getSalas().get(101);
        Stack<Ligacao> emergencia=u.infra.stEmergencia(ponto);

            Double distemerg=u.infra.getDistanceInMeters(emergencia);
            Double tempoemerg=u.infra.getTime(emergencia);
        if(Infra.time)
        {
            System.out.println("Caminho -->  ");
            this.infra.printCaminhoAux(emergencia);
            System.out.println("\nTempo:"+tempoemerg +" segundos");
            return;
        }
        System.out.println("Caminho -->  ");
        this.infra.printCaminhoAux(emergencia);
        System.out.println("\nDistancia:"+distemerg +" metros");
    }

    /**
     * manda nodes para desativar no grafo
     * @param u universidade
     * @param time se true retorna o tempo senao retorna a distancia
     * @param node node a ser desativado
     * @throws NodeNotFoundException caso nao tenha o nó
     */
    public void desativarNo(Uni u,Boolean time,Node node) throws NodeNotFoundException {
        Infra.time=time;
        this.infra.desativarNodeDaUni(node);
    }

    /**
     * vai mandar construir um subgrafo de um determinado piso
     * @param u universidade
     */
    public void subGrafoByPiso(Uni u,Integer piso) throws PisoIndisponivelException {
        u.infra.subgrafoByPiso(piso);
        this.infra.setNr_pisos(1);
    }

    /**
     * cria um subgrafo a partir dum set desde o node source até ao node dest
     * @param u
     * @param source
     * @param dest
     */
    private void subGrafoBySet(Uni u, Node source, Node dest) throws NodeNotFoundException {
        u.infra.subgrafoBySet(source,dest);
    }


    /**
     * funcoes para obter o arrayList dos alunos profs salas horarios turmas etc JAVA FX
     *
     */

    public ArrayList<Aluno> getArrayAlunos()
    {
        ArrayList<Aluno> aux=new ArrayList<>();
        SeparateChainingHashST<String,Aluno> alunos= this.getAlunos();
        for(String nr: alunos.keys())
        {
            aux.add(alunos.get(nr));
        }
        return aux;
    }

    public ArrayList<Professor> getArrayProfessores()
    {
        ArrayList<Professor> aux= new ArrayList<>();
        SeparateChainingHashST<String,Professor> profs=this.getProfessores();
        for(String nr: profs.keys())
        {
            aux.add(profs.get(nr));
        }
        return aux;
    }

    public void populateUni()
    {
        try {
            this.populateHorariosFromTxt();
            this.populateSalasFromTxt();
            this.populateUcsFromTxt();
            this.populateAlunosFromTxt();
            this.populateProfsFromTxt();
            this.populatTurmasFromTxt();
        }catch (IOException io)
        {
            System.out.println("Erro ao Ler algum Ficheiro!");
        }
    }

    public ArrayList<Sala> getArraySalas() {
        ArrayList<Sala> aux= new ArrayList<>();
        SeparateChainingHashST<Integer,Sala> salas=this.getSalas();
        for(Integer nr: salas.keys())
        {
            aux.add(salas.get(nr));
        }
        return aux;
    }

    public ArrayList<Turma> getArrayTurmas() {
        ArrayList<Turma> aux= new ArrayList<>();
        SeparateChainingHashST<String,Turma> turmas=this.getTurmas();
        for(String s: turmas.keys())
        {
            aux.add(turmas.get(s));
        }
        return aux;
    }

    public ArrayList<Horario> getArrayHorarios() {
        ArrayList<Horario> aux= new ArrayList<>();
       RedBlackBST<Data,Horario> horarios=this.getHorarios();
        for(Data d: horarios.keys())
        {
            aux.add(horarios.get(d));
        }
        return aux;
    }

    /**
     * guarda a universidade num ficheiro binário
     * @throws IOException
     */
    public void UniToBin() throws IOException {
        FileOutputStream  file = new FileOutputStream("Universidade.bin");
        ObjectOutputStream oos= new ObjectOutputStream(file);
        oos.writeObject(this);
        oos.close();
        System.out.println("Objeto Universidade guardado!");
    }

    /**
     * carrega a universidade de um ficheiro binário
     * @param u universidade
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void BinToUni(Uni u) throws IOException, ClassNotFoundException
    {
        FileInputStream  file = new FileInputStream("Universidade.bin");
        ObjectInputStream ois= new ObjectInputStream(file);
        u=((Uni)ois.readObject());
        System.out.println("Objeto Universidade Carregado!");
    }
}