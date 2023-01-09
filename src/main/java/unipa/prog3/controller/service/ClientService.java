package unipa.prog3.controller.service;

import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Cliente;

import java.util.Vector;

public class ClientService extends GenericService<Cliente> {
    public ClientService() {
        super(TableProvider.TableName.CLIENTS);
    }

    public Cliente findRecord(Cliente cliente) {
        Vector<Cliente> clienti = select(cliente::equals);
        if (!clienti.isEmpty())
            return clienti.get(0);
        return null;
    }

    @Override
    public Cliente entityFromString(String s) {
        String[] info = s.split(Table.delimiter);
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
        return cliente.getID() + Table.delimiter + cliente.getNome() + Table.delimiter + cliente.getCognome()
                + Table.delimiter + cliente.getStato() + Table.delimiter + cliente.getCittà()
                + Table.delimiter + cliente.getCap() + Table.delimiter + cliente.getIndirizzo()
                + Table.delimiter + cliente.getEmail() + Table.delimiter + cliente.getTelefono();
    }
}
