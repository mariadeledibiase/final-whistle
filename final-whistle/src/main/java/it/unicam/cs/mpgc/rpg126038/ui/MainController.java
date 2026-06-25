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
 * Controller della schermata principale.
 * Gestisce l'avvio di una nuova partita e la creazione e del servizio del torneo condiviso tra le schermate.
 */
public class MainController {

    /** Pulsante per iniziare una nuova partita */
    @FXML private Button btnNewGame;

    /** Servizio condiviso del torneo — ricreato ad ogni nuova partita */
    private static TournamentService tournamentService;

    /**
     * Restituisce il servizio del torneo condiviso.
     */
    public static TournamentService getTournamentService() {
        return tournamentService;
    }

    /**
     * Crea un nuovo servizio e apre la schermata di creazione squadra.
     */
    @FXML
    private void onNewGame() {
        // Ricrea sempre un nuovo servizio ad ogni partita
        tournamentService = new TournamentService(
                new DefaultMatchEngine(),
                new JsonTournamentRepository()
        );
        tournamentService.createTournament("Final Whistle");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateTeamView.fxml"));
            Stage stage = (Stage) btnNewGame.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
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