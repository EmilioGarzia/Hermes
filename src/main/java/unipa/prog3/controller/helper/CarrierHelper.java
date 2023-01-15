package unipa.prog3.controller.helper;

import unipa.prog3.controller.service.util.BFSNode;
import unipa.prog3.controller.service.util.Graph;
import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Route;

import java.awt.*;
import java.util.Vector;

/**
 * Classe che gestisce i criteri per ottenere il miglior impiego possibile delle risorse dell'azienda,
 * tenendo conto dei mezzi a disposizione che percorrono gli stessi itinerari e dei colli da spedire
 * */
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

    /**
     * Aggiunge un centro al grafo BFS, se non già presente e restituisce il nodo ad esso associato
     * @param center centro di smistamento che si vuole istanziare come nodo del grafo BFS
     * @return ritorna un istanza nodo BFS di tipo "Centro"
     * */
    private BFSNode<Centro> add(Centro center) {
        String centerString = joinStrings(center.keysToString());
        if (map.containsKey(centerString))
            return (BFSNode<Centro>) map.get(centerString);

        BFSNode<Centro> node = new BFSNode<>(center);
        map.put(centerString, node);
        return node;
    }

    /**
     * Sceglie la miglior soluzione circa il carico dei colli su di un veicolo
     * @param packsToSend Insieme dei pacchi da spedire
     * @return ritorna un istanza di Vector<Collo> contenente il collo con tutti i pacchi da spedire
     * */
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

    /**
     * Ricerca il percorso migliore e i vari centri di smistamento che visiterà il carico prima di arrivare a destinazione
     * @param partenza centro di partenza
     * @param destinazione centro di destinazione
     * @return ritorna un'istanza di Vector<Centro> che contiene i centri che fungeranno da checkpoint perima dell'arrivo a destinazione
     * */
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

    /**
     * Sposta il carico specificato in input al centro di smistamento successivo
     * @param current centro in cui il collo si trova in questo momento
     * @param path percorso totale che il collo deve affrontare
     * */
    public Centro nextStep(Vector<Centro> path, Centro current) {
        String lastString = joinStrings(path.get(0).keysToString());
        BFSNode<Centro> node = (BFSNode<Centro>) map.get(lastString);
        String currentString = joinStrings(current.keysToString());
        BFSNode<Centro> currentNode = (BFSNode<Centro>) map.get(currentString);
        while(node != null) {
            if (node.getParent() == currentNode)
                return node.getData();
            node = node.getParent();
        }

        return null;
    }

    private String joinStrings(Vector<String> strings) {
        return String.join(":", strings);
    }
}
