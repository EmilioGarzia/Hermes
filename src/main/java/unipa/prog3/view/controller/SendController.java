package unipa.prog3.view.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import unipa.prog3.MainApplication;
import unipa.prog3.controller.genetica.Cromosoma;
import unipa.prog3.controller.genetica.Popolazione;
import unipa.prog3.controller.helper.CarrierHelper;
import unipa.prog3.controller.service.*;
import unipa.prog3.model.relation.*;

import java.util.HashMap;
import java.util.Vector;

/**
 * Classe che gestisce le interazioni dell'utente con i vari widget della view send-view.fxml
 * */
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

    /**
     * Inizializza gli elementi che compongono la view
     */
    public void initialize() {
        populateChooser();
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            if (newValue.intValue() == clientsMap.size())
                MainApplication.getMainController().showView("/unipa/prog3/view/client-view.fxml");
        };

        senderChooser.getSelectionModel().selectedIndexProperty().addListener(listener);
        receiverChooser.getSelectionModel().selectedIndexProperty().addListener(listener);

        RouteService routeService = (RouteService) ServiceProvider.getService(Rotta.class);
        carrierHelper = new CarrierHelper(routeService.selectAll());
    }

    /**
     * Gestisce il processo di spedizione subito dopo che l'utente clicca su "Spedisci" nella view di send-view.fxml,
     * informando lo stesso sull'esito dell'operazione
     * */
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
        spedisciColli();
    }

    /**
     * Utilizza l'algoritmo di IA (Algoritmo Genetico) per la selezione dei veicoli
     * da utilizzare per spedire i colli che sono ancora in fase di elaborazione
     * */
    private void spedisciColli() {
        CourierService courierService = (CourierService) ServiceProvider.getService(Corriere.class);
        Vector<Corriere> corrieres = courierService.selectAvailable();
        VehicleService vehicleService = (VehicleService) ServiceProvider.getService(Veicolo.class);
        Vector<Veicolo> veicoli = vehicleService.selectAvailable();
        Vector<Collo> colli = packageService.selectNotSent();

        while(!veicoli.isEmpty() && !corrieres.isEmpty()) {
            Cromosoma best = null;
            for (Collo collo : colli) {
                // Trova l'insieme di colli che dovranno seguire un percorso simile a quello corrente
                Vector<Collo> bestLoad = carrierHelper.findBestLoad(colli, collo);

                // Cerca il veicolo con il fattore di carico maggiore
                for (Veicolo v : veicoli) {
                    Cromosoma soluzione = popolazione.findBestSolutionForSingleVehicle(v, bestLoad, 10);
                    if (best == null || soluzione.weightRatio() > best.weightRatio())
                        best = soluzione;
                }
            }

            // Se il veicolo ha un fattore di carico maggiore o uguale all'80%, allora può partire per la sua spedizione
            if (best != null && best.weightRatio() >= 0.8) {
                // Associa ad ogni collo il veicolo nel quale deve essere caricato
                for (Collo c : best) {
                    c.setVeicolo(best.getVehicle());
                    packageService.update(c);
                    colli.remove(c);
                    System.out.println("Il collo " + c.getCodice() + " è stato assegnato al veicolo " + best.getVehicle().getCodice());
                }

                // Rimuove il veicolo dalla lista dei veicoli disponibili
                veicoli.remove(best.getVehicle());
                Corriere corriere = corrieres.get((int) (Math.random() * corrieres.size()));
                corriere.setVehicle(best.getVehicle());
                courierService.update(corriere);
                corrieres.remove(corriere);
            } else break;
        }
    }

    @Override
    public void onResume() {
        populateChooser();
    }

    /**
     * Carica nelle choiceBox gli indirizzi già memorizzati e l'opzione per aggiungerne di nuovi
     * */
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

    /**
     * Aggiunge alla choiceBox il nuovo recod aggiunto nella client-view.fxml
     * @param chooser,nomi
     * */
    private void updateChooser(ChoiceBox<String> chooser, Vector<String> nomi) {
        int selected = chooser.getSelectionModel().getSelectedIndex();
        chooser.setItems(FXCollections.observableList(nomi));
        if (selected < nomi.size()-1)
            chooser.getSelectionModel().select(selected);
    }
}
