package it.unicam.cs.mpgc.rpg126038.engine;

import it.unicam.cs.mpgc.rpg126038.model.Match;
import it.unicam.cs.mpgc.rpg126038.model.MatchResult;

import java.util.Random;

/**
 * Implementazione predefinita del motore di simulazione delle partite.
 * Il risultato viene calcolato in base alla forza delle squadre e a un fattore casuale che simula l'imprevedibilità del gioco.
 */
public class DefaultMatchEngine implements MatchEngine {

    /** Generatore di numeri casuali per simulare l'imprevedibilità */
    private final Random random = new Random();

    /**
     * Simula una partita calcolando i gol in base alla forza delle squadre e a un fattore casuale.
     */
    @Override
    public MatchResult simulate(Match match) {
        double homeStrength = match.getHomeTeam().calculateStrength() + random.nextDouble() * 20;
        double awayStrength = match.getAwayTeam().calculateStrength() + random.nextDouble() * 20;

        int homeGoals = calculateGoals(homeStrength, awayStrength);
        int awayGoals = calculateGoals(awayStrength, homeStrength);

        // Evita i pareggi nel torneo a eliminazione
        if (homeGoals == awayGoals) {
            if (random.nextBoolean()) homeGoals++;
            else awayGoals++;
        }

        MatchResult result = new MatchResult(
                match.getHomeTeam(),
                match.getAwayTeam(),
                homeGoals,
                awayGoals
        );

        match.setResult(result);
        return result;
    }

    /**
     * Calcola il numero di gol segnati da una squadra in base al rapporto di forza tra le due squadre.
     */
    private int calculateGoals(double attackStrength, double defenseStrength) {
        double ratio = attackStrength / (attackStrength + defenseStrength);
        return (int) (ratio * 5);
    }
}
