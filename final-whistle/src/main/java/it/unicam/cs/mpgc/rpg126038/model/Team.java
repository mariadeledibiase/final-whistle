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

    /** Schema di gioco della squadra */
    private Formation formation;

    /**
     * Costruttore che inizializza una squadra con nome e tattica.
     */
    public Team(String name, Tactic tactic, Formation formation) {
        this.name = name;
        this.tactic = tactic;
        this.formation = formation;
        this.players = new ArrayList<>();
    }

    /**
     * Costruttore che inizializza una squadra con nome e tattica.
     * Lo schema predefinito è 4-4-2.
     */
    public Team(String name, Tactic tactic) {
        this(name, tactic, Formation.F_4_4_2);
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
     * Calcola la forza complessiva della squadra
     * in base alla tattica e allo schema di gioco adottati.
     */
    public double calculateStrength() {
        double base = switch (tactic) {
            case OFFENSIVE -> 60.0;
            case DEFENSIVE -> 40.0;
            case BALANCED  -> 50.0;
        };

        double formationBonus = switch (formation) {
            case F_4_3_3 -> 5.0;
            case F_4_4_2 -> 0.0;
            case F_3_5_2 -> 2.0;
            case F_5_3_2 -> -3.0;
        };

        return base + formationBonus;
    }

    public String getName() { return name; }
    public List<Player> getPlayers() { return players; }
    public Tactic getTactic() { return tactic; }
    public Formation getFormation() { return formation; }
    /**
     * Cambia la tattica della squadra.
     */
    public void setTactic(Tactic tactic) { this.tactic = tactic; }

    /**
     * Cambia lo schema di gioco della squadra.
     */
    public void setFormation(Formation formation) { this.formation = formation; }

    /**
     * Restituisce una rappresentazione testuale della squadra.
     */
    @Override
    public String toString() {
        return name + " [" + tactic + " - " + formation + "] - " + players.size() + " giocatori";
    }
}