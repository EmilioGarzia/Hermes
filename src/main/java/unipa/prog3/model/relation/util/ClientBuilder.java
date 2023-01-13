package unipa.prog3.model.relation.util;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Cliente;

public class ClientBuilder {
    private final Cliente cliente;

    public ClientBuilder() {
        cliente = new Cliente();
    }

    public ClientBuilder setID(String id) {
        cliente.setKeys(id);
        return this;
    }

    public ClientBuilder setNome(String nome) {
        cliente.setNome(nome);
        return this;
    }

    public ClientBuilder setCognome(String cognome) {
        cliente.setCognome(cognome);
        return this;
    }

    public ClientBuilder setCentro(Centro centro) {
        cliente.setCentro(centro);
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

    public ClientBuilder setCivico(int civico) {
        cliente.setCivico(civico);
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
