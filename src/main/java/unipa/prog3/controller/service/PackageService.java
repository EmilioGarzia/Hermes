package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Cliente;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Veicolo;
import unipa.prog3.model.io.TableProvider;

import java.util.Vector;

public class PackageService extends GenericService<Collo> {
    public PackageService() {
        super(TableProvider.TableName.PACKAGES);
    }

    public Vector<Collo> selectNotSent() {
        return select(collo -> collo.getVeicolo() == null);
    }

    public Vector<Collo> selectByVehicleNotDelivered(Veicolo veicolo) {
        return select(collo -> collo.getVeicolo() != null && !collo.isConsegnato()
                && collo.getVeicolo().equalKeys(veicolo));
    }

    @Override
    public Collo relationFromFields(String[] fields) {
        ClientService clientService = (ClientService) ServiceProvider.getService(Cliente.class);
        Cliente mittente = clientService.select(fields[1]);
        Cliente destinatario = clientService.select(fields[2]);

        float peso = Float.parseFloat(fields[3]);
        boolean consegnato = Boolean.parseBoolean(fields[4]);

        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Veicolo veicolo = vehicleService.select(fields[5]);
        return new Collo(fields[0], mittente, destinatario, peso, consegnato, veicolo);
    }
}
