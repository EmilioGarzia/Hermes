package unipa.prog3.controller.genetica;

import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;

import java.util.Vector;

/**
 * Rappresentazione della collezione di soluzioni totali. La classe implementa
 * tutte le operazioni necessarie al funzionamento dell'algoritmo di genetica.
 */
public class Popolazione extends Vector<Cromosoma> {

    public Popolazione(int dimensione) {
        super(dimensione);
    }

    /**
     * Permette di cercare la soluzione migliore per il container di un determinato veicolo.
     * @param veicolo Veicolo interessato
     * @param colli Vector dei colli da utilizzare per la creazione delle soluzioni
     * @param generazioni Numero di generazioni da creare
     * @return La soluzione migliore trovata nell'ultima generazione
     */
    public Cromosoma findBestSolutionForSingleVehicle(Veicolo veicolo, Vector<Collo> colli, int generazioni) {
        clear();
        for (int i = 0; i < capacity(); i++) {
            Cromosoma soluzione = new Cromosoma(veicolo);
            soluzione.generaCasuale(colli);
            add(soluzione);
        }

        for (int i = 0; i < generazioni-1; i++) {
            double fitnessSum = sumFitness();

            // Crea una nuova generazione basandosi sulle soluzioni migliori di quella precedente
            for (int j = 0; j < size(); j++) {
                Cromosoma parent = pickOne(fitnessSum);
                Cromosoma child = (Cromosoma) parent.clone();
                child.muta(colli);
                set(j, child);
            }
        }

        return findBest();
    }

    /**
     * Seleziona una soluzione dando priorità a quelle aventi un fitness migliore
     * @param fitnessSum Somma totale dei fitness delle soluzioni della generazione attuale
     * @return La soluzione scelta
     */
    private Cromosoma pickOne(double fitnessSum) {
        double rand = Math.random()*fitnessSum;
        if (rand == 0)
            return get(0);

        int index = 0;
        while(rand > 0)
            rand -= get(index++).fitness();
        return get(index-1);
    }

    /**
     * Calcola la somma dei fitness delle soluzioni della generazione attuale
     * @return La somma calcolata
     */
    private double sumFitness() {
        double fitnessSum = 0;
        for (Cromosoma c : this)
            fitnessSum += c.fitness();
        return fitnessSum;
    }

    /**
     * Cerca la soluzione migliore nella generazione attuale, sulla base dei fitness
     * @return La soluzione trovata
     */
    private Cromosoma findBest() {
        Cromosoma best = get(0);
        for (Cromosoma c : this)
            if (c.fitness() > best.fitness())
                best = c;

        return best;
    }
}
