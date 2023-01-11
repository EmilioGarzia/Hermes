package unipa.prog3.controller.service;

import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Veicolo;

import java.util.Vector;

public class VehicleService extends GenericService<Veicolo> {
    public VehicleService() {
        super(TableProvider.TableName.VEHICLES);
    }

    public Vector<Veicolo> selectAvailable() {
        return select(v -> {
            PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
            Vector<Collo> packages = packageService.selectByVehicle(v);
            for (Collo collo : packages)
                if (!collo.isConsegnato())
                    return false;
            return true;
        });
    }

    @Override
    public Veicolo entityFromString(String s) {
        String[] info = s.split(Table.delimiter);
        return new Veicolo(info[0], info[1], Double.parseDouble(info[2]));
    }

    @Override
    public String entityToString(Veicolo veicolo) {
        return veicolo.getID() + Table.delimiter + veicolo.getTipo() + Table.delimiter + veicolo.getCapienza();
    }
}
