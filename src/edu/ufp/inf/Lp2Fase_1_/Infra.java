/******************************************************************************
 *  Compilation:  javac SymbolDigraph.java
 *  Execution:    java SymbolDigraph
 *  Dependencies: ST.java Digraph.java In.java
 *  Data files:   https://algs4.cs.princeton.edu/42digraph/routes.txt
 *  
 *  %  java SymbolDigraph routes.txt " "
 *  JFK
 *     MCO
 *     ATL
 *     ORD
 *  ATL
 *     HOU
 *     MCO
 *  LAX
 *
 ******************************************************************************/

package edu.ufp.inf.Lp2Fase_1_;
import edu.princeton.cs.algs4.*;
import org.w3c.dom.ls.LSOutput;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  The {@code SymbolDigraph} class represents a digraph, where the
 *  vertex names are nodes.
 *  By providing mappings between string vertex names and integers,
 *  it serves as a wrapper around the
 *  {@link EdgeWeightedGraph} data type, which assumes the vertex names are integers
 *  between 0 and <em>V</em> - 1.
 *  It also supports initializing a symbol digraph from a file.
 *  <p>
 *  This implementation uses an {@link SeparateChainingHashST} to map from Nodes to integers,
 *  an array to map from integers to strings, and a {@link Digraph} to store
 *  the underlying graph.
 *  The <em>indexOf</em> and <em>contains</em> operations take time 
 *  proportional to log <em>V</em>, where <em>V</em> is the number of vertices.
 *  The <em>nameOf</em> operation takes constant time.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Infra implements Serializable
{
    private SeparateChainingHashST<Node, Integer> st =new SeparateChainingHashST<>();     // Node -> index
    private SeparateChainingHashST<Integer, Node> keys; // index  -> Node
    private SeparateChainingHashST<Node, Integer> save;
    private EdgeWeightedDigraph graph;                      // the underlying digraph
    private Integer nr_pisos;
    private Integer n_pisos_save;
    protected static boolean time;

    public Infra(SeparateChainingHashST<Integer, Sala> salas, Integer nr_pisos, Boolean tempo)
    {
        this.nr_pisos=nr_pisos;
        this.n_pisos_save=nr_pisos;
        this.carregar_st_grafo(salas); //carrega para a st toda a estrutura
        this.start();  //faz o grafo
        time=tempo;
        //todo montar o grafo e atribuir index ao node
    }

    public SeparateChainingHashST<Node, Integer> getSt() {
        return st;
    }

    public void setSt(SeparateChainingHashST<Node, Integer> st) {
        this.st = st;
    }

    public SeparateChainingHashST<Integer, Node> getKeys() {
        return keys;
    }

    public void setKeys(SeparateChainingHashST<Integer, Node> keys) {
        this.keys = keys;
    }

    public EdgeWeightedDigraph getGraph() {
        return graph;
    }

    public void setGraph(EdgeWeightedDigraph graph) {
        this.graph = graph;
    }

    public Integer getNr_pisos() {
        return nr_pisos;
    }

    public void setNr_pisos(Integer nr_pisos) {
        this.nr_pisos = nr_pisos;
    }
    //constructer



    /**
     * funcao que vai dar start ao grafo e á st de keys
     */
    public void start()
    {   this.keys=new SeparateChainingHashST<>();
        //invertes inde to get node key in an array
        for (Node n : this.st.keys()) {
            this.keys.put(st.get(n), n);
        }
        //build graph
        this.graph = new EdgeWeightedDigraph(st.size()); //tem tantos vertices como nos

        //temos de adicionar os pesos
        for (Integer i : this.keys.keys()) //percorre os nós
        {
            Node node = this.keys.get(i);         //nó auxiliar
            SeparateChainingHashST<Node, Double> pesos = node.getPesos();  //vai buscar a st com os pesos do nó
            for (Node n : pesos.keys())                                  //percorre a st com os pesos
            {
                if(this.st.get(n)!=null)
                {
                    Ligacao li = new Ligacao(i, this.st.get(n), pesos.get(n));  //cria uma edge
                    graph.addEdge(li);                                                  //adiciona ao grafo a edge
                }
            }
        }
        System.out.println(this.graph); //printa o grafo
    }

    /**
     * carrega o grafo
     * @param salitas salas da universidade
     */
    public void carregar_st_grafo(SeparateChainingHashST<Integer,Sala> salitas) {
        ArrayList<Node> nodeaux=new ArrayList<>();
        SeparateChainingHashST<Node,Integer> nodes= this.getSt();
        RedBlackBST<Integer, Sala> salas;
        Node lastNode = null; //ultimo nó a ser adicionado
        Node currentNode= null;
        Node aux;
        boolean flag=true;
        Sala sala;

        //monta escadas, hall, pp entra
        monta_estrutura(nodeaux,nr_pisos);

        //começa a montar a ST de nodes para depois enviar para a construçao do grafo
        for(int i=0;i<this.nr_pisos;i++)//para cada piso vai montar de forma igual menos para o primeiro
        {
            if(i==0)//adiciona a entrada ao inicio
            {
                nodes.put(lastNode=getNodeInicial(nodeaux),nodes.size());
            }
            else{//quando sobe de nivel adiciona o topo da escada ao inicio
                nodes.put(currentNode=getNodebyPiso(nodeaux,(double)i+1,"ESCADATOPO"),nodes.size());
                currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto()));//acrescento o peso ao no currente com o no anterior
                lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto()));//Acrescento o peso ao no anterior com o no currente
                lastNode=currentNode;
            }
            salas=getSalasByPiso((double)(i+1),salitas);
            for(Integer nr: salas.keys()) //percorre as salas
            {
                if(salas.get(nr).getPonto().getY()!=0.0 && flag)//caso a posiçao y da sala deixe de ser 0 adiciona o ponto de passagem
                {
                    nodes.put(currentNode=getNodebyPiso(nodeaux,(double)(i+1),"PP"),nodes.size());
                    currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto()));//acrescento o peso ao no currente com o no anterior
                    lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto()));//Acrescento o peso ao no anterior com o no currente
                    lastNode=currentNode;
                    nodes.put(currentNode=getNodebyPiso(nodeaux,(double)(i+1),"ACESS"),nodes.size());
                    currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto()));//acrescento o peso ao no currente com o no anterior
                    lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto()));//Acrescento o peso ao no anterior com o no currente
                    lastNode=currentNode;
                    flag=false;
                }
                //adiciona sala atribuindo pesos ao node
                nodes.put(currentNode=(salas.get(nr)),nodes.size());
                currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto())); //acrescento o peso ao no currente com o no anterior
                lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto())); //Acrescento o peso ao no anterior com o no currente
                lastNode=currentNode;
            }

            //depois de serem adicionadas todas as salas do piso vamos adicionar o hall
            nodes.put(currentNode=getNodebyPiso(nodeaux,(double)(i+1),"HALL"),nodes.size());
            currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto())); //acrescento o peso ao no currente com o no anterior //peso com a ultima sala
            lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto())); //Acrescento o peso ao no anterior com o no currente //ultima sala com o hall

            sala=salas.get(salas.min()); //vai buscar a primeira sala do piso e prenche pesos
            currentNode.getPesos().put(sala,currentNode.getPonto().getDistanceFrom(sala.getPonto()));
            sala.getPesos().put(currentNode,sala.getPonto().getDistanceFrom(currentNode.getPonto()));

            if(i==0) //caso seje 1 vamos buscar a entrada
            {
                aux=getNodeInicial(nodeaux);
                currentNode.getPesos().put(aux,currentNode.getPonto().getDistanceFrom(aux.getPonto()));
                aux.getPesos().put(currentNode,aux.getPonto().getDistanceFrom(currentNode.getPonto()));
            }
            else //caso seje dos outros niveis a cima vai buscar uma escada topo desse piso
            {
                aux=getNodebyPiso(nodeaux,(double)(i+1),"ESCADATOPO");
                currentNode.getPesos().put(aux,currentNode.getPonto().getDistanceFrom(aux.getPonto()));
                aux.getPesos().put(currentNode,aux.getPonto().getDistanceFrom(currentNode.getPonto()));
            }

            lastNode=currentNode;
            nodes.put(currentNode=getNodebyPiso(nodeaux,(double)(i+1),"ESCADABOT"),nodes.size());
            currentNode.getPesos().put(lastNode,currentNode.getPonto().getDistanceFrom(lastNode.getPonto()));//acrescento o peso ao no currente com o no anterior
            lastNode.getPesos().put(currentNode,lastNode.getPonto().getDistanceFrom(currentNode.getPonto()));//Acrescento o peso ao no anterior com o no currente
            sala=salas.get(salas.max()); //vai buscar a ultima sala do piso e prenche pesos
            currentNode.getPesos().put(sala,currentNode.getPonto().getDistanceFrom(sala.getPonto()));
            sala.getPesos().put(currentNode,sala.getPonto().getDistanceFrom(currentNode.getPonto()));
            lastNode=currentNode;
            flag=true; //vai para outro piso precisa da flag a true;
        }
        this.save=this.st; //guarda um save da st normal
    }

    /**
     * distancia ou tempo entre duas salas
     * @param p ponto onde o aluno está neste momento --> source
     * @param d destiny
     * @return retorna um caminho
     * @throws NodeNotFoundException
     */
    public Stack<Ligacao> stEntreSalas(Ponto p,Node d) throws NodeNotFoundException, PisoIndisponivelException {
        Node s=closerNodeFromSource(p);
        if(!st.contains(s) && !st.contains((d)))
            throw new NodeNotFoundException("Node nao existe!");
       else
        {
            int source = this.st.get(s);
            DijkstraSP dij = new DijkstraSP(this.graph,source);
            return (Stack<Ligacao>) dij.pathTo(this.st.get(d));
        }

    }

    /**
     * funcao que retorna o caminho mais rapido de emergencia
     * @param p ponto onde o user / porf está
     * @return caminho de emergencia
     * @throws NodeNotFoundException
     */
    public Stack<Ligacao> stEmergencia(Ponto p) throws NodeNotFoundException, PisoIndisponivelException {
        Node s=closerNodeFromSource(p);
        if(!st.contains(s))
            throw new NodeNotFoundException("Node nao existe!");
        else {
            SeparateChainingHashST<Double, Node> nodesemerg = carregar_nodes_emergencia(s);
            Node node = melhor_node_emergencia(nodesemerg);
            int source = this.st.get(s);
            DijkstraSP dij = new DijkstraSP(this.graph, source);
            return (Stack<Ligacao>) dij.pathTo(this.st.get(node));
        }
    }

    /**
     * desativa um nó do grafo aumentando o seu peso
     * @param node nó a desativar
     * @throws NodeNotFoundException
     */
    public void desativarNodeDaUni(Node node) throws NodeNotFoundException {
        if(!st.contains(node))
            throw new NodeNotFoundException("Node nao existe na st!");
        else
        {
            SeparateChainingHashST<Node, Double> pesos=this.keys.get(this.st.get(node)).getPesos();
            for(Node n : pesos.keys())
            {
                if(n.getPesos().get(node)!=null) //tem o node na st de pesos
                {
                    n.getPesos().put(node,10000.0);
                }
            }
        }
        start(); //cria um grafo com os pesos atualizados
    }

    /**
     * retona qual será o melhor node de emergencia a ir
     * @param nodes todos os nós de emergencia
     * @return melhor nó
     */
    public Node melhor_node_emergencia(SeparateChainingHashST<Double,Node> nodes)
    {
        double menor=1000.0;
        for(Double d: nodes.keys())
        {
            if(d<menor)
            {
                menor=d;
            }
        }
        return nodes.get(menor);
    }
    /**
     * vai contruir uma St com os caminhos do
     * @param s nó source
     * @return retorna st preenchida com os nós do tipo outdoor (emergencia)
     */
    public SeparateChainingHashST<Double,Node> carregar_nodes_emergencia(Node s)
    {
        SeparateChainingHashST<Double,Node> aux=new SeparateChainingHashST<>();

        for(Node n : this.st.keys())
        {
            if(n.getOutdoor())//caso tenha acesso ao exterior
            {
                DijkstraSP dij=new DijkstraSP(this.getGraph(),this.st.get(s));
                aux.put(dij.distTo(this.st.get(n)),n);
            }
        }
        return aux;
    }
    /**
     * monta os nodes estruturais necessarios para a universidade
     * @param nodeaux arraylist de nodes auxiliar
     * @param nr_pisos nr de pisos da universidade
     */
    public void monta_estrutura( ArrayList<Node> nodeaux, int nr_pisos)
    {
        Node entrada=new Node("ENTRADA",new Ponto(0.0,0.0,1.0),"ENTRADA"); //porta de entrada
        nodeaux.add(entrada);
        for(int i=0;i<this.nr_pisos;i++)//cria tantos halls, pontos de passagem, e acessos ao exterior/interior
        {
            Node auxhall=new Node("HALL-"+(i+1),new Ponto(5.0,10.0,(double)i+1),"HALL");
            Node auxPP=new Node("PP-"+(i+1),new Ponto(32.50,10.0,(double)i+1),"PP");
            Node auxAcess=new Node("ACESS-"+(i+1),new Ponto(32.50,20.0,(double)i+1),"ACESS");
            nodeaux.add(auxhall);
            nodeaux.add(auxPP);
            nodeaux.add(auxAcess);
        }
        for(int j=0;j<this.nr_pisos;j++)//cria tantas encadas como npisos-1 (o primeiro nao tem escadas)
        {
            Node escadatopo=new Node("EscadaTopo-"+(j+2),new Ponto(0.0,5.0,(double)j+2),"ESCADATOPO");
            Node escadabot=new Node("EscadaBot-"+(j+1),new Ponto(0.0,15.0,(double)j+1),"ESCADABOT");
            nodeaux.add(escadatopo);
            nodeaux.add(escadabot);
        }
    }

    /**
     * retorna a disntancia
     * @param caminho stack de Ligacoes
     * @return disntancia
     */
    public Double getDistanceInMeters(Stack<Ligacao> caminho)
    {
        double distancia=0.0;
        for(Ligacao d:caminho)
        {
            distancia=distancia+d.weight();
        }
        return distancia;
    }

    /**
     * tempo entra nos
     * @param caminho stack de ligacoes
     * @return tempo
     */
    public Double getTime(Stack<Ligacao> caminho)
    {
        double tempo=0.0;
        for(Ligacao d:caminho)
        {
            tempo=tempo+d.weight();
        }
        return tempo;
    }

    /**
     * retorna um node por piso e tipo
     * @param nodes arraylist auxiliar de nos
     * @param piso piso que queremos
     * @param tipo tipo de nó
     * @return nó pedido
     */
    public Node getNodebyPiso(ArrayList<Node> nodes, Double piso,String tipo)
    {
        for(Node n:nodes)
        {
            if(n.getTipo().compareTo(tipo)==0)
            {
                if(n.getPiso().compareTo(piso)==0)
                {
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * retorna um node por piso e tipo
     * @param nodes schST de nodes
     * @param piso piso que queremos
     * @param tipo tipo de nó
     * @return nó pedido
     */
    public Node getNodebyPiso(SeparateChainingHashST<Node,Integer> nodes, Double piso,String tipo)
    {
        for(Node n:nodes.keys())
        {
            if(n.getTipo().compareTo(tipo)==0)
            {
                if(n.getPiso().compareTo(piso)==0)
                {
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * funcao que retorna a entrada
     * @param nodes array de nós
     * @return node entrada
     */
    public Node getNodeInicial(ArrayList<Node> nodes)
    {
        for(Node n:nodes)
        {
            if(n.getTipo().compareTo("ENTRADA")==0)
            {
                return n;
            }
        }
        return null;
    }

    /**
     * retorna uma redblack de salas por piso
     * @param piso piso das salas que queremos
     * @param salasAux salas da universidade
     * @return red de salas por piso
     */
    public RedBlackBST<Integer,Sala> getSalasByPiso(Double piso,SeparateChainingHashST<Integer,Sala> salasAux)
    {
        RedBlackBST<Integer, Sala> salasRed=new RedBlackBST<>();
        Sala sala;
        for(Integer s: salasAux.keys())
        {
            if((sala=salasAux.get(s)).getPonto().getZ().compareTo(piso)==0)
            {

                salasRed.put(sala.getNr_sala(),sala);
            }
        }
        return salasRed;
    }

    /**
     * se o grafo é conexo
     * @return true se é false se não é
     */
    public Boolean isconnected() {
        Boolean flag=true;
        int source=0;
        EdgeWeightedDigraph G=this.getGraph();
        DijkstraSP dij=new DijkstraSP(G,source);
        for(int i=0;i<G.V();i++)
        {
            if(!dij.hasPathTo(i))
            {
                System.out.println("Grafo não conexo!");
                flag=false;
                return flag;
            }
        }
        System.out.println("Grafo conexo!");
        return flag;
    }

    /**
     * cria um subgrafo de um piso
     * @param piso piso escolhido para fazer subgrafo
     */
    public void subgrafoByPiso(Integer piso) throws PisoIndisponivelException {
        if(this.nr_pisos<piso) throw new PisoIndisponivelException("Piso indisponivel");
        else{
            this.st=getNodesOfPiso((double)piso);
            start();
        }

    }

    /**
     * funcao que retorna todos os nodes do piso! Com o index modificado
     * @param piso piso escolhido para fazer subgrafo
     * @return st dos nós do piso
     */
    public SeparateChainingHashST<Node,Integer> getNodesOfPiso(Double piso)
    {
        SeparateChainingHashST<Node, Integer> nodes =new SeparateChainingHashST<>();
        RedBlackBST<Integer,Node> auxnodes=new RedBlackBST<>();

        for(Node n: this.st.keys())
        {
            if(n.getPiso().compareTo(piso)==0)//caso tenha o mesmo piso
            {
                auxnodes.put(this.st.get(n),n); //vai buscar o seu id á st e o node
            }
        }
        for(Integer i: auxnodes.keys()) //passa para os nodes tudo ordenadoooo
        {
            nodes.put(auxnodes.get(i),nodes.size());
        }
        return nodes;
    }

    /**
     * repoem o grafo inicial da faculdade
     */
    public void resetOriginalGraph(SeparateChainingHashST<Integer,Sala> salitas)
    {
        this.st=new SeparateChainingHashST<>();
        this.nr_pisos=this.n_pisos_save;
        this.carregar_st_grafo(salitas);
        start();
    }

    public void subgrafoBySet(Node source, Node dest) throws NodeNotFoundException {
        if(!this.st.contains(source) || !this.st.contains(dest))
            throw new NodeNotFoundException("Um dos nós do set não encontrados!");
        if(this.st.get(source)<this.st.get(dest))
        {
            SeparateChainingHashST<Node,Integer> nodes =getNodesOfSet(source,dest);
            this.st=nodes;
            System.out.println("TAMANHO NODES "+nodes.size());
            start();
        }
        else
        {
            System.out.println("Subgrafo impossivel de se fazer!");
        }
    }

    /**
     * funcao que retorna um set de nodes para criar o subgrafo
     * @param source onde começa
     * @param dest onde acaba
     * @return retorna uma st com todos esses nós
     */
    public SeparateChainingHashST<Node, Integer> getNodesOfSet(Node source, Node dest) {

        RedBlackBST<Integer,Node> aux=new RedBlackBST<>();
        SeparateChainingHashST<Node,Integer> nodes= new SeparateChainingHashST<>();
        int max_index=this.st.get(dest);
        int min_index=this.st.get(source);
        int index=0;
        for(Node n: this.st.keys())
        {
            index=st.get(n);
            if((index>=min_index && index<=max_index))
            {
                aux.put(index,n);
            }
        }
        for(Integer i: aux.keys())
        {
            nodes.put(aux.get(i),nodes.size());
        }
        System.out.println("TAMANHO DOS NODES: "+nodes.size());
        return nodes;
    }

    /**
     * passado o ponto vai buscar o nó mais perto
     * @param ponto localizacao do aluno
     * @return node mais perto do aluno
     * @throws PisoIndisponivelException caso o piso não seja válido
     */
    public Node closerNodeFromSource(Ponto ponto) throws PisoIndisponivelException {
        if(this.nr_pisos<ponto.getZ())
        {
            throw new PisoIndisponivelException("Piso nao encontrado");
        }
        SeparateChainingHashST<Node,Integer> nodes =getNodesOfPiso(ponto.getZ());
        double distmin=10000.0;
        double aux=0.0;
        Node melhornode=null;
        for(Node n:nodes.keys())
        {
            if((aux=n.getPonto().getDistanceFrom(ponto))<distmin)
            {
                melhornode=n;
                distmin=aux;
            }
        }
        System.out.println(melhornode.getId());
        return melhornode;
    }


    public void printCaminhoAux(Stack<Ligacao> caminho) {
        SeparateChainingHashST<Integer,Node> keys = this.getKeys();
        for(Ligacao l:caminho)
        {
            System.out.println(keys.get(l.from()).getId()+"  -->  "+keys.get(l.to()).getId()+" - "+"[Distancia: "+l.weight()+" Tempo: "+ l.gettime()+ "]");
        }
    }
}

    /**  
     * Initializes a digraph from a file using the specified delimiter.
     * Each line in the file contains
     * the name of a vertex, followed by a list of the names
     * of the vertices adjacent to that vertex, separated by the delimiter.
     * @param filename the name of the file
     * @param delimiter the delimiter between fields
     */
   /** public SymbolDigraph(String filename, String delimiter)
    {
        st = new ST<String, Integer>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }

        // inverted index to get string keys in an array
        keys = new String[st.size()];
        for (String name : st.keys()) {
            keys[st.get(name)] = name;
        }

        // second pass builds the digraph by connecting first vertex on each
        // line to all others
        graph = new Digraph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = st.get(a[i]);
                graph.addEdge(v, w);
            }
        }
    }
**/
    /**
     * Does the digraph contain the vertex named {@code s}?
     * @param s the name of a vertex
     * @return {@code true} if {@code s} is the name of a vertex, and {@code false} otherwise
     */
    /**
    public boolean contains(String s) {
        return st.contains(s);
    }
     **/

    /**
     * Returns the integer associated with the vertex named {@code s}.
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
     * @deprecated Replaced by {@link #indexOf(String)}.
     */
    /**
    @Deprecated
    public int index(String s) {
        return st.get(s);
    }
    **/
    /**
     * Returns the integer associated with the vertex named {@code s}.
     * @param s the name of a vertex
     * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
     */
    /**
    public int indexOf(String s) {
        return st.get(s);
    }
**/
    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @return the name of the vertex associated with the integer {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     * @deprecated Replaced by {@link #nameOf(int)}.
     */
    /**
    @Deprecated
    public String name(int v) {
        validateVertex(v);
        return keys[v];
    }
    **/

    /**
     * Returns the name of the vertex associated with the integer {@code v}.
     * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
     * @return the name of the vertex associated with the integer {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    /**
    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }
     **/

    /**
     * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the digraph.
     *
     * @return the digraph associated with the symbol digraph
     * @deprecated Replaced by {@link #digraph()}.
     */
    /**
    @Deprecated
    public Digraph G() {
        return graph;
    }
    **/

    /**
     * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
     * not to mutate the digraph.
     *
     * @return the digraph associated with the symbol digraph
     */
    /**
    public Digraph digraph() {
        return graph;
    }
     **/

    /**
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
**/
    /**
     * Unit tests the {@code SymbolDigraph} data type.
     *
     * @param args the command-line arguments
     */
    /**
    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, delimiter);
        Digraph graph = sg.digraph();
        while (!StdIn.isEmpty()) {
            String t = StdIn.readLine();
            for (int v : graph.adj(sg.index(t))) {
                StdOut.println("   " + sg.name(v));
            }
        }
    }

}
     **/

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
