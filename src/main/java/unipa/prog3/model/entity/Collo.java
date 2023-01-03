package unipa.prog3.model.entity;

public class Collo extends Entity {
    private final Cliente mittente, destinatario;
    private final double peso;
    private Veicolo veicolo;

    public Collo(String codice, Cliente mittente, Cliente destinatario, double peso) {
        super(codice);
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.peso = peso;
    }

    public Cliente getMittente() {
        return mittente;
    }

    public Cliente getDestinatario() {
        return destinatario;
    }

    public double getPeso() {
        return peso;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }
}
