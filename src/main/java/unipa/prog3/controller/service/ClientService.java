package unipa.prog3.controller.service;

import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Cliente;
import unipa.prog3.model.relation.util.ClientBuilder;

import java.util.Vector;

/**
 * Specializzazione di un Service che gestisca le istanze della classe Cliente
 */
public class ClientService extends GenericService<Cliente> {
    public ClientService() {
        super(TableProvider.TableName.CLIENTS);
    }

    /**
     * Seleziona un cliente dalla tabella che abbia tutti gli attributi (escluse le chiavi)
     * corrispondenti agli attributi dell'oggetto Cliente passato come argomento del metodo
     * @param cliente Istanza di un cliente da utilizzate per il confronto
     * @return Istanza del cliente caricato dalla tabella
     */
    public Cliente findRecord(Cliente cliente) {
        Vector<Cliente> clienti = select(cliente::equals);
        if (!clienti.isEmpty())
            return clienti.get(0);
        return null;
    }

    @Override
    public Cliente relationFromFields(String[] fields) {
        // Carica il centro di smistamento associato utilizzando la chiave contenuta nel relativo campo
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro centro = centerService.selectByLocation(fields[3], fields[4]);

        // Costruisce l'oggetto Cliente utilizzando il relativo Builder
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.setID(fields[0]).setNome(fields[1]).setCognome(fields[2]).setCentro(centro)
                .setCap(Integer.parseInt(fields[5])).setIndirizzo(fields[6]).setCivico(Integer.parseInt(fields[7]))
                .setTelefono(fields[9]);
        if (fields[8].equals("null"))
            clientBuilder.setEmail(null);
        return clientBuilder.getCliente();
    }
}
