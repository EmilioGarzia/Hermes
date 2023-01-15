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

    @Override
    public Courier relationFromFields(String[] fields) {
        // Carica il veicolo associato utilizzando la chiave contenuta nel relativo campo
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[3]);
        return new Courier(fields[0], fields[1], fields[2], veicolo);
    }
}