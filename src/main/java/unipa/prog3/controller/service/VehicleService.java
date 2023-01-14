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
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        Vector<Collo> packs = packageService.selectAll();
        return select(v -> {
            for (Collo pack : packs)
                if (pack.getVeicolo() != null && pack.getVeicolo().equalKeys(v))
                    return false;
            return true;
        });
    }

    @Override
    public Veicolo relationFromFields(String[] fields) {
        return new Veicolo(fields[0], fields[1], Float.parseFloat(fields[2]));
    }
}
