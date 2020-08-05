package edu.ufp.inf.Lp2Fase_1_.GUI;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.ufp.inf.Lp2Fase_1_.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.PropertyPermission;
import java.util.ResourceBundle;

public class GUIController implements Initializable {
    public TableView<Aluno> TabelaAlunos;
    public TableColumn<Aluno,String> alNomeCol;
    public TableColumn<Aluno,String> alNumCol;
    public TableColumn<Aluno,String> alEmailCol;
    public TableColumn<Aluno,String> alCursoCol;
    public TableColumn<Aluno,String> alCCCol;

    public TableView<Professor> TabelaProfessores;
    public TableColumn<Professor,String> profNomeCol;
    public TableColumn<Professor,String> profEmailCol;
    public TableColumn<Professor,Horario> profHorarioCol;

    public TableView<Sala>TabelaSala;
    public TableColumn<Sala,Integer> salLotacaoCol;
    public TableColumn<Sala,Integer> salNumeroCol;
    public TableColumn<Sala,Double> salPisoCol;
    public TableColumn<Sala,Ponto> salLocalCol;

    public TableView<Turma> TabelaTurmas;
    public TableColumn<Turma,String> turIDCol;
    public TableColumn<Turma,String> turUcCol;
    public TableColumn<Turma,String> turNAlunosCol;
    public TableColumn<Turma,String> turProfCol;
    public TableColumn<Turma,Horario> turHorarioCol;

    public TableView <Horario> TabelaHorarios;
    public TableColumn<Horario,Data> horInicioCol;
    public TableColumn<Horario,Data> horFimCol;
    private Uni uni;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
      uni=new Uni("Universidade Fernando Pessoar",0,2);
      alNomeCol.setCellValueFactory(new PropertyValueFactory<>("Nome"));
      alNumCol.setCellValueFactory(new PropertyValueFactory<>("nr_aluno"));
      alEmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
      alCursoCol.setCellValueFactory(new PropertyValueFactory<>("Curso"));
      alCCCol.setCellValueFactory(new PropertyValueFactory<>("cc"));

      profNomeCol.setCellValueFactory((new PropertyValueFactory<>("Nome")));
      profEmailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
      profHorarioCol.setCellValueFactory(new PropertyValueFactory<>("horario_atend"));

      salLotacaoCol.setCellValueFactory(new PropertyValueFactory<>("Lotacao_max"));
      salNumeroCol.setCellValueFactory(new PropertyValueFactory<>("Nr_sala"));
      salPisoCol.setCellValueFactory(new PropertyValueFactory<>("Piso"));
      salLocalCol.setCellValueFactory(new PropertyValueFactory<>("Ponto"));

      turIDCol.setCellValueFactory(new PropertyValueFactory<>("Turma_id"));
      turNAlunosCol.setCellValueFactory(new PropertyValueFactory<>("Nr_alunos"));
      turUcCol.setCellValueFactory(new PropertyValueFactory<>("NomeUc"));
      turHorarioCol.setCellValueFactory(new PropertyValueFactory<>("Horario_aula"));
      turProfCol.setCellValueFactory(new PropertyValueFactory<>("MainProfessor"));

      horInicioCol.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
      horFimCol.setCellValueFactory(new PropertyValueFactory<>("DataFim"));

    }

    public void handleReadFromTxtAction(ActionEvent actionEvent) throws IOException {
        uni.populateUni();
        TabelaAlunos.getItems().addAll(uni.getArrayAlunos());
        TabelaProfessores.getItems().addAll(uni.getArrayProfessores());
        TabelaSala.getItems().addAll(uni.getArraySalas());
        TabelaTurmas.getItems().addAll(uni.getArrayTurmas());
        TabelaHorarios.getItems().addAll(uni.getArrayHorarios());

    }

    public void handleReadFromBinAction(ActionEvent actionEvent) {
    }

    public void handleSaveToTxtAction(ActionEvent actionEvent) {
    }

    public void handleSaveToBinAction(ActionEvent actionEvent) {
    }

    public void handleExitAction(ActionEvent actionEvent) {
    }

    public void handleAboutAction(ActionEvent actionEvent) {
    }
}
