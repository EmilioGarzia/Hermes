package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Veicolo;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Courier;

import java.util.Vector;

public class CourierService extends GenericService<Courier> {
    public CourierService() {
        super(TableProvider.TableName.COURIERS);
    }

    public Vector<Courier> selectAvailable() {
        return select(courier -> courier.getVehicle() == null);
    }

    public Vector<Courier> selectTraveling() {
        return select(courier -> courier.getVehicle() != null);
    }

    @Override
    public Courier relationFromFields(String[] fields) {
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[3]);
        return new Courier(fields[0], fields[1], fields[2], veicolo);
    }
}