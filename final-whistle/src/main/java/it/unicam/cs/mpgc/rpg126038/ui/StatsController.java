package it.unicam.cs.mpgc.rpg126038.ui;

import it.unicam.cs.mpgc.rpg126038.model.Match;
import it.unicam.cs.mpgc.rpg126038.model.Player;
import it.unicam.cs.mpgc.rpg126038.model.Team;
import it.unicam.cs.mpgc.rpg126038.service.TournamentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Controller della schermata delle statistiche.
 * Mostra la classifica delle squadre e le statistiche dei giocatori.
 * Il pulsante principale cambia in base al risultato: "Ricomincia" se il giocatore ha perso, "Gioca Nuova Partita" se ha vinto.
 */
public class StatsController {

    /** Lista con le statistiche delle squadre */
    @FXML private ListView<String> lstTeamStats;

    /** Lista con le statistiche dei giocatori */
    @FXML private ListView<String> lstPlayerStats;

    /** Pulsante principale che cambia in base al risultato */
    @FXML private Button btnAction;

    /** Servizio che gestisce la logica del torneo */
    private TournamentService tournamentService;

    /** La squadra del giocatore */
    private Team playerTeam;

    /** Indica se il giocatore ha vinto il torneo */
    private boolean playerWon;

    /**
     * Imposta il servizio del torneo e popola le statistiche.
     */
    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
        this.playerTeam = tournamentService.getTournament().getTeams().get(0);
        this.playerWon = tournamentService.getTournament().getWinners().contains(playerTeam);
        populateTeamStats();
        populatePlayerStats();
        updateButton();
    }

    /**
     * Aggiorna il testo del pulsante in base al risultato.
     */
    private void updateButton() {
        if (playerWon && tournamentService.getTournament().isFinished()) {
            btnAction.setText("Gioca Nuova Partita");
            btnAction.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; " +
                    "-fx-font-size: 14; -fx-font-weight: bold; " +
                    "-fx-min-width: 220; -fx-padding: 13; " +
                    "-fx-background-radius: 10; -fx-cursor: hand;");
        } else if (!playerWon) {
            btnAction.setText("Ricomincia");
            btnAction.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; " +
                    "-fx-font-size: 14; -fx-font-weight: bold; " +
                    "-fx-min-width: 220; -fx-padding: 13; " +
                    "-fx-background-radius: 10; -fx-cursor: hand;");
        } else {
            btnAction.setText("Turno Successivo");
            btnAction.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white; " +
                    "-fx-font-size: 14; -fx-font-weight: bold; " +
                    "-fx-min-width: 220; -fx-padding: 13; " +
                    "-fx-background-radius: 10; -fx-cursor: hand;");
        }
    }

    /**
     * Gestisce il pulsante principale in base al risultato.
     */
    @FXML
    private void onAction() {
        if (playerWon && tournamentService.getTournament().isFinished()) {
            goToMainMenu();
        } else if (!playerWon) {
            goToMainMenu();
        } else {
            goToNextRound();
        }
    }

    /**
     * Avanza al turno successivo.
     */
    private void goToNextRound() {
        tournamentService.nextRound();

        for (Match match : tournamentService.getTournament().getCurrentRound()) {
            if (!match.getHomeTeam().equals(playerTeam) &&
                    !match.getAwayTeam().equals(playerTeam)) {
                tournamentService.getMatchEngine().simulate(match);
            }
        }

        Match playerMatch = tournamentService.getTournament().getCurrentRound().stream()
                .filter(m -> m.getHomeTeam().equals(playerTeam) ||
                        m.getAwayTeam().equals(playerTeam))
                .findFirst()
                .orElse(null);

        if (playerMatch == null) {
            goToMainMenu();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MatchView.fxml"));
            Stage stage = (Stage) lstTeamStats.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            MatchController controller = loader.getController();
            controller.setMatch(tournamentService, playerTeam, playerMatch);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * Popola la classifica squadre.
     */
    private void populateTeamStats() {
        lstTeamStats.getItems().clear();
        tournamentService.getTournament().getTeams().stream()
                .sorted((t1, t2) -> Double.compare(
                        t2.calculateStrength(), t1.calculateStrength()))
                .forEach(team -> {
                    String label = team.equals(playerTeam) ?
                            "★  " + team.getName().toUpperCase() + "  (TU)" :
                            "·  " + team.getName();
                    lstTeamStats.getItems().add(
                            label +
                                    "     Tattica: " + tacticToItalian(team.getTactic().name()) +
                                    "  ·  Giocatori: " + team.getPlayers().size()
                    );
                });
    }

    /**
     * Popola le statistiche giocatori.
     */
    private void populatePlayerStats() {
        lstPlayerStats.getItems().clear();
        for (Team team : tournamentService.getTournament().getTeams()) {
            String teamLabel = team.equals(playerTeam) ?
                    "★  " + team.getName().toUpperCase() + "  (LA TUA SQUADRA)" :
                    "·  " + team.getName().toUpperCase();
            lstPlayerStats.getItems().add(teamLabel);
            for (Player player : team.getPlayers()) {
                String ruolo = player.getRole().substring(0, 1);
                String stats = "   " + ruolo + "  " + player.getName() +
                        "     Gol " + player.getGoals() +
                        "  ·  Assist " + player.getAssists() +
                        "  ·  Presenze " + player.getPresences();
                if (player.getRole().equals("Portiere")) {
                    stats += "  ·  Parate " + player.getSaves();
                }
                lstPlayerStats.getItems().add(stats);
            }
        }
    }

    /**
     * Torna alla schermata principale.
     */
    @FXML
    private void onMainMenu() {
        goToMainMenu();
    }

    /**
     * Naviga alla schermata principale.
     */
    private void goToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Stage stage = (Stage) lstTeamStats.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}