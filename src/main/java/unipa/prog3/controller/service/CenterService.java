package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Centro;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;

public class CenterService extends GenericService<Centro> {
    public CenterService() {
        super(TableProvider.TableName.CENTERS);
    }

    public Centro selectByLocation(String città, String stato) {
        return select(centro -> centro.getCittà().equals(città) && centro.getStato().equals(stato)).get(0);
    }

    @Override
    public Centro entityFromString(String s) {
        String[] info = s.split(Table.delimiter);
        return new Centro(info[0], info[1], info[2]);
    }

    @Override
    public String entityToString(Centro centro) {
        return centro.getID() + Table.delimiter + centro.getCittà() + Table.delimiter + centro.getStato();
    }
}
