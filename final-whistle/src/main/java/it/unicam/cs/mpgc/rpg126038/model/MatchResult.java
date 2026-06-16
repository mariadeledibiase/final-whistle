package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta il risultato di una partita tra due squadre.
 * Contiene i gol segnati da ciascuna squadra e il riferimento al vincitore.
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

    /**
     * Costruttore che inizializza il risultato di una partita.
     */
    public MatchResult(Team homeTeam, Team awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
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

    /**
     * Restituisce una rappresentazione testuale del risultato.
     */
    @Override
    public String toString() {
        return homeTeam.getName() + " " + homeGoals + " - " + awayGoals + " " + awayTeam.getName();
    }
}