package unipa.prog3.model.relation;

import java.time.LocalDateTime;

public class Delivery extends Relation {
    public Delivery(Collo collo, Centro centro, Courier corriere, LocalDateTime timestamp) {
        super(3, 1);
        addKey(collo);
        addKey(centro);
        addKey(corriere);
        addData(timestamp);
    }

    public Delivery(Collo collo, Centro centro, Courier corriere) {
        this(collo, centro, corriere, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Delivery delivery)
            return getCollo().equals(delivery.getCollo()) && getCentro().equals(delivery.getCentro())
                && getCorriere().equals(delivery.getCorriere()) && getTimestamp().equals(delivery.getTimestamp());
        return false;
    }

    public Collo getCollo() {
        return (Collo) keys[0];
    }

    public Centro getCentro() {
        return (Centro) keys[1];
    }

    public Courier getCorriere() {
        return (Courier) keys[2];
    }

    public LocalDateTime getTimestamp() {
        return (LocalDateTime) data[0];
    }
}
