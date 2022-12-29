package unipa.prog3.model.entity;

public abstract class Entity {
    protected String id;

    protected Entity(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
