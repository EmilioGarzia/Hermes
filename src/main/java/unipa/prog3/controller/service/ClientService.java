package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Cliente;

import java.util.Vector;

public class ClientService extends GenericService<Cliente> {
    public ClientService() {
        super("clients");
    }

    public Cliente findRecord(Cliente cliente) {
        Vector<Cliente> clienti = select(cliente::equals);
        if (!clienti.isEmpty())
            return clienti.get(0);
        return null;
    }

    @Override
    public Cliente entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        Cliente client = new Cliente(info[0], info[1], info[2]);
        client.setStato(info[3]);
        client.setCittà(info[4]);
        client.setCap(Integer.parseInt(info[5]));
        client.setIndirizzo(info[6]);
        client.setEmail(info[7]);
        client.setTelefono(info[8]);
        return client;
    }

    @Override
    public String entityToString(Cliente cliente) {
        return cliente.getID() + DataManager.delimiter + cliente.getNome() + DataManager.delimiter + cliente.getCognome()
                + DataManager.delimiter + cliente.getStato() + DataManager.delimiter + cliente.getCittà()
                + DataManager.delimiter + cliente.getCap() + DataManager.delimiter + cliente.getIndirizzo()
                + DataManager.delimiter + cliente.getEmail() + DataManager.delimiter + cliente.getTelefono();
    }
}
