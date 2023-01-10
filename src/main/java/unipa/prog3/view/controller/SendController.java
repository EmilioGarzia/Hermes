package unipa.prog3.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
    @FXML
    private Label errorLabel;

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
        errorLabel.setTextFill(Color.RED);
        Cliente sender = clientsMap.get(senderChooser.getValue());
        Cliente receiver = clientsMap.get(receiverChooser.getValue());
        if (sender == receiver) {
            errorLabel.setText("Il destinatario non può coincidere con il mittente!");
            return;
        }

        float weight = Float.parseFloat(weightField.getText());
        Collo collo = new Collo(null, sender, receiver, weight);
        packageService.insert(collo);
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText("Utilizza il codice " + collo.getID() + " per tracciare la tua spedizione");
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
                if (best == null || soluzione.weightRatio() > best.weightRatio())
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

        updateChooser(senderChooser, nomi);
        updateChooser(receiverChooser, nomi);
    }

    private void updateChooser(ChoiceBox<String> chooser, Vector<String> nomi) {
        int selected = chooser.getSelectionModel().getSelectedIndex();
        chooser.setItems(FXCollections.observableList(nomi));
        if (selected < nomi.size()-1)
            chooser.getSelectionModel().select(selected);
    }
}
