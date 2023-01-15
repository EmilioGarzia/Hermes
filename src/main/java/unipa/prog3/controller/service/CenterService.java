package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.io.TableProvider;

import java.util.Vector;

/**
 * Specializzazione di un Service per la gestione dei centri di smistamento
 * rappresentati dalla classe Centro che estende Relation
 */
public class CenterService extends GenericService<Centro> {
    public CenterService() {
        super(TableProvider.TableName.CENTERS);
    }

    /**
     * Seleziona il centro identificato dalla città e lo stato di appartenenza
     * @param città Città di appartenenza del centro
     * @param stato Stato di appartenenza della città e, quindi, del centro
     * @return L'istanza della classe Centro selezionata dalla tabella
     */
    public Centro selectByLocation(String città, String stato) {
        Vector<Centro> centers = select(centro -> centro.getCittà().equals(città) && centro.getStato().equals(stato));
        if (centers.size() > 0)
            return centers.get(0);
        return null;
    }

    @Override
    public Centro relationFromFields(String[] fields) {
        return new Centro(fields[0], fields[1]);
    }
}
