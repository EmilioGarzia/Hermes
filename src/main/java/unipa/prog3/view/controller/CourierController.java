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
    private ChoiceBox<Courier> courierChooser;
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

        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);

        Vector<Courier> couriers = courierService.selectTraveling();
        if (couriers.isEmpty()) {
            formPane.setDisable(true);
            return;
        }

        formPane.setDisable(false);

        courierChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Courier courier) {
                if (courier != null)
                    return courier.getID();
                return null;
            }

            @Override
            public Courier fromString(String s) {
                return courierService.select(s);
            }
        });
        courierChooser.setItems(FXCollections.observableList(couriers));
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
     * Metodo che gestisce la segnalazione di un collo informando anche dell'avvenuta/mancata segnalazione di un collo da parte di un corriere
     * */
    @FXML
    public void report() {
        Courier courier = courierChooser.getValue();
        if (courier == null) {
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

        RouteService routeService = (RouteService) ServiceProvider.getService(Route.class);
        CarrierHelper carrierHelper = new CarrierHelper(routeService.selectAll());
        Vector<Centro> path = carrierHelper.findPath(pack.getPartenza(), pack.getDestinazione());
        DeliveryService deliveryService = (DeliveryService) ServiceProvider.getService(Delivery.class);
        Delivery lastDelivery = deliveryService.selectLastByPackage(pack);

        Centro center;
        if (lastDelivery == null)
            center = path.get(0);
        else center = carrierHelper.nextStep(path, lastDelivery.getCentro());

        Delivery delivery = new Delivery(pack, center, courier);
        deliveryService.insert(delivery);

        if (center.equals(pack.getDestinazione())) {
            PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
            pack.setConsegnato(true);
            packageService.update(pack);
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Consegna del collo avvenuta!");

            if (packageService.selectByVehicleNotDelivered(courier.getVehicle()).isEmpty()) {
                courier.setVehicle(null);
                CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
                courierService.update(courier);
            }
        } else {
            statusLabel.setTextFill(Color.GREEN);
            statusLabel.setText("Segnalazione avvenuta con successo!");
        }

        initialize();
    }

    @Override
    public void onResume() {}
}
