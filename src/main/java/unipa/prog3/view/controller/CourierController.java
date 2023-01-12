package unipa.prog3.view.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;
import unipa.prog3.controller.helper.CarrierHelper;
import unipa.prog3.controller.service.*;
import unipa.prog3.model.entity.*;

import java.util.Vector;

public class CourierController extends Controller {
    @FXML
    private ChoiceBox<Courier> courierChooser;
    @FXML
    private ChoiceBox<Collo> packageChooser;
    @FXML
    private ChoiceBox<Centro> centerChooser;

    public void initialize() {
        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);

        courierChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Courier courier) {
                return courier.getID();
            }

            @Override
            public Courier fromString(String s) {
                return courierService.select(s);
            }
        });
        courierChooser.setItems(FXCollections.observableList(courierService.selectTraveling()));
        courierChooser.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
            packageChooser.setItems(FXCollections.observableList(packageService.selectByVehicle(newValue.getVehicle()))));

        packageChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Collo collo) {
                return collo.getID();
            }

            @Override
            public Collo fromString(String s) {
                return packageService.select(s);
            }
        });
        packageChooser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                centerChooser.setItems(FXCollections.observableList(findCentersByPackage(newValue))));

        centerChooser.setConverter(new StringConverter<>() {
            @Override
            public String toString(Centro centro) {
                return centro.getCittà() + " - " + centro.getStato();
            }

            @Override
            public Centro fromString(String s) {
                String[] info = s.split(" - ");
                return centerService.selectByLocation(info[0], info[1]);
            }
        });
    }

    private Vector<Centro> findCentersByPackage(Collo pack) {
        RouteService routeService = (RouteService) ServiceProvider.getService(Route.class);
        CarrierHelper helper = new CarrierHelper(routeService.selectAll());
        return helper.findPath(pack.getPartenza(), pack.getDestinazione());
    }

    @FXML
    public void report() {
        Courier courier = courierChooser.getValue();
        Collo pack = packageChooser.getValue();
        Centro center = centerChooser.getValue();

        if (center.equals(pack.getDestinazione())) {
            pack.setConsegnato(true);
            ((PackageService) ServiceProvider.getService(Collo.class)).update(pack);
        }

        DeliveryService deliveryService = (DeliveryService) ServiceProvider.getService(Delivery.class);
        Delivery delivery = new Delivery(pack, center, courier);
        if (!deliveryService.exists(delivery))
            deliveryService.insert(delivery);
    }

    @Override
    public void onResume() {}
}
