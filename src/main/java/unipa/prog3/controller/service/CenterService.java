package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.io.TableProvider;

public class CenterService extends GenericService<Centro> {
    public CenterService() {
        super(TableProvider.TableName.CENTERS);
    }

    public Centro selectByLocation(String città, String stato) {
        return select(centro -> centro.getCittà().equals(città) && centro.getStato().equals(stato)).get(0);
    }

    @Override
    public Centro relationFromFields(String[] fields) {
        return new Centro(fields[0], fields[1]);
    }
}
