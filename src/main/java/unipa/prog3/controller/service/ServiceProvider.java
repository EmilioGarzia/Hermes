package unipa.prog3.controller.service;

import unipa.prog3.model.entity.*;

import java.util.HashMap;

public abstract class ServiceProvider {
    private static final HashMap<Class<? extends Entity>, Service<? extends Entity>> servicePool;

    static {
        servicePool = new HashMap<>();
    }

    public static Service<? extends Entity> getService(Class<? extends Entity> type) {
        if (servicePool.containsKey(type))
            return servicePool.get(type);

        Service<? extends Entity> service = createService(type);
        servicePool.put(type, service);
        return service;
    }

    private static Service<? extends Entity> createService(Class<? extends Entity> type) {
        if (Cliente.class.equals(type))
            return new ClientService();
        else if (Collo.class.equals(type))
            return new PackageService();
        else if (Courier.class.equals(type))
            return new CourierService();
        else if (Veicolo.class.equals(type))
            return new VehicleService();
        else if (Centro.class.equals(type))
            return new CenterService();
        else if (Delivery.class.equals(type))
            return new DeliveryService();
        else if (Route.class.equals(type))
            return new RouteService();
        return null;
    }
}
