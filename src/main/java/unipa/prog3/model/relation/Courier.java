package unipa.prog3.model.relation;

public class Courier extends Relation {
    public Courier(String id, String name, String surname, Veicolo vehicle) {
        super(1, 3);
        addKey(id);
        addData(name);
        addData(surname);
        addData(vehicle);
    }

    public Courier(String name, String surname) {
        this(null, name, surname, null);
    }

    public String getID() {
        return (String) keys[0];
    }

    public String getName() {
        return (String) data[0];
    }

    public String getSurname() {
        return (String) data[1];
    }

    public Veicolo getVehicle() {
        return (Veicolo) data[2];
    }

    public void setVehicle(Veicolo vehicle) {
        data[2] = vehicle;
    }
}
