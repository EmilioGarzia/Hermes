package unipa.prog3.model.io;

import java.util.HashMap;

public class TableProvider {
    private static final HashMap<TableName, Table> tablePool;

    static {
        tablePool = new HashMap<>();
    }

    public static Table getTable(TableName tableName) {
        if (tablePool.containsKey(tableName))
            return tablePool.get(tableName);

        Table table = new Table(tableName.toString().toLowerCase() + ".csv");
        tablePool.put(tableName, table);
        return table;
    }

    public enum TableName {
        CLIENTS, COURIERS, VEHICLES, PACKAGES
    }
}
