package unipa.prog3.model.relation;

import java.time.LocalDateTime;

public class Consegna extends Relation {
    public Consegna(Collo collo, Centro centro, Corriere corriere, LocalDateTime timestamp) {
        super(3, 1);
        addKey(collo);
        addKey(centro);
        addKey(corriere);
        addData(timestamp);
    }

    public Consegna(Collo collo, Centro centro, Corriere corriere) {
        this(collo, centro, corriere, LocalDateTime.now());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Consegna consegna)
            return getCollo().equals(consegna.getCollo()) && getCentro().equals(consegna.getCentro())
                && getCorriere().equals(consegna.getCorriere()) && getTimestamp().equals(consegna.getTimestamp());
        return false;
    }

    public Collo getCollo() {
        return (Collo) keys[0];
    }

    public Centro getCentro() {
        return (Centro) keys[1];
    }

    public Corriere getCorriere() {
        return (Corriere) keys[2];
    }

    public LocalDateTime getTimestamp() {
        return (LocalDateTime) data[0];
    }
}
