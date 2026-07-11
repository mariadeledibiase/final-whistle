package it.unicam.cs.mpgc.rpg126038.ui;

import it.unicam.cs.mpgc.rpg126038.model.Formation;
import it.unicam.cs.mpgc.rpg126038.model.Player;
import it.unicam.cs.mpgc.rpg126038.model.Tactic;
import it.unicam.cs.mpgc.rpg126038.model.Team;
import it.unicam.cs.mpgc.rpg126038.service.TournamentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller della schermata di creazione della squadra del giocatore.
 * Permette di inserire nome, tattica e giocatori.
 * Riceve il servizio del torneo dal controller precedente invece di usare campi statici, rispettando il principio
 * di inversione delle dipendenze (SOLID).
 */
public class CreateTeamController {

    /** Numero minimo di giocatori per squadra */
    private static final int MIN_PLAYERS = 5;

    /** Numero massimo di giocatori per squadra */
    private static final int MAX_PLAYERS = 11;

    /** Campo per il nome della squadra */
    @FXML private TextField txtTeamName;

    /** ComboBox per la tattica */
    @FXML private ComboBox<String> cmbTactic;

    /** ComboBox per lo schema di gioco */
    @FXML private ComboBox<String> cmbFormation;

    /** Campo per il nome del giocatore */
    @FXML private TextField txtPlayerName;

    /** ComboBox per il ruolo del giocatore */
    @FXML private ComboBox<String> cmbRole;

    /** Lista dei giocatori aggiunti */
    @FXML private ListView<String> lstPlayers;

    /** Etichetta che mostra il numero di giocatori aggiunti */
    @FXML private Label lblPlayerCount;

    /** La squadra del giocatore */
    private Team playerTeam;

    /** Servizio del torneo ricevuto dal controller precedente */
    private TournamentService tournamentService;

    /**
     * Inizializza il controller configurando le ComboBox.
     */
    @FXML
    public void initialize() {
        cmbTactic.getItems().addAll("Offensiva", "Difensiva", "Bilanciata");
        cmbTactic.setValue("Bilanciata");

        cmbFormation.getItems().addAll("4-3-3", "4-4-2", "3-5-2", "5-3-2");
        cmbFormation.setValue("4-4-2");

        cmbRole.getItems().addAll("Portiere", "Difensore", "Centrocampista", "Attaccante");
        cmbRole.setValue("Centrocampista");
    }

    /**
     * Imposta il servizio del torneo ricevuto dal controller precedente.
     */
    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

/**
 * Converte la stringa della tattica nell'enum corrispondente.
 */
private Tactic parseTactic(String tactic) {
    return switch (tactic) {
        case "Offensiva" -> Tactic.OFFENSIVE;
        case "Difensiva" -> Tactic.DEFENSIVE;
        default -> Tactic.BALANCED;
    };
}

/**
 * Converte la stringa dello schema nell'enum corrispondente.
 */
private Formation parseFormation(String formation) {
    return switch (formation) {
        case "4-3-3" -> Formation.F_4_3_3;
        case "3-5-2" -> Formation.F_3_5_2;
        case "5-3-2" -> Formation.F_5_3_2;
        default -> Formation.F_4_4_2;
    };
}
    /**
     * Aggiunge un giocatore alla squadra con nome e ruolo.
     * Non permette di aggiungere più di MAX_PLAYERS giocatori.
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

        // Controlla il limite massimo di giocatori
        if (playerTeam.getPlayers().size() >= MAX_PLAYERS) return;

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
        updatePlayerCount();
    }

    /**
     * Rimuove il giocatore selezionato dalla lista e dalla squadra.
     */
    @FXML
    private void onRemovePlayer() {
        int index = lstPlayers.getSelectionModel().getSelectedIndex();
        if (index < 0) return;
        lstPlayers.getItems().remove(index);
        playerTeam.removePlayer(playerTeam.getPlayers().get(index));
        updatePlayerCount();
    }

    /**
     * Aggiorna l'etichetta del contatore giocatori.
     */
    private void updatePlayerCount() {
        int count = playerTeam != null ? playerTeam.getPlayers().size() : 0;
        lblPlayerCount.setText("Giocatori: " + count + " / " + MAX_PLAYERS +
                (count < MIN_PLAYERS ? "  (minimo " + MIN_PLAYERS + " per iniziare)" : "  ✓ Pronto!"));
    }
    /**
     * Genera le squadre avversarie con lo stesso numero di giocatori della squadra del giocatore, rendendole completamente bilanciate.
     */
    private void generateOpponents(int count) {
        Random random = new Random();
        String[] prefixes = {"FC", "Real", "Athletic", "Sporting", "United", "AC"};
        String[] cities = {"Pescara", "Chieti", "Camerino", "Macerata", "Guardiagrele", "Tolentino"};
        String[] firstNames = {"Marco", "Luca", "Andrea", "Davide", "Matteo",
                "Giovanni", "Roberto", "Francesco", "Alessandro", "Simone"};
        String[] lastNames = {"Di Crescenzo", "Di Biase", "Di Marzio", "Felicioni", "Esposito",
                "Paolucci", "Primavera", "Ricci", "Di Fabio", "Del Greco"};
        String[] roles = {"Portiere", "Difensore", "Difensore",
                "Centrocampista", "Centrocampista", "Attaccante", "Attaccante"};
        Tactic[] tactics = {Tactic.OFFENSIVE, Tactic.DEFENSIVE, Tactic.BALANCED};
        Formation[] formations = {Formation.F_4_3_3, Formation.F_4_4_2,
                Formation.F_3_5_2, Formation.F_5_3_2};
        List<String> usedNames = new ArrayList<>();

        // Numero di giocatori uguale alla squadra del giocatore
        int playerCount = playerTeam.getPlayers().size();

        for (int i = 0; i < count; i++) {
            String teamName;
            do {
                teamName = prefixes[random.nextInt(prefixes.length)] + " " +
                        cities[random.nextInt(cities.length)];
            } while (usedNames.contains(teamName));
            usedNames.add(teamName);

            Team opponent = new Team(teamName,
                    tactics[random.nextInt(tactics.length)],
                    formations[random.nextInt(formations.length)]);

            for (int j = 0; j < playerCount; j++) {
                String role = roles[j % roles.length];
                String pName = firstNames[random.nextInt(firstNames.length)] + " " +
                        lastNames[random.nextInt(lastNames.length)];
                opponent.addPlayer(new Player(pName, role, 0, 0, 0, 0));
            }
            tournamentService.addTeam(opponent);
        }
    }


    /**
     * Avvia il torneo aggiungendo la squadra del giocatore, generando gli avversari e aprendo la schermata di gioco.
     * Richiede almeno MIN_PLAYERS giocatori per avviare il torneo.
     */
    @FXML
    private void onStartTournament() {
        if (playerTeam == null || playerTeam.getPlayers().size() < MIN_PLAYERS) return;

        tournamentService.addTeam(playerTeam);
        generateOpponents(3);
        tournamentService.startTournament();
        tournamentService.saveTournament();

        var playerMatch = tournamentService.getTournament().getCurrentRound().stream()
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
            controller.setMatch(tournamentService, playerTeam, playerMatch);
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