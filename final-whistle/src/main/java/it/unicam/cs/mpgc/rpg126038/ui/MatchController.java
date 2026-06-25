package it.unicam.cs.mpgc.rpg126038.ui;

import it.unicam.cs.mpgc.rpg126038.model.*;
import it.unicam.cs.mpgc.rpg126038.service.TournamentService;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller della schermata di gioco della partita.
 * Mostra gli eventi in tempo reale con animazioni di vittoria e sconfitta, permette di cambiare tattica e gestisce il passaggio al turno successivo.
 */
public class MatchController {

    @FXML private Label lblMatchTitle;
    @FXML private Label lblScore;
    @FXML private Label lblTactic;
    @FXML private Label lblResult;
    @FXML private ListView<String> lstEvents;
    @FXML private Button btnPlay;
    @FXML private Button btnContinue;
    @FXML private VBox root;

    private TournamentService tournamentService;
    private Team playerTeam;
    private Match currentMatch;
    private boolean playerWon = false;

    /**
     * Inizializza il controller nascondendo il pulsante continua e il label risultato.
     */
    @FXML
    public void initialize() {
        btnContinue.setVisible(false);
        btnContinue.setManaged(false);
        lblResult.setVisible(false);
        lblResult.setManaged(false);
    }

    /**
     * Imposta il servizio, la squadra del giocatore e la partita corrente.
     */
    public void setMatch(TournamentService tournamentService, Team playerTeam, Match match) {
        this.tournamentService = tournamentService;
        this.playerTeam = playerTeam;
        this.currentMatch = match;

        String opponent = match.getHomeTeam().equals(playerTeam) ?
                match.getAwayTeam().getName() : match.getHomeTeam().getName();

        lblMatchTitle.setText(playerTeam.getName() + " vs " + opponent);
        lblScore.setText("? - ?");
        lblTactic.setText("Tattica: " + tacticToItalian(playerTeam.getTactic().name()));
        lstEvents.getItems().add("Scegli la tattica e clicca Gioca la Partita!");
    }

    /**
     * Avvia la simulazione della partita e mostra gli eventi con animazione del punteggio.
     */
    @FXML
    private void onPlayMatch() {
        if (currentMatch.isPlayed()) return;

        lstEvents.getItems().clear();
        lstEvents.getItems().add("--- FISCHIO INIZIALE ---");

        tournamentService.getMatchEngine().simulate(currentMatch);
        MatchResult result = currentMatch.getResult();

        for (MatchEvent event : result.getEvents()) {
            lstEvents.getItems().add(event.getDescription());
        }

        lstEvents.getItems().add("--- FISCHIO FINALE ---");

        boolean isHome = currentMatch.getHomeTeam().equals(playerTeam);
        int myGoals = isHome ? result.getHomeGoals() : result.getAwayGoals();
        int theirGoals = isHome ? result.getAwayGoals() : result.getHomeGoals();

        // Animazione punteggio
        animateScore(myGoals, theirGoals);

        playerWon = result.getWinner() != null && result.getWinner().equals(playerTeam);

        // Simula le altre partite
        for (Match match : tournamentService.getTournament().getCurrentRound()) {
            if (!match.isPlayed()) {
                tournamentService.getMatchEngine().simulate(match);
            }
        }

        boolean isLastRound = tournamentService.getTournament().getWinners().size() == 1;

        if (playerWon) {
            if (isLastRound) {
                showVictoryAnimation("CAMPIONE DEL TORNEO!");
                btnContinue.setText("Vedi Statistiche Finali");
            } else {
                showVictoryAnimation("HAI VINTO! Vai alla finale!");
                btnContinue.setText("Vai alla Finale");
            }
        } else {
            showDefeatAnimation();
            btnContinue.setText("Ricomincia");
        }

        btnContinue.setVisible(true);
        btnContinue.setManaged(true);
        btnPlay.setDisable(true);
        tournamentService.saveTournament();
    }

    /**
     * Anima il punteggio con un effetto di scala.
     */
    private void animateScore(int myGoals, int theirGoals) {
        lblScore.setText(myGoals + " - " + theirGoals);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(lblScore.scaleXProperty(), 1),
                        new KeyValue(lblScore.scaleYProperty(), 1)),
                new KeyFrame(Duration.millis(300),
                        new KeyValue(lblScore.scaleXProperty(), 1.5),
                        new KeyValue(lblScore.scaleYProperty(), 1.5)),
                new KeyFrame(Duration.millis(600),
                        new KeyValue(lblScore.scaleXProperty(), 1),
                        new KeyValue(lblScore.scaleYProperty(), 1))
        );
        timeline.play();
    }

    /**
     * Mostra l'animazione di vittoria con sfondo verde brillante.
     */
    private void showVictoryAnimation(String message) {
        lblResult.setText("🏆 " + message);
        lblResult.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: #ffd700;");
        lblResult.setVisible(true);
        lblResult.setManaged(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(lblResult.opacityProperty(), 0),
                        new KeyValue(lblResult.scaleXProperty(), 0.5),
                        new KeyValue(lblResult.scaleYProperty(), 0.5)),
                new KeyFrame(Duration.millis(500),
                        new KeyValue(lblResult.opacityProperty(), 1),
                        new KeyValue(lblResult.scaleXProperty(), 1.2),
                        new KeyValue(lblResult.scaleYProperty(), 1.2)),
                new KeyFrame(Duration.millis(700),
                        new KeyValue(lblResult.scaleXProperty(), 1),
                        new KeyValue(lblResult.scaleYProperty(), 1))
        );
        timeline.play();

        // Sfondo verde brillante
        root.setStyle("-fx-background-color: #1b5e20; -fx-padding: 30;");
    }

    /**
     * Mostra l'animazione di sconfitta con sfondo rosso scuro.
     */
    private void showDefeatAnimation() {
        lblResult.setText("GAME OVER - Sei stato eliminato!");
        lblResult.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: #ff5252;");
        lblResult.setVisible(true);
        lblResult.setManaged(true);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(lblResult.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(800),
                        new KeyValue(lblResult.opacityProperty(), 1))
        );
        timeline.play();

        // Sfondo rosso scuro per la sconfitta
        root.setStyle("-fx-background-color: #7f0000; -fx-padding: 30;");
    }

    /**
     * Gestisce il pulsante continua.
     */
    @FXML
    private void onContinue() {
        if (!playerWon) {
            goToStats();
            return;
        }

        if (tournamentService.getTournament().isFinished()) {
            goToStats();
            return;
        }

        tournamentService.nextRound();

        Match playerMatch = tournamentService.getTournament().getCurrentRound().stream()
                .filter(m -> m.getHomeTeam().equals(playerTeam) ||
                        m.getAwayTeam().equals(playerTeam))
                .findFirst()
                .orElse(null);

        if (playerMatch == null) {
            goToStats();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MatchView.fxml"));
            Stage stage = (Stage) lstEvents.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            MatchController controller = loader.getController();
            controller.setMatch(tournamentService, playerTeam, playerMatch);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Torna alla schermata principale.
     */
    private void goToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Stage stage = (Stage) lstEvents.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vai alla schermata delle statistiche.
     */
    private void goToStats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StatsView.fxml"));
            Stage stage = (Stage) lstEvents.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            StatsController controller = loader.getController();
            controller.setTournamentService(tournamentService);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Imposta tattica offensiva */
    @FXML
    private void onSetOffensive() {
        playerTeam.setTactic(Tactic.OFFENSIVE);
        lblTactic.setText("Tattica: Offensiva");
    }

    /** Imposta tattica difensiva */
    @FXML
    private void onSetDefensive() {
        playerTeam.setTactic(Tactic.DEFENSIVE);
        lblTactic.setText("Tattica: Difensiva");
    }

    /** Imposta tattica bilanciata */
    @FXML
    private void onSetBalanced() {
        playerTeam.setTactic(Tactic.BALANCED);
        lblTactic.setText("Tattica: Bilanciata");
    }

    /**
     * Converte la tattica da inglese a italiano.
     */
    private String tacticToItalian(String tactic) {
        return switch (tactic) {
            case "OFFENSIVE" -> "Offensiva";
            case "DEFENSIVE" -> "Difensiva";
            default -> "Bilanciata";
        };
    }
}