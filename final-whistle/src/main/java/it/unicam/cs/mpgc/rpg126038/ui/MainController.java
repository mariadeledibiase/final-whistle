package it.unicam.cs.mpgc.rpg126038.ui;

import it.unicam.cs.mpgc.rpg126038.engine.DefaultMatchEngine;
import it.unicam.cs.mpgc.rpg126038.persistence.JsonTournamentRepository;
import it.unicam.cs.mpgc.rpg126038.service.TournamentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * Controller della schermata principale dell'applicazione.
 */
public class MainController {

    /** Pulsante per iniziare una nuova partita */
    @FXML private Button btnNewGame;

    /**
     * Crea un nuovo servizio del torneo e apre la schermata di creazione della squadra.
     */
    @FXML
    private void onNewGame() {
        try {
            TournamentService tournamentService = new TournamentService(
                    new DefaultMatchEngine(),
                    new JsonTournamentRepository()
            );
            tournamentService.createTournament("Final Whistle");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTeamView.fxml"));
            Stage stage = (Stage) btnNewGame.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            CreateTeamController controller = loader.getController();
            controller.setTournamentService(tournamentService);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Chiude l'applicazione.
     */
    @FXML
    private void onExit() {
        System.exit(0);
    }
}