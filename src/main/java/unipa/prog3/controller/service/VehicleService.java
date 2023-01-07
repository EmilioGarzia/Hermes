package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Veicolo;

public class VehicleService extends GenericService<Veicolo> {
    public VehicleService() {
        super("vehicles");
    }

    @Override
    public Veicolo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        return new Veicolo(info[0], info[1], Double.parseDouble(info[2]));
    }

    @Override
    public String entityToString(Veicolo veicolo) {
        return veicolo.getID() + DataManager.delimiter + veicolo.getTipo() + DataManager.delimiter + veicolo.getCapienza();
    }
}
