package edu.ufp.inf.Lp2Fase_1_.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import static javafx.application.Application.launch;

public class MainGUI extends Application {
    public static void main(String[] args) { launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UNIGUI.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setWidth(800.0);
        primaryStage.setHeight(600.0);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Universidade Fernando Pessoa");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
