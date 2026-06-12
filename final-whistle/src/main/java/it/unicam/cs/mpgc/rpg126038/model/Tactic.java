package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta le tattiche di gioco disponibili per una squadra.
 * Ogni tattica influenza il calcolo del risultato della partita.
 */
public enum Tactic {

    /**
     * Tattica offensiva: aumenta il peso dell'attacco nel calcolo del punteggio.
     */
    OFFENSIVE,

    /**
     * Tattica difensiva: aumenta il peso della difesa nel calcolo del punteggio.
     */
    DEFENSIVE,

    /**
     * Tattica bilanciata: considera equamente attacco e difesa.
     */
    BALANCED
}
