package unipa.prog3.model.entity;

public class Route extends Entity {
    private final Centro centro1, centro2;

    public Route(Centro centro1, Centro centro2) {
        super(null);
        this.centro1 = centro1;
        this.centro2 = centro2;
    }

    public Centro getCentro1() {
        return centro1;
    }

    public Centro getCentro2() {
        return centro2;
    }
}
