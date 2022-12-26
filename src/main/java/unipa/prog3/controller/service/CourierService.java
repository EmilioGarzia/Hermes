package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;
import unipa.prog3.model.entity.Courier;

public class CourierService extends GenericService {
    public boolean signup(Courier courier) {
        if (findCourierByEmail(courier.getEmail()) == null)
            return dataManager.insertData(DataManager.Table.USERS, courierToString(courier));
        return false;
    }

    public boolean login(String email, String password) {
        Courier courier = findCourierByEmail(email);
        return courier != null && courier.getPassword().equals(password);
    }

    private Courier findCourierByEmail(String email) {
        String[] data = dataManager.readData(DataManager.Table.USERS);
        if (data != null)
            for (String s : data) {
                Courier c = courierFromString(s);
                if (c.getEmail().equals(email))
                    return c;
            }

        return null;
    }

    private String courierToString(Courier courier) {
        return courier.getNome() + DataManager.delimiter + courier.getCognome() + DataManager.delimiter +
                courier.getEmail() + DataManager.delimiter + courier.getPassword();
    }

    private Courier courierFromString(String s) {
        String[] info = s.split(DataManager.delimiter);
        return new Courier(info[0], info[1], info[2], info[3]);
    }
}