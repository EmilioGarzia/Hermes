package unipa.prog3.model.entity;

public class Veicolo extends Entity {
    private final String tipo;
    private final double capienza;

    public Veicolo(String codice, String tipo, double capienza) {
        super(codice);
        this.tipo = tipo;
        this.capienza = capienza;
    }

    public String getTipo() {
        return tipo;
    }

    public double getCapienza() {
        return capienza;
    }
}
