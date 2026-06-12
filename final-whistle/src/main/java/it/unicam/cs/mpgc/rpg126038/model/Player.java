package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta un giocatore all'interno di una squadra.
 * Ogni giocatore ha un nome, un ruolo (Portiere, Difensore, Centrocampista, Attaccante) e tre caratteristiche:
 * attacco, difesa e velocità.
 */
public class Player {

    /** Nome del giocatore */
    private String name;

    /** Ruolo del giocatore (Portiere, Difensore, Centrocampista, Attaccante) */
    private String role;

    /** Statistica di attacco del giocatore (0-100) */
    private int attack;

    /** Statistica di difesa del giocatore (0-100) */
    private int defense;

    /** Statistica di velocità del giocatore (0-100) */
    private int speed;

    /**
     * Costruttore che inizializza un giocatore con tutte le sue caratteristiche.
     *
     * @param name    il nome del giocatore
     * @param role    il ruolo del giocatore
     * @param attack  il valore di attacco
     * @param defense il valore di difesa
     * @param speed   il valore di velocità
     */
    public Player(String name, String role, int attack, int defense, int speed) {
        this.name = name;
        this.role = role;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    /**
     * Restituisce il nome del giocatore.
     */
    public String getName() { return name; }

    /**
     * Restituisce il ruolo del giocatore.
     */
    public String getRole() { return role; }

    /**
     * Restituisce il valore di attacco del giocatore.
     */
    public int getAttack() { return attack; }

    /**
     * Restituisce il valore di difesa del giocatore.
     */
    public int getDefense() { return defense; }

    /**
     * Restituisce il valore di velocità del giocatore.
     */
    public int getSpeed() { return speed; }

    /**
     * Restituisce una rappresentazione del giocatore
     * con nome, ruolo e statistiche.
     */
    @Override
    public String toString() {
        return name + " [" + role + "] ATT:" + attack + " DEF:" + defense + " SPD:" + speed;
    }
}