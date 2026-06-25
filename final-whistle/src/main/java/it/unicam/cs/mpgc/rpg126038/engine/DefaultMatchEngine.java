package it.unicam.cs.mpgc.rpg126038.engine;

import it.unicam.cs.mpgc.rpg126038.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Implementazione predefinita del motore di simulazione delle partite.
 * Genera eventi minuto per minuto rendendo la partita narrativa e aggiorna le statistiche dei giocatori coinvolti.
 */
public class DefaultMatchEngine implements MatchEngine {

    /** Generatore di numeri casuali */
    private final Random random = new Random();

    /**
     * Simula una partita generando eventi casuali minuto per minuto.
     */
    @Override
    public MatchResult simulate(Match match) {
        double homeStrength = match.getHomeTeam().calculateStrength() + random.nextDouble() * 50;
        double awayStrength = match.getAwayTeam().calculateStrength() + random.nextDouble() * 50;

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

        // Genera gli eventi della partita
        generateEvents(result, match.getHomeTeam(), match.getAwayTeam(), homeGoals, awayGoals);

        // Aggiorna le statistiche dei giocatori
        updatePlayerStats(match.getHomeTeam(), homeGoals);
        updatePlayerStats(match.getAwayTeam(), awayGoals);

        match.setResult(result);
        return result;
    }

    /**
     * Calcola il numero di gol segnati da una squadra.
     */
    private int calculateGoals(double attackStrength, double defenseStrength) {
        double ratio = attackStrength / (attackStrength + defenseStrength);
        return (int) (ratio * 5) + random.nextInt(2);
    }

    /**
     * Genera gli eventi della partita in ordine cronologico.
     */
    private void generateEvents(MatchResult result, Team home, Team away,
                                int homeGoals, int awayGoals) {
        List<MatchEvent> events = new ArrayList<>();
        List<Integer> minutes = new ArrayList<>();

        int totalEvents = homeGoals + awayGoals + random.nextInt(4) + 2;
        for (int i = 0; i < totalEvents; i++) {
            minutes.add(random.nextInt(90) + 1);
        }
        minutes.sort(Integer::compareTo);

        int homeGoalsLeft = homeGoals;
        int awayGoalsLeft = awayGoals;

        for (int minute : minutes) {
            Team team = random.nextBoolean() ? home : away;
            List<Player> players = team.getPlayers();
            if (players.isEmpty()) continue;

            Player player = players.get(random.nextInt(players.size()));

            // Genera gol o altri eventi
            if (homeGoalsLeft > 0 && team == home) {
                List<Player> attackers = getAttackers(home);
                Player scorer = attackers.isEmpty() ? player : attackers.get(random.nextInt(attackers.size()));
                events.add(new MatchEvent(minute, MatchEvent.EventType.GOL, scorer.getName(), home.getName()));
                homeGoalsLeft--;
            } else if (awayGoalsLeft > 0 && team == away) {
                List<Player> attackers = getAttackers(away);
                Player scorer = attackers.isEmpty() ? player : attackers.get(random.nextInt(attackers.size()));
                events.add(new MatchEvent(minute, MatchEvent.EventType.GOL, scorer.getName(), away.getName()));
                awayGoalsLeft--;
            } else {
                // Evento casuale
                MatchEvent.EventType type = switch (random.nextInt(3)) {
                    case 0 -> MatchEvent.EventType.AMMONIZIONE;
                    case 1 -> MatchEvent.EventType.OCCASIONE_MANCATA;
                    default -> MatchEvent.EventType.PARATA;
                };
                events.add(new MatchEvent(minute, type, player.getName(), team.getName()));
            }
        }

        // Aggiunge eventuali gol rimasti
        while (homeGoalsLeft > 0) {
            List<Player> attackers = getAttackers(home);
            Player scorer = attackers.isEmpty() ?
                    home.getPlayers().get(0) : attackers.get(random.nextInt(attackers.size()));
            events.add(new MatchEvent(random.nextInt(90) + 1,
                    MatchEvent.EventType.GOL, scorer.getName(), home.getName()));
            homeGoalsLeft--;
        }
        while (awayGoalsLeft > 0) {
            List<Player> attackers = getAttackers(away);
            Player scorer = attackers.isEmpty() ?
                    away.getPlayers().get(0) : attackers.get(random.nextInt(attackers.size()));
            events.add(new MatchEvent(random.nextInt(90) + 1,
                    MatchEvent.EventType.GOL, scorer.getName(), away.getName()));
            awayGoalsLeft--;
        }

        events.sort((e1, e2) -> Integer.compare(e1.getMinute(), e2.getMinute()));
        events.forEach(result::addEvent);
    }

    /**
     * Restituisce la lista degli attaccanti e centrocampisti della squadra.
     */
    private List<Player> getAttackers(Team team) {
        return team.getPlayers().stream()
                .filter(p -> p.getRole().equals("Attaccante") ||
                        p.getRole().equals("Centrocampista"))
                .collect(Collectors.toList());
    }

    /**
     * Aggiorna le statistiche dei giocatori della squadra.
     */
    private void updatePlayerStats(Team team, int goals) {
        List<Player> players = team.getPlayers();
        if (players.isEmpty()) return;

        for (Player player : players) {
            player.addPresence();
        }

        List<Player> scorers = getAttackers(team);
        for (int i = 0; i < goals; i++) {
            if (!scorers.isEmpty()) {
                Player scorer = scorers.get(random.nextInt(scorers.size()));
                scorer.addGoal();
                List<Player> assisters = players.stream()
                        .filter(p -> !p.equals(scorer))
                        .collect(Collectors.toList());
                if (!assisters.isEmpty()) {
                    assisters.get(random.nextInt(assisters.size())).addAssist();
                }
            }
        }

        players.stream()
                .filter(p -> p.getRole().equals("Portiere"))
                .findFirst()
                .ifPresent(p -> p.addSaves(random.nextInt(5) + 1));
    }
}