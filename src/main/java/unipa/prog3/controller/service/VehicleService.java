package unipa.prog3.controller.service;

import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Veicolo;

public class VehicleService extends GenericService<Veicolo> {
    public VehicleService() {
        super(TableProvider.TableName.VEHICLES);
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
