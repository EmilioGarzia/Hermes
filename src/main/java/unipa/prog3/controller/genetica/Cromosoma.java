package unipa.prog3.controller.genetica;

import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;

import java.util.List;
import java.util.Vector;

/**
 * La classe rappresenta una soluzione nell'algoritmo di genetica utilizzato
 * per la selezione dei colli da inserire nel container di un determinato veicolo
 * avente una sua capienza massima.
 */
public class Cromosoma extends Vector<Collo> {
    private static final double mutationRate = 0.2;

    private final Veicolo vehicle;
    private double totalWeight, fitness;

    public Cromosoma(Veicolo vehicle) {
        super();
        this.vehicle = vehicle;
        fitness = -1;
    }

    /**
     * Genera una prima soluzione costituita da colli scelti casualmente
     * e che possano essere tutti contenuti nel container del veicolo.
     * @param allPackages Lista di colli da utilizzare per costruire la soluzione
     */
    public void generaCasuale(List<Collo> allPackages) {
        while(size() < allPackages.size() && totalWeight < vehicle.getCapienza())
            add(selezionaCasuale(allPackages));

        if (totalWeight > vehicle.getCapienza())
            remove(size()-1);
    }

    /**
     * Seleziona un collo, che non sia già presente nella soluzione, casualmente
     * @param allPackages Lista di colli da utilizzare per effettuare la selezione
     * @return
     */
    private Collo selezionaCasuale(List<Collo> allPackages) {
        Collo scelto;
        do {
            int r = (int) (Math.random() * allPackages.size());
            scelto = allPackages.get(r);
        } while(contains(scelto));
        return scelto;
    }

    @Override
    public boolean add(Collo collo) {
        if (super.add(collo)) {
            totalWeight += collo.getPeso();
            return true;
        }

        return false;
    }

    @Override
    public Collo set(int index, Collo collo) {
        Collo old = super.set(index, collo);
        totalWeight -= old.getPeso();
        totalWeight += collo.getPeso();
        return old;
    }

    /**
     * Effetta una mutazione della soluzione, sostituendo, con una determinata
     * frequenza, i colli in essa contenuti
     * @param allPackages Lista di colli da utilizzare per effettuare le sostituzioni
     */
    public void muta(List<Collo> allPackages) {
        if (size() == allPackages.size())
            return;

        // Scorre tutti i colli presenti nella soluzione e
        // li sostituisce in base alla frequenza specificata da mutationRate
        // Inoltre, la sostituizione viene effettuata in modo che il nuovo peso totale
        // sia sempre minore della capienza massima del container
        for (int i = 0; i < size(); i++)
            if (Math.random() < mutationRate)
                do
                    set(i, selezionaCasuale(allPackages));
                while (totalWeight > vehicle.getCapienza());
    }

    @Override
    public Collo remove(int index) {
        Collo collo = super.remove(index);
        totalWeight -= collo.getPeso();
        return collo;
    }

    /**
     * Implementazione della funzione di fitness che
     * permette di classificare l'efficacia della soluzione
     * @return Valore del fitness calcolato
     */
    public double fitness() {
        if (fitness == -1)
            fitness = size(); // L'efficacia è data dal numero di colli contenuti nella soluzione
        return fitness;
    }

    /**
     * Calcola il rapporto tra il peso totale dei colli contenuti nella soluzione e la capienza massima del veicolo
     * @return Il rapporto calcolato
     */
    public double weightRatio() {
        return totalWeight/vehicle.getCapienza();
    }

    public Veicolo getVehicle() {
        return vehicle;
    }

    /**
     * Implementazione del design pattern Prototype,
     * per consentire la clonazione della soluzione
     * @return Istanza clone della soluzione corrente
     */
    @Override
    public Object clone() {
        Cromosoma clone = new Cromosoma(vehicle);
        clone.totalWeight = totalWeight;
        clone.addAll(this);
        return clone;
    }
}
