package unipa.prog3.model.entity;

public class Cliente extends Entity {
    private final String nome, cognome;
    private String indirizzo, città, stato;
    private int cap;
    private String email, telefono;

    public Cliente(String id, String nome, String cognome) {
        super(id);
        this.nome = nome;
        this.cognome = cognome;
    }

    public Cliente(String nome, String cognome) {
        this(null, nome, cognome);
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

    public int getCap() {
        return cap;
    }

    public String getCittà() {
        return città;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getStato() {
        return stato;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
