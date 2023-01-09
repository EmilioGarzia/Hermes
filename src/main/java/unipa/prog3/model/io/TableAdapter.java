package unipa.prog3.model.io;

import java.util.Vector;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class TableAdapter {
    private final Table table;

    public TableAdapter(Table table) {
        this.table = table;
    }

    public void insert(String data) {
        table.addRecord(data);
    }

    public Vector<String> select(Predicate<String> condition) {
        return new Vector<>(table.selectRecords(condition).values());
    }

    public void update(Predicate<String> condition, Supplier<String> newData) {
        table.updateRecords(condition, newData);
    }
}
