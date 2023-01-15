package unipa.prog3.controller.service.util;

/**
 * Classe per la visita in ampiezza di un grafo
 * */
public class BFSNode<T> extends Node<T> {
    private BFSNode<T> parent;
    private NodeColor color;
    private int distance;

    public BFSNode(T data) {
        super(data);
        color = NodeColor.BLACK;
    }

    public BFSNode<T> getParent() {
        return parent;
    }

    public void setParent(BFSNode<T> parent) {
        this.parent = parent;
    }

    public NodeColor getColor() {
        return color;
    }

    public void setColor(NodeColor color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Enumeratore per la colorazione dei nodi durante la visita BFS
     * */
    public enum NodeColor {
        BLACK, GRAY, WHITE
    }
}
