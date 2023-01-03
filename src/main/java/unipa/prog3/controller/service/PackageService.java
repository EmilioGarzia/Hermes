package unipa.prog3.controller.service;

import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.io.DataManager;

public class PackageService extends GenericService<Collo> {
    private final ClientService clientService;

    public PackageService() {
        super(DataManager.PACKAGES);
        clientService = (ClientService) ServiceProvider.getService(Cliente.class);
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
