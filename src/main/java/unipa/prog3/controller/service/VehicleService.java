package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Veicolo;

import java.util.Vector;

public class VehicleService extends GenericService<Veicolo> {
    public VehicleService() {
        super(TableProvider.TableName.VEHICLES);
    }

    public Vector<Veicolo> selectAvailable() {
        return select(v -> {
            PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
            Vector<Collo> packages = packageService.selectByVehicleNotDelivered(v);
            return packages.isEmpty();
        });
    }

    @Override
    public Veicolo relationFromFields(String[] fields) {
        return new Veicolo(fields[0], fields[1], Double.parseDouble(fields[2]));
    }
}
