package it.unicam.cs.mpgc.rpg126038;

import it.unicam.cs.mpgc.rpg126038.engine.DefaultMatchEngine;
import it.unicam.cs.mpgc.rpg126038.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitari per il motore di simulazione delle partite.
 * Verifica che il motore generi risultati corretti e coerenti con le regole del torneo a eliminazione diretta.
 */
class MatchEngineTest {

    /** Motore di simulazione */
    private DefaultMatchEngine engine;

    /** Squadra offensiva di test */
    private Team offensiveTeam;

    /** Squadra difensiva di test */
    private Team defensiveTeam;

    /**
     * Inizializza i dati di test prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        engine = new DefaultMatchEngine();

        offensiveTeam = new Team("Offensiva FC", Tactic.OFFENSIVE);
        defensiveTeam = new Team("Difensiva FC", Tactic.DEFENSIVE);

        for (int i = 0; i < 5; i++) {
            offensiveTeam.addPlayer(new Player("Giocatore O" + i, "Attaccante", 0, 0, 0, 0));
            defensiveTeam.addPlayer(new Player("Giocatore D" + i, "Difensore", 0, 0, 0, 0));
        }
    }

    /**
     * Verifica che il risultato non sia mai nullo dopo la simulazione.
     */
    @Test
    void testResultIsNotNull() {
        Match match = new Match(offensiveTeam, defensiveTeam);
        MatchResult result = engine.simulate(match);
        assertNotNull(result);
    }

    /**
     * Verifica che i gol non siano mai negativi.
     */
    @Test
    void testGoalsAreNotNegative() {
        Match match = new Match(offensiveTeam, defensiveTeam);
        MatchResult result = engine.simulate(match);
        assertTrue(result.getHomeGoals() >= 0);
        assertTrue(result.getAwayGoals() >= 0);
    }

    /**
     * Verifica che non ci siano mai pareggi nel torneo a eliminazione.
     */
    @Test
    void testNoDraws() {
        for (int i = 0; i < 20; i++) {
            Match match = new Match(offensiveTeam, defensiveTeam);
            MatchResult result = engine.simulate(match);
            assertNotEquals(result.getHomeGoals(), result.getAwayGoals(),
                    "Non ci devono essere pareggi nel torneo a eliminazione");
        }
    }

    /**
     * Verifica che la partita venga marcata come giocata dopo la simulazione.
     */
    @Test
    void testMatchIsPlayedAfterSimulation() {
        Match match = new Match(offensiveTeam, defensiveTeam);
        assertFalse(match.isPlayed());
        engine.simulate(match);
        assertTrue(match.isPlayed());
    }

    /**
     * Verifica che gli eventi generati abbiano minuti validi (1-90).
     */
    @Test
    void testEventsHaveValidMinutes() {
        Match match = new Match(offensiveTeam, defensiveTeam);
        MatchResult result = engine.simulate(match);
        result.getEvents().forEach(event -> {
            assertTrue(event.getMinute() >= 1 && event.getMinute() <= 90,
                    "Il minuto dell'evento deve essere tra 1 e 90");
        });
    }

    /**
     * Verifica che il vincitore sia sempre una delle due squadre.
     */
    @Test
    void testWinnerIsOneOfTheTeams() {
        Match match = new Match(offensiveTeam, defensiveTeam);
        MatchResult result = engine.simulate(match);
        Team winner = result.getWinner();
        assertTrue(winner.equals(offensiveTeam) || winner.equals(defensiveTeam),
                "Il vincitore deve essere una delle due squadre");
    }
}