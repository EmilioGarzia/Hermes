package unipa.prog3.controller.service;

import unipa.prog3.model.entity.Centro;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Courier;
import unipa.prog3.model.entity.Delivery;
import unipa.prog3.model.io.Table;
import unipa.prog3.model.io.TableProvider;

import java.time.LocalDateTime;

public class DeliveryService extends GenericService<Delivery> {
    public DeliveryService() {
        super(TableProvider.TableName.DELIVERIES);
    }

    public boolean exists(Delivery delivery) {
        return !select(delivery::equals).isEmpty();
    }

    @Override
    public Delivery entityFromString(String s) {
        String[] info = s.split(Table.delimiter);

        PackageService packageService = (PackageService) ServiceProvider.getService(Collo.class);
        Collo pack = packageService.select(info[1]);

        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro center = centerService.select(info[2]);

        CourierService courierService = (CourierService) ServiceProvider.getService(Courier.class);
        Courier courier = courierService.select(info[3]);

        LocalDateTime timestamp = LocalDateTime.parse(info[4]);
        return new Delivery(info[0], pack, center, courier, timestamp);
    }

    @Override
    public String entityToString(Delivery delivery) {
        return delivery.getID() + Table.delimiter + delivery.getCollo().getID() + Table.delimiter
                + delivery.getCentro().getID() + Table.delimiter + delivery.getCorriere().getID() + Table.delimiter
                + delivery.getTimestamp().toString();
    }
}
