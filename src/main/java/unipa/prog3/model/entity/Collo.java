package unipa.prog3.model.entity;

public class Collo extends Entity {
    private final Cliente mittente, destinatario;
    private final float peso;

    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso) {
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

    public float getPeso() {
        return peso;
    }
}
