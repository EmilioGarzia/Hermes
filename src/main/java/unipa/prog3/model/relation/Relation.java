package unipa.prog3.model.relation;

import java.util.Vector;

/**
 * Classe che gestisce i dati del progetto mediante l'implementazione di due Array,
 * questi due array contengono rispettivamente le chiavi e le informazioni associate alle specifiche chiavi,
 * questa classe è necessaria per rendere astratto il concetto di entità e associazioni di un ipotetico
 * diagramma E/R, per l'implementazione di tabelle (o relazioni) per la conservazione dei dati.
 * */
public abstract class Relation {
    protected Object[] keys, data;
    private int lastKey, lastData;

    public Relation(int totalKeys, int totalData) {
        keys = new Object[totalKeys];
        data = new Object[totalData];
    }

    /**
     * Aggiunge una chiave all'interno dell'array che contiene le chiavi
     * @param key chiave da aggiungere all'array key
     * */
    public void addKey(Object key) {
        keys[lastKey++] = key;
    }

    /**
     * Aggiunge un informazione al vettore data
     * @param data informazione da aggiungere al vettore data
     * */
    public void addData(Object data) {
        this.data[lastData++] = data;
    }

    /**
     * Converte tutte le chiavi in un array di stringhe
     * @return Ritorna un istanza Vector contenente tutte le chiavi della tabella come stringhe
     * */
    public final Vector<String> keysToString() {
        return fieldsToStrings(keys);
    }

    /**
     * Converte tutti i campi della tabella in un array di stringhe
     * @return Ritorna un istanza Vector contenente tutti i campi della tabella come stringhe
     * */
    public final Vector<String> fieldsToStrings() {
        Vector<String> keys = fieldsToStrings(this.keys);
        Vector<String> data = fieldsToStrings(this.data);
        keys.addAll(data);
        return keys;
    }

    /**
     * Converte uno specifico campo della tabella in un array di stringhe
     * @param fields array dei campi da convertire in stringhe
     * @return Ritorna un'istanza di Vector<String> contenente il campo specificato come stringa
     * */
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

    /**
     * Controlla che tutte le chiavi di due tabelle (Relation) siano uguali
     * @param relation oggetto relation con cui effettuare il confronto
     * @return true se sono uguali, false, altrimenti
     * */
    public final boolean equalKeys(Relation relation) {
        return equalKeys(relation.keys);
    }

    /**
     * Controlla che tutte le chiavi corrispondano a quelle passate come argomenti in input
     * @param keys insieme delle chiavi da confrontare con quelle già contenute nel vettore keys della classe
     * @return true se sono uguali, false, altrimenti
     * */
    public final boolean equalKeys(Object... keys) {
        if (this.keys.length != keys.length)
            return false;

        for (int i = 0; i < this.keys.length; i++)
            if (!this.keys[i].equals(keys[i]))
                return false;
        return true;
    }

    /**
     * Controlla che una chiave non sia composta da valori nulli
     * @return true se nulla, false, altrimenti
     * */
    public boolean keysAreNull() {
        for (Object key : keys)
            if (key == null)
                return true;
        return false;
    }

    /**
     * Imposta il valore delle chiavi con quelli passati in input
     * @param keys insieme delle chiavi da assegnare come valori del vettore keys della classe
     * */
    public void setKeys(Object... keys) {
        this.keys = keys;
    }
}
