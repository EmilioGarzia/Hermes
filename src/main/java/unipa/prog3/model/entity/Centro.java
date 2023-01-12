package unipa.prog3.model.entity;

public class Centro extends Entity {
    private final String città, stato;

    public Centro(String id, String città, String stato) {
        super(id);
        this.città = città;
        this.stato = stato;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Centro)
            return ((Centro) obj).getID().equals(id);
        return false;
    }

    public String getCittà() {
        return città;
    }

    public String getStato() {
        return stato;
    }
}
