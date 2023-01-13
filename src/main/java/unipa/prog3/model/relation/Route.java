package unipa.prog3.model.relation;

public class Route extends Relation {
    public Route(Centro centro1, Centro centro2) {
        super(2, 0);
        addKey(centro1);
        addKey(centro2);
    }

    public Centro getCentro1() {
        return (Centro) keys[0];
    }

    public Centro getCentro2() {
        return (Centro) keys[1];
    }
}
