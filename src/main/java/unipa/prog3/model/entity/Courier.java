package unipa.prog3.model.entity;

public class Courier extends Entity {
    private final String name, surname, email, password;
    private Veicolo vehicle;

    public Courier(String id, String name, String surname, String email, String password, Veicolo vehicle) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.vehicle = vehicle;
    }

    public Courier(String name, String surname, String email, String password) {
        this(null, name, surname, email, password, null);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Veicolo getVehicle() {
        return vehicle;
    }

    public void setVehicle(Veicolo vehicle) {
        this.vehicle = vehicle;
    }
}
