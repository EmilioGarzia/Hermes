package unipa.prog3.controller.genetica;

import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;

import java.util.List;
import java.util.Vector;

public class Cromosoma extends Vector<Collo> {
    private static final double mutationRate = 0.2;

    private final Veicolo vehicle;
    private double totalWeight, fitness;

    public Cromosoma(Veicolo vehicle) {
        super();
        this.vehicle = vehicle;
        fitness = -1;
    }

    public void randomizza(List<Collo> allPackages) {
        while(size() < allPackages.size() && totalWeight < vehicle.getCapienza())
            add(selezionaCasuale(allPackages));

        if (totalWeight > vehicle.getCapienza())
            remove(size()-1);
    }

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

    public void muta(List<Collo> allPackages) {
        if (size() == allPackages.size())
            return;

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

    public double fitness() {
        if (fitness == -1)
            fitness = size();
        return fitness;
    }

    public Veicolo getVehicle() {
        return vehicle;
    }

    @Override
    public Object clone() {
        Cromosoma clone = new Cromosoma(vehicle);
        clone.totalWeight = totalWeight;
        clone.addAll(this);
        return clone;
    }
}
