package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Courier;
import unipa.prog3.model.relation.Delivery;
import unipa.prog3.model.io.TableProvider;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Vector;

/**
 * Specializzazione di un Service per la gestione delle consegne dei colli
 */
public class DeliveryService extends GenericService<Delivery> {
    public DeliveryService() {
        super(TableProvider.TableName.DELIVERIES);
    }

    /**
     * Seleziona tutte le consegne che riguardano un determinato collo
     * @param pack Istanza del collo da utilizzare per la selezione
     * @return Istanza di un Vector che contiene le consegne selezionate
     */
    public Vector<Delivery> selectByPackage(Collo pack) {
        return select(delivery -> delivery.getCollo().equalKeys(pack));
    }

    /**
     * Seleziona l'ultima consegna effettuata, che riguarda un determinato collo
     * @param pack Istanza del collo da utilizzare per la selezione
     * @return Istanza di Delivery relativa alla consegna selezionata
     */
    public Delivery selectLastByPackage(Collo pack) {
        Vector<Delivery> deliveries = selectByPackage(pack);
        if (deliveries.size() == 0)
            return null;

        // Seleziona l'ultima consegna utilizzando le date di segnalazione
        Delivery last = deliveries.get(0);
        for (Delivery delivery : deliveries)
            if (delivery.getTimestamp().isAfter(last.getTimestamp()))
                last = delivery;
        return last;
    }

    @Override
    public Delivery relationFromFields(String[] fields) {
        // Caricamento del collo corrispondente alla chiave contenuta nel relativo campo
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        Collo pack = packageService.select(fields[0]);

        // Caricamento del centro corrispondente alla chiave contenuta nei relativi campi
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro center = centerService.selectByLocation(fields[1], fields[2]);

        // Caricamento del corriere corrispondente alla chiave contenuta nel relativo campo
        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        Courier courier = courierService.select(fields[3]);

        LocalDateTime timestamp = LocalDateTime.parse(fields[4]);
        return new Delivery(pack, center, courier, timestamp);
    }
}
