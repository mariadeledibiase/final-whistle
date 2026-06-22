package it.unicam.cs.mpgc.rpg126038.persistence;

import java.util.List;
import java.util.Optional;

/**
 * Interfaccia generica per la gestione della persistenza dei dati.
 * Definisce le operazioni base di salvataggio e recupero applicabili a qualsiasi tipo di entità.
 * Segue il principio di inversione delle dipendenze (SOLID), permette di cambiare il meccanismo di persistenza senza
 * modificare il resto dell'applicazione.
 */
public interface Repository<T> {

    /** Salva un'entità nel repository.
     */

    void save(T entity);

    /**
     * Recupera un'entità dal repository tramite il suo nome.
     */
    Optional<T> findByName(String name);

    /**
     * Recupera tutte le entità presenti nel repository.
     */
    List<T> findAll();

    /**
     * Elimina un'entità dal repository tramite il suo nome.
     */
    void delete(String name);
}