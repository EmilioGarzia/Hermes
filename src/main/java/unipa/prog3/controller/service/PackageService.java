package unipa.prog3.controller.service;

import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Veicolo;
import unipa.prog3.model.io.DataManager;

public class PackageService extends GenericService<Collo> {
    public PackageService() {
        super("packages");
    }

    @Override
    public Collo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);

        ClientService clientService = (ClientService) ServiceProvider.getService(Cliente.class);
        Cliente mittente = clientService.select(info[1]);
        Cliente destinatario = clientService.select(info[2]);

        double peso = Double.parseDouble(info[3]);

        Veicolo veicolo = null;
        if (!info[4].equals("null")) {
            VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
            veicolo = vehicleService.select(info[4]);
        }

        return new Collo(info[0], mittente, destinatario, peso, veicolo);
    }

    @Override
    public String entityToString(Collo collo) {
        StringBuilder builder = new StringBuilder();
        builder.append(collo.getID()).append(DataManager.delimiter).append(collo.getMittente().getID())
                .append(DataManager.delimiter).append(collo.getDestinatario().getID()).append(DataManager.delimiter)
                .append(collo.getPeso()).append(DataManager.delimiter);
        if (collo.getVeicolo() != null)
            builder.append(collo.getVeicolo().getID());
        else builder.append("null");;
        return builder.toString();
    }
}
