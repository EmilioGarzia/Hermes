package unipa.prog3.controller.service;

import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Courier;

import java.util.Vector;

public class CourierService extends GenericService<Courier> {
    public CourierService() {
        super(TableProvider.TableName.COURIERS);
    }

    public boolean signup(Courier courier) {
        if (findCourierByEmail(courier.getEmail()).isEmpty()) {
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
        return select(courier -> courier.getEmail().equals(email));
    }

    @Override
    public String entityToString(Courier courier) {
        return courier.getNome() + Table.delimiter + courier.getCognome()
                + Table.delimiter + courier.getEmail()
                + Table.delimiter + courier.getPassword();
    }

    @Override
    public Courier entityFromString(String s) {
        String[] info = s.split(Table.delimiter);
        return new Courier(info[0], info[1], info[2], info[3]);
    }
}