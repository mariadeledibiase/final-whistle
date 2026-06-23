package it.unicam.cs.mpgc.rpg126038.ui;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controller della schermata principale dell'applicazione.
 * Gestisce gli eventi dei pulsanti della schermata iniziale.
 */
public class MainController {

    /**
     * Apre la schermata per creare un nuovo torneo.
     */
    @FXML
    private void onNewTournament() {
        System.out.println("Nuovo torneo");
    }

    /**
     * Apre la schermata per caricare un torneo salvato.
     */
    @FXML
    private void onLoadTournament() {
        System.out.println("Carica torneo");
    }

    /**
     * Chiude l'applicazione.
     */
    @FXML
    private void onExit() {
        Stage stage = new Stage();
        stage.close();
        System.exit(0);
    }
}