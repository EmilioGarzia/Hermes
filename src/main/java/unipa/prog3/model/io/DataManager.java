package unipa.prog3.model.io;

import java.util.Vector;
import java.util.function.Predicate;

public class DataManager {
    public static final String delimiter = ";";
    public static final Table COURIERS = new Table("couriers.csv");
    public static final Table CLIENTS = new Table("clients.csv");
    public static final Table PACKAGES = new Table("packages.csv");
    public static final Table VEHICLES = new Table("vehicles.csv");

    private static DataManager instance;

    private DataManager() {}

    public boolean insertData(Table table, String data) {
        return table.addRecord(data);
    }

    public Vector<String> findData(Table table, Predicate<String> condition) {
        return table.selectRecords(condition);
    }

    public boolean updateData(Table table, String data) {
        return table.updateRecord(data);
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }
}
