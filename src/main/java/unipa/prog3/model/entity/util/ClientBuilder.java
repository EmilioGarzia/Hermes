package unipa.prog3.model.entity.util;

import unipa.prog3.model.entity.Cliente;

public class ClientBuilder {
    private final Cliente cliente;

    public ClientBuilder() {
        cliente = new Cliente();
    }

    public ClientBuilder setNome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClientBuilder setCognome(String cognome) {
        cliente.setCognome(cognome);
        return this;
    }

    public ClientBuilder setStato(String stato) {
        cliente.setStato(stato);
        return this;
    }

    public ClientBuilder setCittà(String città) {
        cliente.setCittà(città);
        return this;
    }

    public ClientBuilder setCap(int cap) {
        cliente.setCap(cap);
        return this;
    }

    public ClientBuilder setIndirizzo(String indirizzo) {
        cliente.setIndirizzo(indirizzo);
        return this;
    }

    public ClientBuilder setEmail(String email) {
        cliente.setEmail(email);
        return this;
    }

    public ClientBuilder setTelefono(String telefono) {
        cliente.setTelefono(telefono);
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
