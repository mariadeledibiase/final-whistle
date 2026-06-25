package it.unicam.cs.mpgc.rpg126038.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta il risultato di una partita tra due squadre.
 * Contiene i gol segnati da ciascuna squadra, il riferimento al vincitore e la lista degli eventi accaduti durante la partita.
 */
public class MatchResult {

    /** Squadra di casa */
    private Team homeTeam;

    /** Squadra ospite */
    private Team awayTeam;

    /** Gol segnati dalla squadra di casa */
    private int homeGoals;

    /** Gol segnati dalla squadra ospite */
    private int awayGoals;

    /** Lista degli eventi accaduti durante la partita */
    private List<MatchEvent> events;

    /**
     * Costruttore che inizializza il risultato di una partita.
     */
    public MatchResult(Team homeTeam, Team awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.events = new ArrayList<>();
    }

    /**
     * Aggiunge un evento alla lista degli eventi della partita.
     */
    public void addEvent(MatchEvent event) {
        events.add(event);
    }

    /**
     * Restituisce il vincitore della partita.
     * In caso di pareggio viene restituito null.
     */
    public Team getWinner() {
        if (homeGoals > awayGoals) return homeTeam;
        if (awayGoals > homeGoals) return awayTeam;
        return null;
    }

    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public int getHomeGoals() { return homeGoals; }
    public int getAwayGoals() { return awayGoals; }
    public List<MatchEvent> getEvents() { return events; }

    /**
     * Restituisce una rappresentazione testuale del risultato.
     */
    @Override
    public String toString() {
        return homeTeam.getName() + " " + homeGoals + " - " + awayGoals + " " + awayTeam.getName();
    }
}