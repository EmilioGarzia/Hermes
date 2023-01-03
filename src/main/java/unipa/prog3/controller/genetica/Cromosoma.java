package unipa.prog3.controller.genetica;

import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Veicolo;

import java.util.List;
import java.util.Vector;

public class Cromosoma extends Vector<Collo> {
    private static final double frequenzaMutazioni = 0.2;

    private final Veicolo veicolo;
    private float pesoTotale;

    public Cromosoma(Veicolo veicolo) {
        super();
        this.veicolo = veicolo;
    }

    public void randomizza(List<Collo> allPackages) {
        while(pesoTotale < veicolo.getCapienza()) {
            Collo selezionato = selezionaCasuale(allPackages);
            add(selezionato);
            pesoTotale += selezionato.getPeso();
        }

        if (pesoTotale > veicolo.getCapienza())
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
        boolean result = super.add(collo);
        pesoTotale += collo.getPeso();
        return result;
    }

    @Override
    public Collo set(int index, Collo collo) {
        Collo old = super.set(index, collo);
        pesoTotale -= old.getPeso();
        pesoTotale += collo.getPeso();
        return old;
    }

    public void muta(List<Collo> allPackages) {
        for (int i = 0; i < size(); i++)
            if (Math.random() < frequenzaMutazioni)
                do
                    set(i, selezionaCasuale(allPackages));
                while (pesoTotale > veicolo.getCapienza());
    }

    @Override
    public Collo remove(int index) {
        Collo collo = super.remove(index);
        pesoTotale -= collo.getPeso();
        return collo;
    }

    public double fitness() {
        return pesoTotale/veicolo.getCapienza();
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public float getPesoTotale() {
        return pesoTotale;
    }
}
