package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Corriere;
import unipa.prog3.model.relation.Veicolo;
import unipa.prog3.model.io.TableProvider;

import java.util.Vector;

/**
 * Specializzazione di un Service che gestisca le istanze della classe Courier
 */
public class CourierService extends GenericService<Corriere> {
    public CourierService() {
        super(TableProvider.TableName.COURIERS);
    }

    /**
     * Seleziona tutti corrieri disponibili per la guida di una spedizione
     * @return Istanza di un Vector contenente i corrieri selezionati
     */
    public Vector<Corriere> selectAvailable() {
        return select(courier -> courier.getVehicle() == null);
    }

    /**
     * Seleziona tutti i corrieri che sono già in viaggio
     * @return Istanza di un Vector contenente i corrieri selezionati
     */
    public Vector<Corriere> selectTraveling() {
        return select(courier -> courier.getVehicle() != null);
    }

    /**
     * Verifica se il veicolo specificato sia già stato associato ad un corriere
     * @param veicolo Veicolo per cui effettuare il controllo
     * @return true se il veicolo è stato già associato ad un corriere, false altrimenti
     */
    public boolean existsDriverOfVehicle(Veicolo veicolo) {
        Vector<Corriere> traveling = selectTraveling();
        for (Corriere corriere : traveling)
            if (corriere.getVehicle().equalKeys(veicolo))
                return true;
        return false;
    }

    @Override
    public Corriere relationFromFields(String[] fields) {
        // Carica il veicolo associato utilizzando la chiave contenuta nel relativo campo
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[3]);
        return new Corriere(fields[0], fields[1], fields[2], veicolo);
    }
}