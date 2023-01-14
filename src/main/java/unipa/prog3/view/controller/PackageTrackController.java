package unipa.prog3.view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import unipa.prog3.controller.helper.CarrierHelper;
import unipa.prog3.controller.service.DeliveryService;
import unipa.prog3.controller.service.RouteService;
import unipa.prog3.controller.service.ServiceProvider;
import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Delivery;
import unipa.prog3.model.relation.Route;

import java.time.LocalDateTime;
import java.util.Vector;

public class PackageTrackController extends Controller {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label statusLabel;
    @FXML
    private TableView<DeliveryTableRecord> historyTable;
    @FXML
    private TableColumn<DeliveryTableRecord, String> timestampField, descriptionField;

    public void initialize() {
        Collo collo = TrackController.getCollo();
        StringBuilder builder = new StringBuilder();
        builder.append("Stato della spedizione ").append(collo.getCodice()).append(": ");
        if (collo.getVeicolo() == null) {
            builder.append("In elaborazione...");
            return;
        } else if (collo.isConsegnato())
            builder.append("Consegnato!");
        else builder.append("In consegna...");
        statusLabel.setText(builder.toString());

        RouteService routeService = (RouteService) ServiceProvider.getService(Route.class);
        CarrierHelper helper = new CarrierHelper(routeService.selectAll());
        Vector<Centro> path = helper.findPath(collo.getPartenza(), collo.getDestinazione());

        DeliveryService deliveryService = (DeliveryService) ServiceProvider.getService(Delivery.class);
        Vector<Delivery> deliveries = deliveryService.selectByPackage(collo);

        if (deliveries.size() > 0) {
            System.out.println("Fatto");
            Delivery last = deliveries.get(deliveries.size() - 1);
            int index = path.indexOf(last.getCentro())+1;
            progressBar.setProgress((double) index / path.size());

            timestampField.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            descriptionField.setCellValueFactory(new PropertyValueFactory<>("description"));

            Vector<DeliveryTableRecord> records = new Vector<>();
            deliveries.forEach(delivery -> {
                LocalDateTime dateTime = delivery.getTimestamp();
                String timestamp = dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear()
                        + " " + dateTime.getHour() + ":" + dateTime.getMinute();
                String description = "Il collo è arrivato al centro di smistamento di "
                        + String.join(" - ", delivery.getCentro().keysToString());
                records.add(new DeliveryTableRecord(timestamp, description));
            });
            historyTable.setItems(FXCollections.observableList(records));
        }
    }

    @Override
    public void onResume() {

    }
}
