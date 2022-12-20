package unipa.prog3.model.entity;

public class Collo {
    private final String codice;
    private final Cliente mittente, destinatario;
    private final float peso;

    public Collo(String codice, Cliente mittente, Cliente destinatario, float peso) {
        this.codice = codice;
        this.mittente = mittente;
        this.destinatario = destinatario;
        this.peso = peso;
    }

    public String getCodice() {
        return codice;
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
