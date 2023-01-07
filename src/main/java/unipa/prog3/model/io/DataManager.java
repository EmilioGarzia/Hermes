package unipa.prog3.model.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        return new Vector<>(getTable(tableName).selectRecords(condition).values());
    }

    public static boolean updateData(String tableName, Supplier<String> newData, Predicate<String> condition) {
        HashMap<Long, String> records = getTable(tableName).selectRecords(condition);
        for (Map.Entry<Long, String> entry : records.entrySet())
            getTable(tableName).updateRecord(entry.getKey(), newData.get());
        return true;
    }
}
