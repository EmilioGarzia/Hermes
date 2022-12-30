package unipa.prog3.controller.service;

import unipa.prog3.model.DataManager;
import unipa.prog3.model.entity.Entity;

public abstract class GenericService<T extends Entity> {
    protected final DataManager.Table table;

    protected GenericService(DataManager.Table table) {
        this.table = table;
    }

    protected String generateID() {
        String language = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder builder;
        do {
            builder = new StringBuilder();
            for (int i = 0; i < 15; i++) {
                int rand = (int) (Math.random() * language.length());
                builder.append(language.charAt(rand));
            }
        } while(find(builder.toString()) != null);

        return builder.toString();
    }

    protected void insert(T t) {
        t.setID(generateID());
        DataManager.getInstance().insertData(table, entityToString(t));
    }

    protected T find(String id) {
        String[] records = DataManager.getInstance().readData(table);
        if (records != null)
            for (String s : records) {
                T t = entityFromString(s);
                if (t.getID().equals(id))
                    return t;
            }

        return null;
    }

    public abstract T entityFromString(String s);
    public abstract String entityToString(T t);
}
