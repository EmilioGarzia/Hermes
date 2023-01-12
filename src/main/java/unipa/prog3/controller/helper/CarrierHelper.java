package unipa.prog3.controller.helper;

import unipa.prog3.controller.service.util.BFSNode;
import unipa.prog3.controller.service.util.Graph;
import unipa.prog3.model.entity.Centro;
import unipa.prog3.model.entity.Collo;
import unipa.prog3.model.entity.Route;

import java.util.Vector;

public class CarrierHelper {
    private final Graph<Centro> map;

    public CarrierHelper(Vector<Route> routes) {
        map = new Graph<>();
        for (Route route : routes) {
            BFSNode<Centro> n1 = add(route.getCentro1());
            BFSNode<Centro> n2 = add(route.getCentro2());
            n1.connectTo(n2);
        }
    }

    private BFSNode<Centro> add(Centro centro) {
        if (map.containsKey(centro))
            return (BFSNode<Centro>) map.get(centro);

        BFSNode<Centro> node = new BFSNode<>(centro);
        map.put(centro, node);
        return node;
    }

    public Vector<Collo> findBestLoad(Vector<Collo> packsToSend) {
        Collo first = packsToSend.get(0);
        Vector<Collo> load = new Vector<>();
        load.add(first);
        Vector<Centro> finalPath = new Vector<>(findPath(first.getPartenza(), first.getDestinazione()));

        for (int i = 1; i < packsToSend.size(); i++) {
            Collo collo = packsToSend.get(i);
            Vector<Centro> path = findPath(collo.getMittente().getCentro(), collo.getDestinatario().getCentro());
            for (Centro centro : path)
                if (finalPath.contains(centro)) {
                    load.add(collo);
                    finalPath.addAll(path);
                    break;
                }
        }

        return load;
    }

    public Vector<Centro> findPath(Centro partenza, Centro destinazione) {
        map.breadthFirstSearch((BFSNode<Centro>) map.get(partenza));

        Vector<Centro> path = new Vector<>();
        BFSNode<Centro> node = (BFSNode<Centro>) map.get(destinazione);
        while(node != null) {
            path.add(node.getData());
            node = node.getParent();
        }

        return path;
    }
}
