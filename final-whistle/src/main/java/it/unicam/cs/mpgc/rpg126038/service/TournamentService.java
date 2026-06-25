package it.unicam.cs.mpgc.rpg126038.service;

import it.unicam.cs.mpgc.rpg126038.engine.MatchEngine;
import it.unicam.cs.mpgc.rpg126038.model.Match;
import it.unicam.cs.mpgc.rpg126038.model.Team;
import it.unicam.cs.mpgc.rpg126038.model.Tournament;
import it.unicam.cs.mpgc.rpg126038.persistence.Repository;
import it.unicam.cs.mpgc.rpg126038.engine.MatchEngine;

import java.util.List;

/**
 * Servizio che gestisce la logica di business del torneo.
 * Fa da intermediario tra il modello e l'interfaccia grafica, coordinando la simulazione delle partite e la persistenza dei dati.
 */
public class TournamentService {

    /** Il torneo attualmente in gestione */
    private Tournament tournament;

    /** Il motore di simulazione delle partite */
    private final MatchEngine matchEngine;

    /** Il repository per la persistenza dei dati */
    private final Repository<Tournament> repository;

    /**
     * Costruttore che inizializza il servizio con il motore e il repository.
     */
    public TournamentService(MatchEngine matchEngine, Repository<Tournament> repository) {
        this.matchEngine = matchEngine;
        this.repository = repository;
    }

    /**
     * Crea un nuovo torneo con il nome specificato.
     */
    public void createTournament(String name) {
        this.tournament = new Tournament(name);
    }

    /**
     * Aggiunge una squadra al torneo corrente.
     */
    public void addTeam(Team team) {
        tournament.addTeam(team);
    }

    /**
     * Avvia il torneo generando il primo turno di partite.
     */
    public void startTournament() {
        tournament.generateRound(tournament.getTeams());
    }

    /**
     * Simula tutte le partite del turno corrente.
     */
    public void simulateCurrentRound() {
        for (Match match : tournament.getCurrentRound()) {
            if (!match.isPlayed()) {
                matchEngine.simulate(match);
            }
        }
    }

    /**
     * Avanza al turno successivo usando i vincitori del turno corrente.
     * Funziona solo se tutte le partite del turno sono state simulate.
     */
    public void nextRound() {
        if (!tournament.isCurrentRoundFinished()) return;
        List<Team> winners = tournament.getWinners();
        if (winners.size() > 1) {
            tournament.generateRound(winners);
        }
    }

    /**
     * Salva il torneo corrente tramite il repository.
     */
    public void saveTournament() {
        repository.save(tournament);
    }

    /**
     * Carica un torneo salvato tramite il repository.
     */
    public void loadTournament(String name) {
        repository.findByName(name).ifPresent(t -> this.tournament = t);
    }

    /**
     * Restituisce il torneo corrente.
     */
    public Tournament getTournament() {
        return tournament;
    }
    /**
     * Restituisce il motore di simulazione delle partite.
     */
    public MatchEngine getMatchEngine() {
        return matchEngine;
    }
}