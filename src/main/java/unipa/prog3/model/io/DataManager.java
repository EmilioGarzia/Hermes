package unipa.prog3.model.io;

import java.util.HashMap;
import java.util.Vector;
import java.util.function.Predicate;

public abstract class DataManager {
    public static final String delimiter = ";";
    private static final HashMap<String, Table> tablePool = new HashMap<>();

    public static Table getTable(String name) {
        if (tablePool.containsKey(name))
            return tablePool.get(name);
        Table table = new Table(name.toLowerCase()+".csv");
        tablePool.put(name, table);
        return table;
    }

    public static boolean insertData(String tableName, String data) {
        return getTable(tableName).addRecord(data);
    }

    public static Vector<String> findData(String tableName, Predicate<String> condition) {
        return getTable(tableName).selectRecords(condition);
    }

    public static boolean updateData(String tableName, String data) {
        return getTable(tableName).updateRecord(data);
    }
}
