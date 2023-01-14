package unipa.prog3.controller.helper;

import unipa.prog3.controller.service.util.BFSNode;
import unipa.prog3.controller.service.util.Graph;
import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Route;

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

    private BFSNode<Centro> add(Centro center) {
        String centerString = joinStrings(center.keysToString());
        if (map.containsKey(centerString))
            return (BFSNode<Centro>) map.get(centerString);

        BFSNode<Centro> node = new BFSNode<>(center);
        map.put(centerString, node);
        return node;
    }

    public Vector<Collo> findBestLoad(Vector<Collo> packsToSend) {
        Vector<Collo> load = new Vector<>();

        if (packsToSend.size() > 0) {
            Collo first = packsToSend.get(0);
            load.add(first);
            Vector<Centro> finalPath = new Vector<>(findPath(first.getPartenza(), first.getDestinazione()));

            for (int i = 1; i < packsToSend.size(); i++) {
                Collo collo = packsToSend.get(i);
                Vector<Centro> path = findPath(collo.getPartenza(), collo.getDestinazione());
                for (Centro centro : path)
                    if (finalPath.contains(centro)) {
                        load.add(collo);
                        finalPath.addAll(path);
                        break;
                    }
            }
        }

        return load;
    }

    public Vector<Centro> findPath(Centro partenza, Centro destinazione) {
        String partenzaString = joinStrings(partenza.keysToString());
        String destinazioneString = joinStrings(destinazione.keysToString());
        map.breadthFirstSearch((BFSNode<Centro>) map.get(partenzaString));

        Vector<Centro> path = new Vector<>();
        BFSNode<Centro> node = (BFSNode<Centro>) map.get(destinazioneString);
        while(node != null) {
            path.add(node.getData());
            node = node.getParent();
        }

        return path;
    }

    private String joinStrings(Vector<String> strings) {
        return String.join(":", strings);
    }
}
