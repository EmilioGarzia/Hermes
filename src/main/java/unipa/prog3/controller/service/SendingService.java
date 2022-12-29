package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;

public class SendingService extends GenericService<Collo> {
    private final ClientService clientService;

    public SendingService() {
        super(DataManager.Table.PACKAGES);
        clientService = new ClientService();
    }

    public Collo send(Cliente sender, Cliente receiver, float weight) {
        clientService.insert(sender);
        clientService.insert(receiver);
        Collo pack = new Collo(null, sender, receiver, weight);
        insert(pack);
        return pack;
    }

    public String entityToString(Collo pack) {
        return pack.getID() + DataManager.delimiter + pack.getMittente().getID() + DataManager.delimiter +
                pack.getDestinatario().getID() + DataManager.delimiter + pack.getPeso();
    }

    public Collo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        ClientService clientService = new ClientService();
        Cliente sender = clientService.find(info[1]);
        Cliente receiver = clientService.find(info[2]);
        float weight = Float.parseFloat(info[3]);
        return new Collo(info[0], sender, receiver, weight);
    }
}
