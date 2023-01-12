package unipa.prog3.model.entity;

public class Cliente extends Entity {
    private String nome, cognome;
    private String indirizzo, città, stato;
    private int cap;
    private String email, telefono;
    private Centro centro;

    public Cliente(String id, String nome, String cognome) {
        super(id);
        this.nome = nome;
        this.cognome = cognome;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cliente cliente))
            return false;
        return cliente.nome.equals(nome) && cliente.cognome.equals(cognome) && cliente.indirizzo.equals(indirizzo)
                && cliente.città.equals(città) && cliente.stato.equals(stato) && cliente.cap == cap;
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
}
