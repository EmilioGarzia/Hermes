package unipa.prog3.model.entity;

import java.time.LocalDateTime;

public class Delivery extends Entity {
    private final Collo collo;
    private final Centro centro;
    private final Courier corriere;
    private final LocalDateTime timestamp;

    public Delivery(String id, Collo collo, Centro centro, Courier corriere, LocalDateTime timestamp) {
        super(id);
        this.collo = collo;
        this.centro = centro;
        this.corriere = corriere;
        this.timestamp = timestamp;
    }

    public Delivery(Collo collo, Centro centro, Courier corriere) {
        this(null, collo, centro, corriere, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Delivery delivery)
            return collo.id.equals(delivery.collo.id) && centro.id.equals(delivery.centro.id)
                    && corriere.id.equals(delivery.corriere.id);
        return false;
    }

    public Collo getCollo() {
        return collo;
    }

    public Centro getCentro() {
        return centro;
    }

    public Courier getCorriere() {
        return corriere;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
