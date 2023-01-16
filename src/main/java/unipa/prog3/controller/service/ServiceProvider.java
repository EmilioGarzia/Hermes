package unipa.prog3.controller.service;

import unipa.prog3.model.relation.*;

import java.util.HashMap;

/**
 * Implementazione dei design pattern Flyweight e Factory method che forniscano istanze dei vari Service
 */
public abstract class ServiceProvider {
    private static final HashMap<Class<? extends Relation>, Service<? extends Relation>> servicePool;

    static {
        servicePool = new HashMap<>();
    }

    public static Service<? extends Relation> getService(Class<? extends Relation> type) {
        if (servicePool.containsKey(type))
            return servicePool.get(type);

        Service<? extends Relation> service = createService(type);
        servicePool.put(type, service);
        return service;
    }

    private static Service<? extends Relation> createService(Class<? extends Relation> type) {
        if (Cliente.class.equals(type))
            return new ClientService();
        else if (Collo.class.equals(type))
            return new PackageService();
        else if (Corriere.class.equals(type))
            return new CourierService();
        else if (Veicolo.class.equals(type))
            return new VehicleService();
        else if (Centro.class.equals(type))
            return new CenterService();
        else if (Consegna.class.equals(type))
            return new DeliveryService();
        else if (Rotta.class.equals(type))
            return new RouteService();
        return null;
    }
}
