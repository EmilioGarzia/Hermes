package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Courier;

import java.util.Vector;

public class CourierService extends GenericService<Courier> {
    public CourierService() {
        super(DataManager.COURIERS);
    }

    public boolean signup(Courier courier) {
        if (findCourierByEmail(courier.getEmail()) == null) {
            insert(courier);
            return true;
        }

        return false;
    }

    public boolean login(String email, String password) {
        Vector<Courier> couriers = findCourierByEmail(email);
        for (Courier c : couriers)
            if (c.getPassword().equals(password))
                return true;
        return false;
    }

    private Vector<Courier> findCourierByEmail(String email) {
        return find(courier -> courier.getEmail().equals(email));
    }

    public String entityToString(Courier courier) {
        return courier.getNome() + DataManager.delimiter + courier.getCognome() + DataManager.delimiter +
                courier.getEmail() + DataManager.delimiter + courier.getPassword();
    }

    public Courier entityFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        return new Courier(info[0], info[1], info[2], info[3]);
    }
}