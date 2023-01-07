package unipa.prog3.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.genetica.Cromosoma;
import unipa.prog3.controller.genetica.Popolazione;
import unipa.prog3.controller.service.ClientService;
import unipa.prog3.controller.service.PackageService;
import unipa.prog3.controller.service.VehicleService;
import unipa.prog3.controller.service.util.ServiceProvider;
import unipa.prog3.model.entity.Cliente;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Veicolo;

import java.util.HashMap;
import java.util.Vector;

public class SendController extends Controller {
    @FXML
    private ChoiceBox<String> senderChooser, receiverChooser;
    @FXML
    private TextField weightField;

    private final HashMap<String, Cliente> clientsMap;
    private final PackageService packageService;

    private Vector<Veicolo> veicoli;
    private Vector<Collo> colli;
    private final Popolazione popolazione;

    public SendController() {
        super();
        clientsMap = new HashMap<>();
        packageService = (PackageService) ServiceProvider.getService(Collo.class);
        popolazione = new Popolazione(100);
    }

    public void initialize() {
        populateChooser();
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            if (newValue.intValue() == clientsMap.size())
                MainApplication.getMainController().loadView("/unipa/prog3/client-view.fxml");
        };

        senderChooser.getSelectionModel().selectedIndexProperty().addListener(listener);
        receiverChooser.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    @FXML
    public void send() {
        Cliente sender = clientsMap.get(senderChooser.getValue());
        Cliente receiver = clientsMap.get(receiverChooser.getValue());
        if (sender == receiver) {
            // TODO Add error label and update it here
            return;
        }

        float weight = Float.parseFloat(weightField.getText());
        Collo collo = new Collo(null, sender, receiver, weight);
        packageService.insert(collo);
        spedisciVeicoli(collo);
    }

    private void spedisciVeicoli(Collo collo) {
        if (veicoli == null) {
            VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
            veicoli = vehicleService.selectAll();
        }

        if (colli == null)
            colli = packageService.selectAll();
        else colli.add(collo);

        while(!colli.isEmpty() && !veicoli.isEmpty()) {
            Cromosoma best = null;
            for (Veicolo v : veicoli) {
                Cromosoma soluzione = popolazione.findBestSolutionForSingleVehicle(v, colli, 10);
                if (best == null || soluzione.fitness() > best.fitness())
                    best = soluzione;
            }

            for (Collo c : best) {
                c.setVeicolo(best.getVeicolo());
                packageService.update(c);
                colli.remove(c);
                System.out.println("Il collo " + c.getID() + " è stato assegnato al veicolo " + best.getVeicolo().getID());
            }
            veicoli.remove(best.getVeicolo());
        }
    }

    @Override
    public void onResume() {
        populateChooser();
    }

    private void populateChooser() {
        ClientService clientService = (ClientService) ServiceProvider.getService(Cliente.class);
        Vector<Cliente> clienti = clientService.selectAll();
        Vector<String> nomi = new Vector<>();

        clientsMap.clear();
        for (Cliente cliente : clienti) {
            String nome = cliente.getNome() + " " + cliente.getCognome();
            nomi.add(nome);
            clientsMap.put(nome, cliente);
        }
        nomi.add("+ Aggiungi indirizzo");

        int selectedSender = senderChooser.getSelectionModel().getSelectedIndex();
        senderChooser.setItems(FXCollections.observableList(nomi));
        senderChooser.getSelectionModel().select(selectedSender);

        int selectedReceiver = receiverChooser.getSelectionModel().getSelectedIndex();
        receiverChooser.setItems(FXCollections.observableList(nomi));
        receiverChooser.getSelectionModel().select(selectedReceiver);
    }
}
