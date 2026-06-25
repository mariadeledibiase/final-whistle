package it.unicam.cs.mpgc.rpg126038.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.mpgc.rpg126038.model.Tournament;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione del repository che salva e carica i tornei in formato JSON su file system.
 * Utilizza la libreria Gson per la serializzazione e deserializzazione.
 */
public class JsonTournamentRepository implements Repository<Tournament> {

    /** Istanza di Gson per la serializzazione/deserializzazione */
    private final Gson gson;

    /** Cartella in cui vengono salvati i file JSON dei tornei */
    private final Path saveDirectory;

    /**
     * Costruttore che inizializza il repository con la cartella di salvataggio.
     */
    public JsonTournamentRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.saveDirectory = Paths.get("saves");
    }

    /**
     * Salva un torneo su file JSON.
     * Il file viene nominato con il nome del torneo.
     */
    @Override
    public void save(Tournament tournament) {
        try {
            Files.createDirectories(saveDirectory);
            Path filePath = saveDirectory.resolve(tournament.getName() + ".json");
            Files.writeString(filePath, gson.toJson(tournament));
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del torneo: " + e.getMessage());
        }
    }

    /**
     * Cerca e carica un torneo dal file JSON corrispondente al nome.
     */
    @Override
    public Optional<Tournament> findByName(String name) {
        Path filePath = saveDirectory.resolve(name + ".json");
        if (!Files.exists(filePath)) return Optional.empty();
        try {
            String json = Files.readString(filePath);
            return Optional.of(gson.fromJson(json, Tournament.class));
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento del torneo: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Restituisce tutti i tornei salvati nella cartella di salvataggio.
     */
    @Override
    public List<Tournament> findAll() {
        List<Tournament> tournaments = new ArrayList<>();
        if (!Files.exists(saveDirectory)) return tournaments;
        try {
            Files.list(saveDirectory)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(p -> {
                        try {
                            String json = Files.readString(p);
                            tournaments.add(gson.fromJson(json, Tournament.class));
                        } catch (IOException e) {
                            System.err.println("Errore nella lettura del file: " + p);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dei tornei: " + e.getMessage());
        }
        return tournaments;
    }

    /**
     * Elimina il file JSON corrispondente al torneo con il nome indicato.
     */
    @Override
    public void delete(String name) {
        try {
            Files.deleteIfExists(saveDirectory.resolve(name + ".json"));
        } catch (IOException e) {
            System.err.println("Errore durante l'eliminazione del torneo: " + e.getMessage());
        }
    }
}