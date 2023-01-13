package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Courier;
import unipa.prog3.model.relation.Delivery;
import unipa.prog3.model.io.TableProvider;

import java.time.LocalDateTime;
import java.util.Vector;

public class DeliveryService extends GenericService<Delivery> {
    public DeliveryService() {
        super(TableProvider.TableName.DELIVERIES);
    }

    public boolean exists(Delivery delivery) {
        return !select(delivery::equals).isEmpty();
    }

    public Vector<Delivery> selectByPackage(Collo pack) {
        return select(delivery -> delivery.getCollo().equals(pack));
    }

    @Override
    public Delivery relationFromFields(String[] fields) {
        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        Collo pack = packageService.select(fields[0]);

        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro center = centerService.select(fields[1]);

        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        Courier courier = courierService.select(fields[2]);

        LocalDateTime timestamp = LocalDateTime.parse(fields[3]);
        return new Delivery(pack, center, courier, timestamp);
    }
}
