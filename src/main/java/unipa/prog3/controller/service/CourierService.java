package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;
import unipa.prog3.model.entity.Courier;

public class CourierService extends GenericService<Courier> {
    public CourierService() {
        super(DataManager.Table.COURIERS);
    }

    public boolean signup(Courier courier) {
        if (findCourierByEmail(courier.getEmail()) == null) {
            insert(courier);
            return true;
        }

        return false;
    }

    public boolean login(String email, String password) {
        Courier courier = findCourierByEmail(email);
        return courier != null && courier.getPassword().equals(password);
    }

    private Courier findCourierByEmail(String email) {
        String[] data = dataManager.readData(DataManager.Table.COURIERS);
        if (data != null)
            for (String s : data) {
                Courier c = entityFromString(s);
                if (c.getEmail().equals(email))
                    return c;
            }

        return null;
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