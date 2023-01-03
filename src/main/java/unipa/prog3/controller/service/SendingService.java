package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;

public class SendingService extends GenericService<Collo> {
    private final ClientService clientService;

    public SendingService() {
        super(DataManager.PACKAGES);
        clientService = new ClientService();
    }

    public Collo send(Cliente sender, Cliente receiver, float weight) {
        Cliente foundSender = clientService.findRecord(sender);
        if (foundSender == null)
            clientService.insert(sender);
        else sender.setID(foundSender.getID());

        Cliente foundReceiver = clientService.findRecord(receiver);
        if (foundReceiver == null)
            clientService.insert(receiver);
        else receiver.setID(foundReceiver.getID());
        
        Collo pack = new Collo(generateID(), sender, receiver, weight);
        insert(pack);
        return pack;
    }

    @Override
    public String entityToString(Collo pack) {
        return pack.getID() + DataManager.delimiter + pack.getMittente().getID() + DataManager.delimiter +
                pack.getDestinatario().getID() + DataManager.delimiter + pack.getPeso();
    }

    @Override
    public Collo entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        ClientService clientService = new ClientService();
        Cliente sender = clientService.find(info[1]);
        Cliente receiver = clientService.find(info[2]);
        float weight = Float.parseFloat(info[3]);
        return new Collo(info[0], sender, receiver, weight);
    }
}
