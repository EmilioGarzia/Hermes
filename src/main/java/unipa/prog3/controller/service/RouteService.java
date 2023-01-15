package unipa.prog3.controller.service;

import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Route;
import unipa.prog3.model.io.TableProvider;

/**
 * Specializzazione di un Service per la gestione delle rotte,
 * ossia i collegamenti tra i vari centri di smistamento
 */
public class RouteService extends GenericService<Route> {
    public RouteService() {
        super(TableProvider.TableName.ROUTES);
    }

    @Override
    public Route relationFromFields(String[] fields) {
        // Carica i centri di partenza e destinazione utilizzando le chiavi conservate nei relativi campi
        CenterService centerService = (CenterService) ServiceProvider.getService(Centro.class);
        Centro partenza = centerService.selectByLocation(fields[0], fields[1]);
        Centro destinazione = centerService.selectByLocation(fields[2], fields[3]);
        return new Route(partenza, destinazione);
    }
}