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
import unipa.prog3.controller.helper.CarrierHelper;
import unipa.prog3.controller.service.*;
import unipa.prog3.model.relation.*;

import java.util.HashMap;
import java.util.Vector;

public class SendController extends Controller {
    @FXML
    private ChoiceBox<String> senderChooser, receiverChooser;
    @FXML
    private TextField weightField;
    @FXML
    private TextField errorLabel;

    private final HashMap<String, Cliente> clientsMap;
    private final PackageService packageService;

    private CarrierHelper carrierHelper;
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
                MainApplication.getMainController().showView("/unipa/prog3/view/client-view.fxml");
        };

        senderChooser.getSelectionModel().selectedIndexProperty().addListener(listener);
        receiverChooser.getSelectionModel().selectedIndexProperty().addListener(listener);

        RouteService routeService = (RouteService) ServiceProvider.getService(Route.class);
        carrierHelper = new CarrierHelper(routeService.selectAll());
    }

    @FXML
    public void send() {
        errorLabel.setStyle("-fx-text-fill: red");
        if (senderChooser.getValue() == null) {
            errorLabel.setText("Devi selezionare un mittente!");
            return;
        } else if (receiverChooser.getValue() == null) {
            errorLabel.setText("Devi selezionare un destinatario!");
            return;
        }

        Cliente sender = clientsMap.get(senderChooser.getValue());
        Cliente receiver = clientsMap.get(receiverChooser.getValue());
        if (sender == receiver) {
            errorLabel.setText("Il destinatario non può coincidere con il mittente!");
            return;
        }

        float weight;
        try {
            weight = Float.parseFloat(weightField.getText());
        } catch (NumberFormatException e) {
            errorLabel.setText("Il peso inserito non è valido!");
            return;
        }

        Collo collo = new Collo(null, sender, receiver, weight);
        packageService.insert(collo);
        errorLabel.setStyle("-fx-text-fill: #00c700");
        errorLabel.setText("Utilizza il codice " + collo.getCodice() + " per tracciare la tua spedizione");
        errorLabel.setVisible(true);
        spedisciVeicoli();
    }

    private void spedisciVeicoli() {
        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        Vector<Courier> couriers = courierService.selectAvailable();
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Vector<Veicolo> veicoli = vehicleService.selectAvailable();
        Vector<Collo> colli = packageService.selectNotSent();
        System.out.println("Colli: " + colli.size() + ", Veicoli: " + veicoli.size() + ", Corrieri: " + couriers.size());

        while(!colli.isEmpty() && !veicoli.isEmpty() && !couriers.isEmpty()) {
            Vector<Collo> bestLoad = carrierHelper.findBestLoad(colli);
            Cromosoma best = null;
            for (Veicolo v : veicoli) {
                Cromosoma soluzione = popolazione.findBestSolutionForSingleVehicle(v, bestLoad, 1);
                if (best == null || soluzione.size() > best.size())
                    best = soluzione;
            }

            if (best == null) return;
            for (Collo c : best) {
                c.setVeicolo(best.getVehicle());
                packageService.update(c);
                colli.remove(c);
                System.out.println("Il collo " + c.getCodice() + " è stato assegnato al veicolo " + best.getVehicle().getCodice());
            }
            veicoli.remove(best.getVehicle());

            Courier courier = couriers.get((int) (Math.random()*couriers.size()));
            courier.setVehicle(best.getVehicle());
            courierService.update(courier);
            couriers.remove(courier);
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
