package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Veicolo;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Courier;

import java.util.Vector;

/**
 * Specializzazione di un Service che gestisca le istanze della classe Courier
 */
public class CourierService extends GenericService<Courier> {
    public CourierService() {
        super(TableProvider.TableName.COURIERS);
    }

    /**
     * Seleziona tutti corrieri disponibili per la guida di una spedizione
     * @return Istanza di un Vector contenente i corrieri selezionati
     */
    public Vector<Courier> selectAvailable() {
        return select(courier -> courier.getVehicle() == null);
    }

    /**
     * Seleziona tutti i corrieri che sono già in viaggio
     * @return Istanza di un Vector contenente i corrieri selezionati
     */
    public Vector<Courier> selectTraveling() {
        return select(courier -> courier.getVehicle() != null);
    }

    /**
     * Verifica se il veicolo specificato sia già stato associato ad un corriere
     * @param veicolo Veicolo per cui effettuare il controllo
     * @return true se il veicolo è stato già associato ad un corriere, false altrimenti
     */
    public boolean existsDriverOfVehicle(Veicolo veicolo) {
        Vector<Courier> traveling = selectTraveling();
        for (Courier courier : traveling)
            if (courier.getVehicle().equalKeys(veicolo))
                return true;
        return false;
    }

    @Override
    public Courier relationFromFields(String[] fields) {
        // Carica il veicolo associato utilizzando la chiave contenuta nel relativo campo
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[3]);
        return new Courier(fields[0], fields[1], fields[2], veicolo);
    }
}