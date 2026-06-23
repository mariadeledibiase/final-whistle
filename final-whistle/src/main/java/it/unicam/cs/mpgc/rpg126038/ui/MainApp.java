package it.unicam.cs.mpgc.rpg126038.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale dell'interfaccia grafica
 * Si occupa di avviare la finestra principale dell'applicazione e caricare la schermata iniziale.
 */
public class MainApp extends Application {

    /**
     * Metodo chiamato da JavaFX per avviare l'interfaccia grafica.
     * Carica il file FXML della schermata principale.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Final Whistle");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo di ingresso per il lancio dell'applicazione JavaFX
     */
    public static void main(String[] args) {
        launch(args);
    }
}