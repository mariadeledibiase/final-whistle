package it.unicam.cs.mpgc.rpg126038.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un torneo a eliminazione diretta.
 * Gestisce le squadre partecipanti e i turni di gioco, avanzando i vincitori al turno successivo fino alla finale.
 */
public class Tournament {

    /** Nome del torneo */
    private String name;

    /** Lista delle squadre partecipanti */
    private List<Team> teams;

    /** Lista delle partite del turno corrente */
    private List<Match> currentRound;

    /** Numero del turno corrente */
    private int roundNumber;

    /**
     * Costruttore che inizializza il torneo con un nome.
     */
    public Tournament(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.currentRound = new ArrayList<>();
        this.roundNumber = 0;
    }

    /**
     * Aggiunge una squadra al torneo.
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * Genera le partite del prossimo turno.
     * Le squadre vengono abbinate in ordine di lista.
     */
    public void generateRound(List<Team> teamsForRound) {
        currentRound.clear();
        roundNumber++;
        for (int i = 0; i < teamsForRound.size() - 1; i += 2) {
            currentRound.add(new Match(teamsForRound.get(i), teamsForRound.get(i + 1)));
        }
    }

    /**
     * Restituisce i vincitori del turno corrente.
     */
    public List<Team> getWinners() {
        List<Team> winners = new ArrayList<>();
        for (Match match : currentRound) {
            if (match.isPlayed() && match.getResult().getWinner() != null) {
                winners.add(match.getResult().getWinner());
            }
        }
        return winners;
    }

    /**
     * Indica se tutte le partite del turno corrente sono state giocate.
     */
    public boolean isCurrentRoundFinished() {
        return currentRound.stream().allMatch(Match::isPlayed);
    }

    /**
     * Indica se il torneo è terminato (rimane una sola squadra vincitrice).
     */
    public boolean isFinished() {
        return isCurrentRoundFinished() && getWinners().size() == 1;
    }

    public String getName() { return name; }
    public List<Team> getTeams() { return teams; }
    public List<Match> getCurrentRound() { return currentRound; }
    public int getRoundNumber() { return roundNumber; }

    /**
     * Restituisce una rappresentazione testuale del torneo.
     */
    @Override
    public String toString() {
        return name + " - Turno " + roundNumber + " (" + teams.size() + " squadre)";
    }
}