package unipa.prog3.model.relation;

public class Cliente extends Relation {
    public Cliente(String id, String nome, String cognome) {
        super(1, 8);
        addKey(id);
        addData(nome);
        addData(cognome);
    }

    public Cliente() {
        this(null, null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cliente cliente))
            return false;
        return cliente.getNome().equals(getNome()) && cliente.getCognome().equals(getCognome())
                && cliente.getIndirizzo().equals(getIndirizzo()) && cliente.getCivico() == getCivico()
                && cliente.getCap() == getCap() && cliente.getCentro().equals(getCentro());
    }

    public String getNome() {
        return (String) data[0];
    }

    public void setNome(String nome) {
        data[0] = nome;
    }

    public String getCognome() {
        return (String) data[1];
    }

    public void setCognome(String cognome) {
        data[1] = cognome;
    }

    public Centro getCentro() {
        return (Centro) data[2];
    }

    public void setCentro(Centro centro) {
        data[2] = centro;
    }

    public int getCap() {
        return (int) data[3];
    }

    public void setCap(int cap) {
        data[3] = cap;
    }

    public String getIndirizzo() {
        return (String) data[4];
    }

    public void setIndirizzo(String indirizzo) {
        data[4] = indirizzo;
    }

    public int getCivico() {
        return (int) data[5];
    }

    public void setCivico(int civico) {
        data[5] = civico;
    }

    public String getEmail() {
        return (String) data[6];
    }

    public void setEmail(String email) {
        data[6] = email;
    }

    public String getTelefono() {
        return (String) data[7];
    }

    public void setTelefono(String telefono) {
        data[7] = telefono;
    }
}
