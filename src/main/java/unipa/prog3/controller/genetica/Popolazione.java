package unipa.prog3.controller.genetica;

import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;

import java.util.Vector;

public class Popolazione extends Vector<Cromosoma> {
    public Popolazione(int dimensione) {
        super(dimensione);
    }

    public Cromosoma findBestSolutionForSingleVehicle(Veicolo veicolo, Vector<Collo> colli, int generazioni) {
        clear();
        for (int i = 0; i < capacity(); i++) {
            Cromosoma soluzione = new Cromosoma(veicolo);
            soluzione.randomizza(colli);
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

    private Cromosoma pickOne(double fitnessSum) {
        double rand = Math.random()*fitnessSum;
        if (rand == 0)
            return get(0);

        int index = 0;
        while(rand > 0)
            rand -= get(index++).fitness();
        return get(index-1);
    }

    private double sumFitness() {
        double fitnessSum = 0;
        for (Cromosoma c : this)
            fitnessSum += c.fitness();
        return fitnessSum;
    }

    private Cromosoma findBest() {
        Cromosoma best = get(0);
        for (Cromosoma c : this)
            if (c.fitness() > best.fitness())
                best = c;

        return best;
    }
}
