package it.unicam.cs.mpgc.rpg126038.ui;

import it.unicam.cs.mpgc.rpg126038.model.Player;
import it.unicam.cs.mpgc.rpg126038.model.Tactic;
import it.unicam.cs.mpgc.rpg126038.model.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller della schermata di creazione della squadra del giocatore.
 * Permette di inserire nome, tattica e giocatori.
 * Una volta confermata, genera automaticamente le squadre avversarie con statistiche bilanciate e avvia il torneo.
 */
public class CreateTeamController {

    /** Campo per il nome della squadra */
    @FXML private TextField txtTeamName;

    /** ComboBox per la tattica */
    @FXML private ComboBox<String> cmbTactic;

    /** Campo per il nome del giocatore */
    @FXML private TextField txtPlayerName;

    /** ComboBox per il ruolo del giocatore */
    @FXML private ComboBox<String> cmbRole;

    /** Lista dei giocatori aggiunti */
    @FXML private ListView<String> lstPlayers;

    /** La squadra del giocatore */
    private Team playerTeam;

    /**
     * Inizializza il controller configurando le ComboBox.
     */
    @FXML
    public void initialize() {
        cmbTactic.getItems().addAll("Offensiva", "Difensiva", "Bilanciata");
        cmbTactic.setValue("Bilanciata");
        cmbRole.getItems().addAll("Portiere", "Difensore", "Centrocampista", "Attaccante");
        cmbRole.setValue("Centrocampista");
    }

    /**
     * Aggiunge un giocatore alla squadra con nome e ruolo.
     */
    @FXML
    private void onAddPlayer() {
        String name = txtPlayerName.getText().trim();
        if (name.isEmpty()) return;

        if (playerTeam == null) {
            String teamName = txtTeamName.getText().trim();
            if (teamName.isEmpty()) return;
            Tactic tactic = switch (cmbTactic.getValue()) {
                case "Offensiva" -> Tactic.OFFENSIVE;
                case "Difensiva" -> Tactic.DEFENSIVE;
                default -> Tactic.BALANCED;
            };
            playerTeam = new Team(teamName, tactic);
        }

        Player player = new Player(name, cmbRole.getValue(), 0, 0, 0, 0);
        playerTeam.addPlayer(player);

        String ruolo = cmbRole.getValue();
        String iniziale = switch (ruolo) {
            case "Portiere" -> "P";
            case "Difensore" -> "D";
            case "Centrocampista" -> "C";
            case "Attaccante" -> "A";
            default -> "?";
        };
        lstPlayers.getItems().add(iniziale + "   " + name + "   ·   " + ruolo);
        txtPlayerName.clear();
    }

    /**
     * Genera le squadre avversarie con statistiche bilanciate.
     */
    private void generateOpponents(int count) {
        Random random = new Random();
        String[] prefixes = {"FC", "Real", "Athletic", "Sporting", "United", "AC"};
        String[] cities = {"Pescara", "Chieti", "Camerino", "Macerata", "Guardiagrele", "Tolentino"};
        String[] firstNames = {"Marco", "Luca", "Andrea", "Davide", "Matteo",
                "Giovanni", "Lorenzo", "Francesco", "Alessandro", "Jacopo"};
        String[] lastNames = {"Di Crescenzo", "Di Biase", "Di Marzio", "Felicioni", "Esposito",
                "Paolucci", "Primavera", "Ricci", "Di Fabio", "Del Greco"};
        String[] roles = {"Portiere", "Difensore", "Difensore",
                "Centrocampista", "Centrocampista", "Attaccante", "Attaccante"};
        Tactic[] tactics = {Tactic.OFFENSIVE, Tactic.DEFENSIVE, Tactic.BALANCED};
        List<String> usedNames = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String teamName;
            do {
                teamName = prefixes[random.nextInt(prefixes.length)] + " " +
                        cities[random.nextInt(cities.length)];
            } while (usedNames.contains(teamName));
            usedNames.add(teamName);

            Team opponent = new Team(teamName, tactics[random.nextInt(tactics.length)]);
            for (String role : roles) {
                String pName = firstNames[random.nextInt(firstNames.length)] + " " +
                        lastNames[random.nextInt(lastNames.length)];
                opponent.addPlayer(new Player(pName, role, 0, 0, 0, 0));
            }
            MainController.getTournamentService().addTeam(opponent);
        }
    }
    /**
     * Avvia il torneo aggiungendo la squadra del giocatore, generando gli avversari e aprendo la schermata di gioco.
     */
    @FXML
    private void onStartTournament() {
        if (playerTeam == null || playerTeam.getPlayers().isEmpty()) return;

        MainController.getTournamentService().addTeam(playerTeam);
        generateOpponents(3);
        MainController.getTournamentService().startTournament();
        MainController.getTournamentService().saveTournament();

        var playerMatch = MainController.getTournamentService()
                .getTournament().getCurrentRound().stream()
                .filter(m -> m.getHomeTeam().equals(playerTeam) ||
                        m.getAwayTeam().equals(playerTeam))
                .findFirst()
                .orElse(null);

        if (playerMatch == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MatchView.fxml"));
            Stage stage = (Stage) txtTeamName.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            MatchController controller = loader.getController();
            controller.setMatch(MainController.getTournamentService(), playerTeam, playerMatch);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Torna alla schermata principale.
     */
    @FXML
    private void onBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Stage stage = (Stage) txtTeamName.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}