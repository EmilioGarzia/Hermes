package unipa.prog3.controller.service;

import unipa.prog3.model.io.TableAdapter;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.entity.Entity;

import java.util.Vector;
import java.util.function.Predicate;

public abstract class GenericService<T extends Entity> implements Service<T> {
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
        } while(!select(t -> t.getID().equals(builder.toString())).isEmpty());
        return builder.toString();
    }

    @Override
    public void insert(T t) {
        if (t.getID() == null)
            t.setID(generateID());
        table.insert(entityToString(t));
    }

    @Override
    public Vector<T> select(Predicate<T> condition) {
        Vector<String> records = table.select((condition != null) ? data -> condition.test(entityFromString(data)) : null);
        Vector<T> entities = new Vector<>();
        for (String s : records)
            entities.add(entityFromString(s));
        return entities;
    }

    public Vector<T> selectAll() {
        Vector<String> data = table.select(null);
        Vector<T> entities = new Vector<>();
        for (String s : data)
            entities.add(entityFromString(s));
        return entities;
    }

    @Override
    public void update(T t) {
        table.update(data -> entityFromString(data).getID().equals(t.getID()), () -> entityToString(t));
    }

    public T select(String id) {
        Vector<T> entities = select(t -> t.getID().equals(id));
        if (!entities.isEmpty())
            return entities.get(0);
        return null;
    }
}
