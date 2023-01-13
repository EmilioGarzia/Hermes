package unipa.prog3.model.relation;

public class Collo extends Relation {
    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso) {
        this(codice, mittente, destinatario, peso, false, null);
    }

    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso, boolean consegnato, Veicolo veicolo) {
        super(1, 5);
        addKey(codice);
        addData(mittente);
        addData(destinatario);
        addData(peso);
        addData(consegnato);
        addData(veicolo);
    }

    public String getCodice() {
        return (String) keys[0];
    }

    public Cliente getMittente() {
        return (Cliente) data[0];
    }

    public Cliente getDestinatario() {
        return (Cliente) data[1];
    }

    public float getPeso() {
        return (float) data[2];
    }

    public Centro getPartenza() {
        return getMittente().getCentro();
    }

    public Centro getDestinazione() {
        return getDestinatario().getCentro();
    }

    public boolean isConsegnato() {
        return (boolean) data[3];
    }

    public void setConsegnato(boolean consegnato) {
        data[3] = consegnato;
    }

    public Veicolo getVeicolo() {
        return (Veicolo) data[4];
    }

    public void setVeicolo(Veicolo veicolo) {
        data[4] = veicolo;
    }
}
