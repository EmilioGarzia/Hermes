package unipa.prog3.model.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
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

    public static void insertData(String tableName, String data) {
        try {
            getTable(tableName).addRecord(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Vector<String> findData(String tableName, Predicate<String> condition) {
        Vector<String> records = new Vector<>();
        try {
            records.addAll(getTable(tableName).selectRecords(condition).values());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    public static void updateData(String tableName, Supplier<String> newData, Predicate<String> condition) {
        try {
            HashMap<Long, String> records = getTable(tableName).selectRecords(condition);
            for (Map.Entry<Long, String> entry : records.entrySet())
                getTable(tableName).updateRecord(entry.getKey(), newData.get());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
