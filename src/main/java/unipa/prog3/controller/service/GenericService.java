package unipa.prog3.controller.service;

import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableAdapter;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Relation;

import java.util.Vector;
import java.util.function.Predicate;

public abstract class GenericService<T extends Relation> implements Service<T> {
    protected final TableAdapter table;

    protected GenericService(TableProvider.TableName tableName) {
        table = new TableAdapter(TableProvider.getTable(tableName));
    }

    @Override
    public String generateID() {
        String language = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final StringBuilder builder = new StringBuilder();
        do {
            for (int i = 0; i < 15; i++) {
                int rand = (int) (Math.random() * language.length());
                builder.append(language.charAt(rand));
            }
        } while(select(builder.toString()) != null);
        return builder.toString();
    }

    @Override
    public void insert(T t) {
        if (t.keysAreNull())
            t.setKeys(generateID());
        table.insert(relationToString(t));
    }

    @Override
    public Vector<T> select(Predicate<T> condition) {
        Vector<String> records = table.select((condition != null) ? data -> condition.test(relationFromString(data)) : null);
        Vector<T> entities = new Vector<>();
        for (String s : records)
            entities.add(relationFromString(s));
        return entities;
    }

    public Vector<T> selectAll() {
        Vector<String> data = table.select(null);
        Vector<T> entities = new Vector<>();
        for (String s : data)
            entities.add(relationFromString(s));
        return entities;
    }

    @Override
    public void update(T t) {
        table.update(data -> relationFromString(data).equalKeys(t), () -> relationToString(t));
    }

    public T select(Object... ids) {
        Vector<T> entities = select(t -> t.equalKeys(ids));
        if (!entities.isEmpty())
            return entities.get(0);
        return null;
    }

    private String relationToString(T t) {
        return String.join(Table.delimiter, t.fieldsToStrings());
    }

    private T relationFromString(String s) {
        return relationFromFields(s.split(Table.delimiter));
    }

    protected abstract T relationFromFields(String[] fields);
}
