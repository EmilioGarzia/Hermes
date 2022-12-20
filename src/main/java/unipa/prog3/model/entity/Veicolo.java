package unipa.prog3.model.entity;

public class Veicolo {
    private final String codice, tipo;
    private final int capienza;
    private final Collo[] colli;

    public Veicolo(String codice, String tipo, int capienza) {
        this.codice = codice;
        this.tipo = tipo;
        this.capienza = capienza;
        colli = new Collo[capienza];
    }

    public String getCodice() {
        return codice;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCapienza() {
        return capienza;
    }
}
