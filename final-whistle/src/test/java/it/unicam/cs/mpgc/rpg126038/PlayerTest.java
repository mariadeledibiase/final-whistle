package it.unicam.cs.mpgc.rpg126038;

import it.unicam.cs.mpgc.rpg126038.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitari per la classe Player.
 * Verifica il corretto aggiornamento delle statistiche e il calcolo del punteggio per ogni ruolo.
 */
class PlayerTest {

    /** Attaccante di test */
    private Player attaccante;

    /** Portiere di test */
    private Player portiere;

    /** Difensore di test */
    private Player difensore;

    /** Centrocampista di test */
    private Player centrocampista;

    /**
     * Inizializza i giocatori di test prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        attaccante = new Player("Mario Rossi", "Attaccante", 0, 0, 0, 0);
        portiere = new Player("Luca Bianchi", "Portiere", 0, 0, 0, 0);
        difensore = new Player("Andrea Ferrari", "Difensore", 0, 0, 0, 0);
        centrocampista = new Player("Davide Ricci", "Centrocampista", 0, 0, 0, 0);
    }

    /**
     * Verifica che i gol vengano incrementati correttamente.
     */
    @Test
    void testAddGoal() {
        attaccante.addGoal();
        attaccante.addGoal();
        assertEquals(2, attaccante.getGoals());
    }

    /**
     * Verifica che gli assist vengano incrementati correttamente.
     */
    @Test
    void testAddAssist() {
        centrocampista.addAssist();
        assertEquals(1, centrocampista.getAssists());
    }

    /**
     * Verifica che le presenze vengano incrementate correttamente.
     */
    @Test
    void testAddPresence() {
        difensore.addPresence();
        difensore.addPresence();
        difensore.addPresence();
        assertEquals(3, difensore.getPresences());
    }

    /**
     * Verifica che le parate vengano incrementate correttamente.
     */
    @Test
    void testAddSaves() {
        portiere.addSaves(5);
        assertEquals(5, portiere.getSaves());
    }

    /**
     * Verifica che il punteggio dell'attaccante dipenda principalmente dai gol.
     */
    @Test
    void testAttaccanteScoreDependsOnGoals() {
        attaccante.addGoal();
        attaccante.addGoal();
        double scoreConGol = attaccante.calculateScore();
        Player senzaGol = new Player("Test", "Attaccante", 0, 0, 0, 0);
        assertTrue(scoreConGol > senzaGol.calculateScore());
    }

    /**
     * Verifica che il punteggio del portiere dipenda principalmente dalle parate.
     */
    @Test
    void testPortiereScoreDependsOnSaves() {
        portiere.addSaves(10);
        double scoreConParate = portiere.calculateScore();
        Player senzaParate = new Player("Test", "Portiere", 0, 0, 0, 0);
        assertTrue(scoreConParate > senzaParate.calculateScore());
    }

    /**
     * Verifica che il nome e il ruolo del giocatore siano corretti.
     */
    @Test
    void testPlayerNameAndRole() {
        assertEquals("Mario Rossi", attaccante.getName());
        assertEquals("Attaccante", attaccante.getRole());
    }

    /**
     * Verifica che le statistiche iniziali siano tutte a zero.
     */
    @Test
    void testInitialStatsAreZero() {
        assertEquals(0, attaccante.getGoals());
        assertEquals(0, attaccante.getAssists());
        assertEquals(0, attaccante.getPresences());
        assertEquals(0, attaccante.getSaves());
    }
}