package unipa.prog3.controller.service.util;

import java.util.Vector;

public abstract class Node<T> extends Vector<Node<T>> {
    private final T data;

    public Node(T data) {
        super();
        this.data = data;
    }

    public void connectTo(Node<T> n) {
        add(n);
        n.add(this);
    }

    public T getData() {
        return data;
    }
}
