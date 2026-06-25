package it.unicam.cs.mpgc.rpg126038.model;

/**
 * Rappresenta un evento che accade durante una partita.
 * Gli eventi rendono la simulazione più narrativa e coinvolgente, mostrando al giocatore cosa succede minuto per minuto.
 */
public class MatchEvent {

    /**
     * Tipo di evento che può accadere durante una partita.
     */
    public enum EventType {
        GOL,
        AMMONIZIONE,
        INFORTUNIO,
        PARATA,
        OCCASIONE_MANCATA
    }

    /** Il minuto in cui è avvenuto l'evento */
    private int minute;

    /** Il tipo di evento */
    private EventType type;

    /** Il nome del giocatore coinvolto */
    private String playerName;

    /** Il nome della squadra coinvolta */
    private String teamName;

    /**
     * Costruttore che inizializza un evento di partita.
     */
    public MatchEvent(int minute, EventType type, String playerName, String teamName) {
        this.minute = minute;
        this.type = type;
        this.playerName = playerName;
        this.teamName = teamName;
    }

    /**
     * Restituisce una descrizione testuale dell'evento.
     */
    public String getDescription() {
        return switch (type) {
            case GOL -> minute + "' - GOOOL! " + playerName + " segna per " + teamName + "!";
            case AMMONIZIONE -> minute + "' - Ammonizione per " + playerName + " (" + teamName + ")";
            case INFORTUNIO -> minute + "' - Infortunio per " + playerName + " (" + teamName + ")";
            case PARATA -> minute + "' - Che parata di " + playerName + " (" + teamName + ")!";
            case OCCASIONE_MANCATA -> minute + "' - Occasione mancata da " + playerName + " (" + teamName + ")";
        };
    }

    public int getMinute() { return minute; }
    public EventType getType() { return type; }
    public String getPlayerName() { return playerName; }
    public String getTeamName() { return teamName; }
}