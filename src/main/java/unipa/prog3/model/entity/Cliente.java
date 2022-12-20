package unipa.prog3.model.entity;

public class Cliente {
    private final String nome, cognome;
    private String indirizzo, cap, città;

    public Cliente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public String getCittà() {
        return città;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public void setCittà(String città) {
        this.città = città;
    }
}
