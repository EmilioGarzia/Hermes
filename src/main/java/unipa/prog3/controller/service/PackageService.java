package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.io.Table;

public class PackageService extends GenericService<Collo> {
    private final ClientService clientService;

    public PackageService(Table table) {
        super(table);
        clientService = new ClientService();
    }

    @Override
    public Collo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        Cliente mittente = clientService.find(info[1]);
        Cliente destinatario = clientService.find(info[2]);
        return new Collo(info[0], mittente, destinatario, Double.parseDouble(info[3]));
    }

    @Override
    public String entityToString(Collo collo) {
        return null;
    }
}
