package unipa.prog3.controller.service.util;

import java.util.Vector;


/**
 * Classe per la rappresentazione dei singoli nodi che compongono il grafo
 * */
public abstract class Node<T> extends Vector<Node<T>> {
    private final T data;

    public Node(T data) {
        super();
        this.data = data;
    }

    /**
     * Aggiunge un arco di connessione (non orientato) tra due nodi del grafo
     * @param n nodo a cui collegare l'oggetto in questione
     * */
    public void connectTo(Node<T> n) {
        add(n);
        n.add(this);
    }

    public T getData() {
        return data;
    }
}
