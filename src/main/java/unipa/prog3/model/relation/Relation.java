package unipa.prog3.model.relation;

import java.util.Vector;

public abstract class Relation {
    protected Object[] keys, data;
    private int lastKey, lastData;

    public Relation(int totalKeys, int totalData) {
        keys = new Object[totalKeys];
        data = new Object[totalData];
    }

    public void addKey(Object key) {
        keys[lastKey++] = key;
    }

    public void addData(Object data) {
        this.data[lastData++] = data;
    }

    public final Vector<String> keysToString() {
        return fieldsToStrings(keys);
    }

    public final Vector<String> fieldsToStrings() {
        Vector<String> keys = fieldsToStrings(this.keys);
        Vector<String> data = fieldsToStrings(this.data);
        keys.addAll(data);
        return keys;
    }

    private Vector<String> fieldsToStrings(Object[] fields) {
        Vector<String> fieldStrings = new Vector<>();
        for (Object field : fields)
            if (field instanceof Relation relation)
                fieldStrings.addAll(relation.keysToString());
            else if (field == null)
                fieldStrings.add("null");
            else fieldStrings.add(field.toString());

        return fieldStrings;
    }

    public final boolean equalKeys(Relation relation) {
        return equalKeys(relation.keys);
    }

    public final boolean equalKeys(Object... keys) {
        if (this.keys.length != keys.length)
            return false;

        for (int i = 0; i < this.keys.length; i++)
            if (!this.keys[i].equals(keys[i]))
                return false;
        return true;
    }

    public boolean keysAreNull() {
        for (Object key : keys)
            if (key == null)
                return true;
        return false;
    }

    public void setKeys(Object... keys) {
        this.keys = keys;
    }
}
