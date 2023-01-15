package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Cliente;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;
import unipa.prog3.model.io.TableProvider;

import java.util.Vector;

/**
 * Specializzazione di un Service per la gestione dei colli
 */
public class PackageService extends GenericService<Collo> {
    public PackageService() {
        super(TableProvider.TableName.PACKAGES);
    }

    /**
     * Seleziona tutti i colli non ancora spediti
     * @return Istanza di un Vector contenente i colli selezionati
     */
    public Vector<Collo> selectNotSent() {
        return select(collo -> collo.getVeicolo() == null);
    }

    /**
     * Seleziona tutti i colli contenuti nel container di un determinato veicolo
     * @param veicolo Istanza del veicolo che contiene i colli richiesti
     * @return Istanza di Vector che contiene i colli selezionati
     */
    public Vector<Collo> selectByVehicleNotDelivered(Veicolo veicolo) {
        return select(collo -> collo.getVeicolo() != null && !collo.isConsegnato()
                && collo.getVeicolo().equalKeys(veicolo));
    }

    @Override
    public Collo relationFromFields(String[] fields) {
        // Carica gli indirizzi del mittente e del destinatario utilizzando le chiavi contenute nei relativi campi
        ClientService clientService = (ClientService) ServiceProvider.getService(Cliente.class);
        Cliente mittente = clientService.select(fields[1]);
        Cliente destinatario = clientService.select(fields[2]);

        // Effettua il parsing di alcune informazioni
        float peso = Float.parseFloat(fields[3]);
        boolean consegnato = Boolean.parseBoolean(fields[4]);

        // Carica, se possibile, il veicolo corrispondente alla chiave contenuta nel relativo campo
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[5]);
        return new Collo(fields[0], mittente, destinatario, peso, consegnato, veicolo);
    }
}
