package it.unicam.cs.mpgc.rpg126038.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una squadra del torneo.
 * Una squadra è composta da un insieme di giocatori e adotta una tattica di gioco.
 */
public class Team {

    /** Nome della squadra */
    private String name;

    /** Lista dei giocatori della squadra */
    private List<Player> players;

    /** Tattica attualmente adottata dalla squadra */
    private Tactic tactic;

    /**
     * Costruttore che inizializza una squadra con nome e tattica.
     */
    public Team(String name, Tactic tactic) {
        this.name = name;
        this.tactic = tactic;
        this.players = new ArrayList<>();
    }

    /**
     * Aggiunge un giocatore alla squadra.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Rimuove un giocatore dalla squadra (sostituzione).
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Calcola la forza complessiva della squadra in base al punteggio fantacalcio dei giocatori e alla tattica adottata.
     */

    public double calculateStrength() {
        if (players.isEmpty()) return 0;
        double base = players.stream()
                .mapToDouble(Player::calculateScore)
                .average()
                .orElse(0);
        // Se tutti i giocatori hanno statistiche a 0, usa un valore base casuale
        if (base == 0) base = 50;
        return switch (tactic) {
            case OFFENSIVE -> base * 1.2;
            case DEFENSIVE -> base * 0.9;
            case BALANCED  -> base;
        };
    }

    public String getName() { return name; }
    public List<Player> getPlayers() { return players; }
    public Tactic getTactic() { return tactic; }

    /**
     * Cambia la tattica della squadra.
     */
    public void setTactic(Tactic tactic) { this.tactic = tactic; }

    /**
     * Restituisce una rappresentazione testuale della squadra.
     */
    @Override
    public String toString() {
        return name + " [" + tactic + "] - " + players.size() + " giocatori";
    }
}