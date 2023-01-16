package unipa.prog3.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import unipa.prog3.controller.helper.CarrierHelper;
import unipa.prog3.controller.service.*;
import unipa.prog3.model.relation.*;

import java.util.Vector;

/**
 * Classe che gestisce le interazioni dell'utente con i widget della courier-view.fxml
 * */
public class CourierController extends Controller {
    @FXML
    private ChoiceBox<Corriere> courierChooser;
    @FXML
    private ChoiceBox<Collo> packageChooser;
    @FXML
    private Label statusLabel;
    @FXML
    private BorderPane formPane;

    /**
     * Metodo invocato automaticamente da JavaFX per l'inizializzazione della view
     * */
    public void initialize() {
        courierChooser.getItems().clear();
        packageChooser.getItems().clear();

        CourierService courierService = (CourierService) ServiceProvider.getService(Corriere.class);
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);

        Vector<Corriere> corrieres = courierService.selectTraveling();
        if (corrieres.isEmpty()) {
            // Se non ci sono corrieri in viaggio, non è possibile effettuare alcuna segnalazione
            formPane.setDisable(true);
            return;
        }

        formPane.setDisable(false);

        // Inizializza gli elementi delle ChoiceBox utilizzate per segnalare una consegna
        courierChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Corriere corriere) {
                if (corriere != null)
                    return corriere.getID();
                return null;
            }

            @Override
            public Corriere fromString(String s) {
                return courierService.select(s);
            }
        });
        courierChooser.setItems(FXCollections.observableList(corrieres));
        courierChooser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null)
                packageChooser.setItems(FXCollections.observableList(packageService.selectByVehicleNotDelivered(newValue.getVehicle())));
        });

        packageChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Collo collo) {
                if (collo != null)
                    return collo.getCodice();
                return null;
            }

            @Override
            public Collo fromString(String s) {
                return packageService.select(s);
            }
        });
    }

    /**
     * Metodo che gestisce la segnalazione di una consegna informando
     * anche dell'avvenuta/mancata segnalazione della stessa
     * */
    @FXML
    public void report() {
        Corriere corriere = courierChooser.getValue();
        if (corriere == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Devi indicare il tuo codice da corriere!");
            return;
        }

        Collo pack = packageChooser.getValue();
        if (pack == null) {
            statusLabel.setTextFill(Color.RED);
            statusLabel.setText("Devi selezionare un collo!");
            return;
        }

        // Cerca l'ultima consegna effettuata per il collo selezionato
        RouteService routeService = (RouteService) ServiceProvider.getService(Rotta.class);
        CarrierHelper carrierHelper = new CarrierHelper(routeService.selectAll());
        Vector<Centro> path = carrierHelper.findPath(pack.getPartenza(), pack.getDestinazione());
        DeliveryService deliveryService = (DeliveryService) ServiceProvider.getService(Consegna.class);
        Consegna lastConsegna = deliveryService.selectLastByPackage(pack);

        Centro center;
        if (lastConsegna == null)
            center = path.get(0); // Quando non è stata ancora effettuata nessuna segnalazione per quel collo
        else center = carrierHelper.nextStep(path, lastConsegna.getCentro());

        // Inserisce la consegna nella relativa tabella
        Consegna consegna = new Consegna(pack, center, corriere);
        deliveryService.insert(consegna);

        if (center.equals(pack.getDestinazione())) {
            PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
            pack.setConsegnato(true);
            packageService.update(pack);
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Consegna del collo avvenuta!");

            if (packageService.selectByVehicleNotDelivered(corriere.getVehicle()).isEmpty()) {
                // Quando il corriere ha consegnato tutti i colli, ritorna disponibile per una nuova spedizione
                corriere.setVehicle(null);
                CourierService courierService = (CourierService) ServiceProvider.getService(Corriere.class);
                courierService.update(corriere);
            }
        } else {
            // Quando il collo è arrivato a destinazione
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Segnalazione avvenuta con successo!");
        }

        // Reinizializza la view
        initialize();
    }

    @Override
    public void onResume() {}
}
