package unipa.prog3.model.entity;

public class Veicolo {
    private final String codice, tipo;
    private final int capienza;
    private final Collo[] colli;
    private int quantità;

    public Veicolo(String codice, String tipo, int capienza) {
        this.codice = codice;
        this.tipo = tipo;
        this.capienza = capienza;
        colli = new Collo[capienza];
    }

    public boolean inserisciCollo(Collo collo) {
        if (quantità < capienza) {
            colli[quantità++] = collo;
            return true;
        }

        return false;
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

    public int getQuantità() {
        return quantità;
    }
}
