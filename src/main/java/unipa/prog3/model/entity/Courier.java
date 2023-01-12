package unipa.prog3.model.entity;

public class Courier extends Entity {
    private final String name, surname;
    private Veicolo vehicle;

    public Courier(String id, String name, String surname, Veicolo vehicle) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.vehicle = vehicle;
    }

    public Courier(String name, String surname) {
        this(null, name, surname, null);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Veicolo getVehicle() {
        return vehicle;
    }

    public void setVehicle(Veicolo vehicle) {
        this.vehicle = vehicle;
    }
}
