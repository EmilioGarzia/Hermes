package unipa.prog3.controller.service.util;

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

    public enum NodeColor {
        BLACK, GRAY, WHITE
    }
}
