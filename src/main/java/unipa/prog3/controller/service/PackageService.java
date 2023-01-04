package unipa.prog3.controller.service;

import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.io.DataManager;

public class PackageService extends GenericService<Collo> {
    private final ClientService clientService;

    public PackageService() {
        super("packages");
        clientService = (ClientService) ServiceProvider.getService(Cliente.class);
    }

    @Override
    public Collo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        Cliente mittente = clientService.select(info[1]);
        Cliente destinatario = clientService.select(info[2]);
        return new Collo(info[0], mittente, destinatario, Double.parseDouble(info[3]));
    }

    @Override
    public String entityToString(Collo collo) {
        return collo.getID() + DataManager.delimiter + collo.getMittente().getID() + DataManager.delimiter
                + collo.getDestinatario().getID() + DataManager.delimiter + collo.getPeso();
    }
}
