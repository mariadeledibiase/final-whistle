package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta una partita tra due squadre all'interno del torneo.
 * Contiene le due squadre coinvolte e il risultato della partita, che viene impostato dopo la simulazione.
 */
public class Match {

    /** Squadra di casa */
    private Team homeTeam;

    /** Squadra ospite */
    private Team awayTeam;

    /** Risultato della partita, null se non ancora giocata */
    private MatchResult result;

    /**
     * Costruttore che inizializza una partita tra due squadre.
     */
    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.result = null;
    }

    /**
     * Indica se la partita è già stata giocata.
     */
    public boolean isPlayed() {
        return result != null;
    }

    /**
     * Imposta il risultato della partita.
     */
    public void setResult(MatchResult result) {
        this.result = result;
    }

    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public MatchResult getResult() { return result; }

    /**
     * Restituisce una rappresentazione testuale della partita.
     */
    @Override
    public String toString() {
        if (isPlayed()) return result.toString();
        return homeTeam.getName() + " vs " + awayTeam.getName();
    }
}