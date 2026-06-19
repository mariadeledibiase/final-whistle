package it.unicam.cs.mpgc.rpg126038.engine;

import it.unicam.cs.mpgc.rpg126038.model.Match;
import it.unicam.cs.mpgc.rpg126038.model.MatchResult;

/**
 * Interfaccia che definisce il contratto per il motore di simulazione
 * di una partita. Permette di avere diverse implementazioni del calcolo del risultato, rendendo il sistema facilmente estendibile.
 */
public interface MatchEngine {

    /**
     * Simula una partita e restituisce il risultato.
     * Il calcolo tiene conto delle statistiche delle squadre e di un fattore casuale.
     */
    MatchResult simulate(Match match);
}