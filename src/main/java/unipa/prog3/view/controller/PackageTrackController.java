package unipa.prog3.view.controller;

import javafx.collections.FXCollections;
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
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.Vector;

/**
 * Classe per la gestione delle interazione dell'utente con i widget della view tracking-view.fxml
 * */
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
        if (collo.getVeicolo() == null)
            builder.append("In elaborazione...");
        else if (collo.isConsegnato())
            builder.append("Consegnato!");
        else builder.append("In consegna...");
        statusLabel.setText(builder.toString());

        if (collo.getVeicolo() == null) return;

        RouteService routeService = (RouteService) ServiceProvider.getService(Route.class);
        CarrierHelper helper = new CarrierHelper(routeService.selectAll());
        Vector<Centro> path = helper.findPath(collo.getPartenza(), collo.getDestinazione());

        DeliveryService deliveryService = (DeliveryService) ServiceProvider.getService(Delivery.class);
        Vector<Delivery> deliveries = deliveryService.selectByPackage(collo);

        if (deliveries.size() > 0) {
            progressBar.setProgress((double) deliveries.size() / path.size());

            timestampField.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
            descriptionField.setCellValueFactory(new PropertyValueFactory<>("description"));

            deliveries.sort(Comparator.comparing(Delivery::getTimestamp));
            Vector<DeliveryTableRecord> records = new Vector<>();
            deliveries.forEach(delivery -> {
                LocalDateTime dateTime = delivery.getTimestamp();
                String timestamp = dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
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
