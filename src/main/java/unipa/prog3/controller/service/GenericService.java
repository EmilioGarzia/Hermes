package unipa.prog3.controller.service;

import unipa.prog3.model.io.DataManager;
import unipa.prog3.model.entity.Entity;
import unipa.prog3.model.io.Table;

import java.util.Vector;
import java.util.function.Predicate;

public abstract class GenericService<T extends Entity> {
    protected final Table table;

    protected GenericService(Table table) {
        super();
        this.table = table;
    }

    protected String generateID() {
        String language = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final StringBuilder builder = new StringBuilder();
        do {
            for (int i = 0; i < 15; i++) {
                int rand = (int) (Math.random() * language.length());
                builder.append(language.charAt(rand));
            }
        } while(!find(t -> t.getID().equals(builder.toString())).isEmpty());
        return builder.toString();
    }

    public void insert(T t) {
        if (t.getID() == null)
            t.setID(generateID());
        DataManager.getInstance().insertData(table, entityToString(t));
    }

    public void update(T t) {
        DataManager.getInstance().updateData(table, entityToString(t));
    }

    public Vector<T> readAll() {
        Vector<String> data = DataManager.getInstance().findData(table, null);
        Vector<T> entities = new Vector<>();
        for (String s : data)
            entities.add(entityFromString(s));
        return entities;
    }

    public Vector<T> find(Predicate<T> condition) {
        Vector<String> records = DataManager.getInstance().findData(table, (condition != null) ?
                data -> condition.test(entityFromString(data)) : null);
        Vector<T> entities = new Vector<>();
        for (String s : records)
            entities.add(entityFromString(s));
        return entities;
    }

    public T find(String id) {
        Vector<T> entities = find(t -> t.getID().equals(id));
        if (!entities.isEmpty())
            return entities.get(0);
        return null;
    }

    public abstract T entityFromString(String s);
    public abstract String entityToString(T t);
}
