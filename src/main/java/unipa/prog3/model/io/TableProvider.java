package unipa.prog3.model.io;

import java.util.HashMap;

/**
 * Implementazione del design pattern Flyweight per fornire le istanze di Table
 */
public class TableProvider {
    private static final HashMap<TableName, Table> tablePool;

    static {
        tablePool = new HashMap<>();
    }

    /**
     * Restituisce l'oggetto Table associato alla tabella definita dal valore dell'enumeratore
     * @param tableName Nome della tabella che si vuole utilizzare
     * @return Istanza di Table associata alla tabella richiesta
     */
    public static Table getTable(TableName tableName) {
        if (tablePool.containsKey(tableName))
            return tablePool.get(tableName);

        Table table = new Table(tableName.toString().toLowerCase() + ".csv");
        tablePool.put(tableName, table);
        return table;
    }

    /**
     * Enumeratore che fornisce un elenco di tutte le tabelle utilizzabili
     */
    public enum TableName {
        CLIENTS, COURIERS, VEHICLES, PACKAGES, CENTERS, DELIVERIES, ROUTES
    }
}
