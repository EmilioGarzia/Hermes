package unipa.prog3.controller.service;

import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableAdapter;
import unipa.prog3.model.io.TableProvider;
import unipa.prog3.model.relation.Relation;

import java.util.Vector;
import java.util.function.Predicate;

/**
 * La classe è un'astrazione di un Service, il cui scopo è quello di fornire
 * tutti i metodi necessari per inserire, leggere e modificare i dati conservati sui file
 * @param <T> Qualsiasi sottoclasse di Relation che rappresenti una tabella conservata in un file
 */
public abstract class GenericService<T extends Relation> implements Service<T> {
    protected final TableAdapter table;

    protected GenericService(TableProvider.TableName tableName) {
        // Inizializza un oggetto di tipo TableAdapter relativo all'oggetto di tipo Table
        // che contiene i record corrispondenti alle istanze di Relation associate a questo Service
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

    /**
     * Utilizzando il metodo select(Predicate<T>), seleziona tutti i record della tabella.
     * @return Un'istanza di Vector contenente gli oggetti relativi a tutti i record della tabella.
     */
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

    /**
     * Utilizzando il metodo select(Predicate<T>), seleziona l'oggetto corrispondente
     * alla chiave primaria data. La chiave primaria potrebbe essere composta da più ID.
     * @param ids ID che compongono la chiave primaria dell'oggetto da selezionare.
     * @return Restituisce un'istanza dell'oggetto letto dalla tabella.
     */
    public T select(Object... ids) {
        Vector<T> entities = select(t -> t.equalKeys(ids));
        if (!entities.isEmpty())
            return entities.get(0);
        return null;
    }

    /**
     * Converte un'instanza di Relation in una stringa di valori,
     * che rappresentano l'istanza data, separati da un delimitatore,
     * da utilizzare per conservare i dati nella tabella.
     * @param t Istanza di un oggetto di tipo Relation
     * @return La stringa realizzata
     */
    private String relationToString(T t) {
        return String.join(Table.delimiter, t.fieldsToStrings());
    }

    /**
     * Consente di convertire una stringa letta dalla tabella
     * in un'istanza di Relation, chiamando il metodo astratto relationFromFields
     * @param s Stringa corrispondente ad un record della tabella
     * @return Istanza di un oggetto di tipo Relation
     */
    private T relationFromString(String s) {
        return relationFromFields(s.split(Table.delimiter));
    }

    /**
     * Crea un'istanza di Relation e la riempie con i valori contenuti nell'array fields
     * @param fields Array di stringhe corrispondenti ai campi della tabella
     * @return L'istanza di Relation creata
     */
    protected abstract T relationFromFields(String[] fields);
}
