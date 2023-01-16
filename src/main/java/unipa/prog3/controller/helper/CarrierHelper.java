package unipa.prog3.controller.helper;

import unipa.prog3.controller.service.util.BFSNode;
import unipa.prog3.controller.service.util.Graph;
import unipa.prog3.model.relation.Centro;
import unipa.prog3.model.relation.Collo;
import unipa.prog3.model.relation.Rotta;

import java.util.Vector;

/**
 * Classe che gestisce i criteri per ottenere il miglior impiego possibile delle risorse dell'azienda,
 * tenendo conto dei colli che percorrono gli stessi itinerari
 * */
public class CarrierHelper {
    private final Graph<Centro> map;

    public CarrierHelper(Vector<Rotta> rottas) {
        map = new Graph<>();
        for (Rotta rotta : rottas) {
            BFSNode<Centro> n1 = add(rotta.getCentro1());
            BFSNode<Centro> n2 = add(rotta.getCentro2());
            n1.connectTo(n2);
        }
    }

    /**
     * Aggiunge un centro al grafo, se non già presente e restituisce il nodo ad esso associato
     * @param center centro di smistamento che si vuole istanziare come nodo del grafo
     * @return Istanza nodo BFS di tipo "Centro"
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
     * @return Istanza di Vector<Collo> contenente tutti i colli selezionati per il carico
     * */
    public Vector<Collo> findBestLoad(Vector<Collo> packsToSend, Collo selected) {
        Vector<Collo> bestLoad = new Vector<>();
        bestLoad.add(selected);

        // Calcola il percorso che dovrà effettuare il collo selezionato
        Vector<Centro> finalPath = findPath(selected.getPartenza(), selected.getDestinazione());
        if (finalPath.size() > 0) {
            // Aggiunge al carico tutti quei colli che dovranno fare un percorso
            // simile, di almeno il 60% del loro intero percorso, a quello selezionato
            for (Collo collo : packsToSend) {
                if (collo == selected) continue;
                Vector<Centro> path = findPath(collo.getPartenza(), collo.getDestinazione());
                int commonSteps = 0;
                for (Centro centro : path)
                    if (finalPath.contains(centro))
                        commonSteps++;

                // Calcola il rapporto di somiglianza del percorso
                // con quello del collo selezionato inizialmente
                double similarityRatio = (double) commonSteps / path.size();
                if (similarityRatio >= 0.6) {
                    bestLoad.add(collo);
                    finalPath.addAll(path);
                }
            }
        }

        return bestLoad;
    }

    /**
     * Ricerca il percorso più breve da un centro di smistamento ad un altro
     * @param partenza centro di partenza
     * @param destinazione centro di destinazione
     * @return Istanza di Vector<Centro> che contiene i centri che costituiscono il percorso completo
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
     * Trova il centro di smistamento successivo a quello dato
     * @param current centro di cui si vuole trovare il successivo
     * @param path percorso su cui effettuare la ricerca
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

    /**
     * Unisce le stringhe date come argomento in un'unica stringa,
     * utilizzando il carattere ':' come delimitatore di campo
     * @param strings Vector contenente le stringhe che si vogliono unire
     * @return Stringa realizzata
     */
    private String joinStrings(Vector<String> strings) {
        return String.join(":", strings);
    }
}
