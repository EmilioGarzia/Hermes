package unipa.prog3.model.entity;

public class Collo extends Entity {
    private final Cliente mittente, destinatario;
    private final float peso;
    private Veicolo veicolo;

    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso) {
        this(codice, mittente, destinatario, peso, null);
    }

    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso, Veicolo veicolo) {
        super(codice);
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.peso = peso;
        this.veicolo = veicolo;
    }

    public Cliente getMittente() {
        return mittente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public float getPeso() {
        return peso;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }
}
