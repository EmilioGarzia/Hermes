package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Route;
import unipa.prog3.model.io.TableProvider;

public class RouteService extends GenericService<Route> {
    public RouteService() {
        super(TableProvider.TableName.ROUTES);
    }

    @Override
    public Route relationFromFields(String[] fields) {
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro partenza = centerService.select(fields[0]);
        Centro destinazione = centerService.select(fields[1]);
        return new Route(partenza, destinazione);
    }
}