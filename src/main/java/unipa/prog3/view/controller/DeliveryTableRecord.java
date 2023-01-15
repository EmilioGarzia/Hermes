package unipa.prog3.view.controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Classe per la gestione dei dati all'interno della tabella contenuta nella tracking-view.fxml
 * */
public class DeliveryTableRecord {
    private final SimpleStringProperty timestamp;
    private final SimpleStringProperty description;

    public DeliveryTableRecord(String timestamp, String description) {
        this.timestamp = new SimpleStringProperty(timestamp);
        this.description = new SimpleStringProperty(description);
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp.set(timestamp);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
