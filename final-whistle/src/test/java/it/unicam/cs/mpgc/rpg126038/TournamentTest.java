package it.unicam.cs.mpgc.rpg126038;

import it.unicam.cs.mpgc.rpg126038.engine.DefaultMatchEngine;
import it.unicam.cs.mpgc.rpg126038.model.*;
import it.unicam.cs.mpgc.rpg126038.service.TournamentService;
import it.unicam.cs.mpgc.rpg126038.persistence.JsonTournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test unitari per le classi principali del gioco Final Whistle.
 * Verifica il corretto funzionamento del modello, del motore di simulazione e del servizio del torneo.
 */
class TournamentTest {

    /** Squadra di test casa */
    private Team homeTeam;

    /** Squadra di test ospite */
    private Team awayTeam;

    /** Motore di simulazione */
    private DefaultMatchEngine engine;

    /** Servizio del torneo */
    private TournamentService service;

    /**
     * Inizializza i dati di test prima di ogni test.
     */
    @BeforeEach
    void setUp() {
        homeTeam = new Team("Home Team", Tactic.OFFENSIVE);
        awayTeam = new Team("Away Team", Tactic.DEFENSIVE);

        for (int i = 0; i < 5; i++) {
            homeTeam.addPlayer(new Player("Giocatore H" + i, "Centrocampista", 0, 0, 0, 0));
            awayTeam.addPlayer(new Player("Giocatore A" + i, "Centrocampista", 0, 0, 0, 0));
        }

        engine = new DefaultMatchEngine();
        service = new TournamentService(engine, new JsonTournamentRepository());
    }

    /**
     * Verifica che una squadra venga creata correttamente con nome e tattica.
     */
    @Test
    void testTeamCreation() {
        assertEquals("Home Team", homeTeam.getName());
        assertEquals(Tactic.OFFENSIVE, homeTeam.getTactic());
        assertEquals(5, homeTeam.getPlayers().size());
    }

    /**
     * Verifica che il risultato di una partita abbia sempre un vincitore
     * (nessun pareggio nel torneo a eliminazione).
     */
    @Test
    void testMatchAlwaysHasWinner() {
        Match match = new Match(homeTeam, awayTeam);
        MatchResult result = engine.simulate(match);
        assertNotNull(result.getWinner(), "La partita deve avere sempre un vincitore");
    }

    /**
     * Verifica che il risultato della partita contenga eventi.
     */
    @Test
    void testMatchHasEvents() {
        Match match = new Match(homeTeam, awayTeam);
        MatchResult result = engine.simulate(match);
        assertFalse(result.getEvents().isEmpty(), "La partita deve generare eventi");
    }

    /**
     * Verifica che le presenze dei giocatori vengano aggiornate dopo la partita.
     */
    @Test
    void testPlayerPresencesUpdatedAfterMatch() {
        Match match = new Match(homeTeam, awayTeam);
        engine.simulate(match);
        homeTeam.getPlayers().forEach(p ->
                assertEquals(1, p.getPresences(), "Le presenze devono essere aggiornate"));
    }

    /**
     * Verifica che il torneo venga creato correttamente con il nome specificato.
     */
    @Test
    void testTournamentCreation() {
        service.createTournament("Final Whistle");
        assertNotNull(service.getTournament());
        assertEquals("Final Whistle", service.getTournament().getName());
    }

    /**
     * Verifica che il torneo generi correttamente le partite del primo turno.
     */
    @Test
    void testTournamentGeneratesRound() {
        service.createTournament("Final Whistle");
        service.addTeam(homeTeam);
        service.addTeam(awayTeam);
        service.startTournament();
        assertEquals(1, service.getTournament().getCurrentRound().size());
    }

    /**
     * Verifica che la tattica offensiva abbia forza maggiore di quella difensiva.
     */
    @Test
    void testTacticStrength() {
        Team offensive = new Team("Offensiva", Tactic.OFFENSIVE);
        Team defensive = new Team("Difensiva", Tactic.DEFENSIVE);
        assertTrue(offensive.calculateStrength() > defensive.calculateStrength(),
                "La tattica offensiva deve avere forza maggiore di quella difensiva");
    }

    /**
     * Verifica che un giocatore possa essere aggiunto e rimosso dalla squadra.
     */
    @Test
    void testAddAndRemovePlayer() {
        Player player = new Player("Test", "Attaccante", 0, 0, 0, 0);
        homeTeam.addPlayer(player);
        assertEquals(6, homeTeam.getPlayers().size());
        homeTeam.removePlayer(player);
        assertEquals(5, homeTeam.getPlayers().size());
    }
}