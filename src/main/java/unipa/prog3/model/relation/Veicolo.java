package unipa.prog3.model.relation;

public class Veicolo extends Relation {
    public Veicolo(String codice, String tipo, double capienza) {
        super(1, 2);
        addKey(codice);
        addData(tipo);
        addData(capienza);
    }

    public String getCodice() {
        return (String) keys[0];
    }

    public String getTipo() {
        return (String) data[0];
    }

    public double getCapienza() {
        return (double) data[1];
    }
}
