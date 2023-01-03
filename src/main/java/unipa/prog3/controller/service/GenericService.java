package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Entity;
import unipa.prog3.model.io.Table;

import java.util.Vector;
import java.util.function.Predicate;

public abstract class GenericService<T extends Entity> implements Service<T> {
    protected final Table table;

    protected GenericService(Table table) {
        super();
        this.table = table;
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
        DataManager.getInstance().insertData(table, entityToString(t));
    }

    @Override
    public Vector<T> select(Predicate<T> condition) {
        Vector<String> records = DataManager.getInstance().findData(table, (condition != null) ?
                data -> condition.test(entityFromString(data)) : null);
        Vector<T> entities = new Vector<>();
        for (String s : records)
            entities.add(entityFromString(s));
        return entities;
    }

    public Vector<T> selectAll() {
        Vector<String> data = DataManager.getInstance().findData(table, null);
        Vector<T> entities = new Vector<>();
        for (String s : data)
            entities.add(entityFromString(s));
        return entities;
    }

    @Override
    public void update(T t) {
        DataManager.getInstance().updateData(table, entityToString(t));
    }

    public T select(String id) {
        Vector<T> entities = select(t -> t.getID().equals(id));
        if (!entities.isEmpty())
            return entities.get(0);
        return null;
    }
}
