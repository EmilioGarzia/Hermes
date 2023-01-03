package unipa.prog3.controller.genetica;

import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Veicolo;

import java.util.Vector;

public class Popolazione extends Vector<Cromosoma> {
    public Popolazione(int dimensione) {
        super(dimensione);
    }

    public Cromosoma findBestSolutionForSingleVehicle(Veicolo veicolo, Vector<Collo> colli, int generazioni) {
        for (int i = 0; i < size(); i++) {
            Cromosoma soluzione = new Cromosoma(veicolo);
            soluzione.randomizza(colli);
            add(soluzione);
        }

        for (int i = 0; i < generazioni; i++) {
            // 1. Calcola fitness
            // 2. Crea nuova generazione
        }

        return findBest();
    }

    private Cromosoma findBest() {
        Cromosoma best = get(0);
        for (Cromosoma c : this)
            if (c.fitness() > best.fitness())
                best = c;

        return best;
    }
}
