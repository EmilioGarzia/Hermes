package unipa.prog3.model.io;

import java.util.Vector;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Implementazione del design pattern adapter che consente
 * ai service di comunicare con i metodi della classe Table
 */
public class TableAdapter {
    private final Table table;

    public TableAdapter(Table table) {
        this.table = table;
    }

    /**
     * Richiama il metodo addRecord di Table per inserire un record nella tabella
     * @param data Record da inserire nella tabella
     */
    public void insert(String data) {
        table.addRecord(data);
    }

    /**
     * Richiama il metodo selectRecords di Table per selezionare i record che rispettano un determinato criterio
     * @param condition Criterio da utilizzare per la selezione dei record
     * @return Vector contenente i record selezionati
     */
    public Vector<String> select(Predicate<String> condition) {
        // Restituisce un Vector contenente solo i record contenuti nella HashMap restituita dal metodo selectRecords
        return new Vector<>(table.selectRecords(condition).values());
    }

    /**
     * Richiama il metodo updateRecords di Table per aggiornare i record che rispettano un determinato criterio con nuovi dati
     * @param condition Criterio da utilizzare per la ricerca dei record da aggiornare
     * @param newData Metodo da utilizzare per la richiesta dei nuovi record
     */
    public void update(Predicate<String> condition, Supplier<String> newData) {
        table.updateRecords(condition, newData);
    }
}
