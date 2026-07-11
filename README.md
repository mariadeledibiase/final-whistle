# Final Whistle

Final Whistle è un gioco di ruolo a tema calcistico sviluppato in Java con interfaccia grafica JavaFX. Il giocatore crea la propria squadra, sceglie la tattica e affronta squadre avversarie generate automaticamente in un torneo a eliminazione diretta.

## Funzionalità principali

- Creazione della propria squadra con nome, tattica e creazione giocatori per la tua squadra (nome e ruolo)
- Scelta della tattica di gioco: Offensiva, Difensiva, Bilanciata
- Simulazione delle partite con cronaca degli eventi minuto per minuto
- Scelta dello schema di gioco (4-3-3, 4-4-2, 3-5-2, 5-3-2)
- Possibilità di effettuare una sostituzione prima della partita
- Rimozione di giocatori dalla rosa prima di avviare il torneo
- Statistiche dei giocatori aggiornate dopo ogni partita (gol, assist, presenze, parate)
- Torneo a eliminazione diretta con 4 squadre (semifinali e finale)
- Salvataggio automatico del torneo in formato JSON

## Come eseguire il progetto

### Prerequisiti

- Java 21 o superiore 
- Gradle

### Istruzioni

    git clone https://github.com/mariadeledibiase/final-whistle.git
    cd final-whistle/final-whistle

### Build del progetto

    ./gradlew build

### Esecuzione

    ./gradlew run

---

## Uso di strumenti di AI

Utilizzato Claude (Anthropic) come supporto durante lo sviluppo per:

- Chiarire errori di compilazione e problemi di configurazione Gradle
- Suggerimenti sulla struttura dei package e delle classi
- Generazione di una prima versione di alcune classi, poi analizzata, compresa e adattata manualmente
- Supporto nella scrittura dei test JUnit

Per una descrizione più dettagliata del progetto consultare la **Wiki del repository**.
