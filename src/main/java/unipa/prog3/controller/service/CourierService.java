package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Veicolo;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Courier;

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
    public String entityToString(Courier courier) {
        return courier.getID() + Table.delimiter + courier.getName() + Table.delimiter + courier.getSurname();
    }

    @Override
    public Courier entityFromString(String s) {
        String[] info = s.split(Table.delimiter);

        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo vehicle = vehicleService.select(info[3]);

        return new Courier(info[0], info[1], info[2], vehicle);
    }
}