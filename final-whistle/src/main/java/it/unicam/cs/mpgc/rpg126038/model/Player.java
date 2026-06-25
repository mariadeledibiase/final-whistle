package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta un giocatore all'interno di una squadra.
 * Le statistiche del giocatore sono ispirate al fantacalcio: gol, assist, presenze e parate (per i portieri).
 */
public class Player {

    /** Nome del giocatore */
    private String name;

    /** Ruolo del giocatore */
    private String role;

    /** Numero di gol segnati */
    private int goals;

    /** Numero di assist effettuati */
    private int assists;

    /** Numero di presenze in campo */
    private int presences;

    /** Numero di parate (rilevante solo per i portieri) */
    private int saves;

    /**
     * Costruttore che inizializza un giocatore con tutte le statistiche.
     */
    public Player(String name, String role, int goals, int assists, int presences, int saves) {
        this.name = name;
        this.role = role;
        this.goals = goals;
        this.assists = assists;
        this.presences = presences;
        this.saves = saves;
    }

    /**
     * Calcola il punteggio del giocatore in base al ruolo, come nel fantacalcio ogni ruolo ha pesi diversi.
     */
    public double calculateScore() {
        return switch (role) {
            case "Portiere"       -> saves * 0.5 + presences * 0.3 + goals * 0.2;
            case "Difensore"      -> presences * 0.4 + assists * 0.3 + goals * 0.3;
            case "Centrocampista" -> assists * 0.4 + presences * 0.3 + goals * 0.3;
            case "Attaccante"     -> goals * 0.5 + assists * 0.3 + presences * 0.2;
            default               -> (goals + assists + presences + saves) / 4.0;
        };
    }

    /**
     * Aggiunge un gol al contatore del giocatore.
     */
    public void addGoal() { this.goals++; }

    /**
     * Aggiunge un assist al contatore del giocatore.
     */
    public void addAssist() { this.assists++; }

    /**
     * Aggiunge una presenza al contatore del giocatore.
     */
    public void addPresence() { this.presences++; }

    /**
     * Aggiunge parate al contatore del portiere.
     */
    public void addSaves(int n) { this.saves += n; }

    public String getName() { return name; }
    public String getRole() { return role; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }
    public int getPresences() { return presences; }
    public int getSaves() { return saves; }

    /**
     * Restituisce una rappresentazione testuale del giocatore.
     */
    @Override
    public String toString() {
        return name + " [" + role + "] Gol:" + goals + " Assist:" + assists +
                " Presenze:" + presences + " Parate:" + saves;
    }
}