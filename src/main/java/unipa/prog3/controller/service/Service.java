package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Relation;

import java.util.Vector;
import java.util.function.Predicate;

/**
 * Interfaccia per i service
 * @param <T> Qualsiasi sottoclasse di Relation che rappresenti una tabella conservata in un file
 */
public interface Service<T extends Relation> {
    /**
     * Genera un codice alfanumerico univoco per identificare le istanze conservate nella tabella
     * @return Il codice generato
     */
    String generateID();

    /**
     * Effettua l'inserimento dell'istanza nella tabella, quindi su file.
     * Inoltre imposta un ID all'istanza, se ne è priva, generandone uno nuovo.
     * @param t Istanza da inserire nella tabella
     */
    void insert(T t);

    /**
     * Seleziona solo alcuni o tutti i record della tabella, in base al criterio utilizzato
     * @param condition Istanza dell'interfaccia funzionale Predicate, che definisca il criterio
     *                  da utilizzare per selezionare i record. Se null verranno selezionati tutti i record.
     * @return Istanza di Vector che contenga gli oggetti relativi ai record caricati dalla tabella.
     */
    Vector<T> select(Predicate<T> condition);

    /**
     * Effettua l'aggiornamento di un record già esistente in tabella.
     * @param t Istanza aggiornata dell'oggetto associato al record da modificare.
     */
    void update(T t);
}
