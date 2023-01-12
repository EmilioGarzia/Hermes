package unipa.prog3.controller.service.util;

import java.util.*;

public class Graph<T> extends HashMap<T, Node<T>> {
    public void breadthFirstSearch(BFSNode<T> source) {
        for (Node<T> node : values()) {
            BFSNode<T> bfsNode = (BFSNode<T>) node;
            bfsNode.setColor(BFSNode.NodeColor.BLACK);
            bfsNode.setParent(null);
        }
        source.setDistance(0);

        Queue<BFSNode<T>> queue = new ArrayDeque<>();
        queue.add(source);
        while(!queue.isEmpty()) {
            BFSNode<T> n1 = queue.peek();
            n1.setColor(BFSNode.NodeColor.WHITE);
            for (Node<T> n2 : n1) {
                BFSNode<T> bfsNode = (BFSNode<T>) n2;
                if (bfsNode.getColor() != BFSNode.NodeColor.WHITE) {
                    bfsNode.setColor(BFSNode.NodeColor.GRAY);
                    bfsNode.setDistance(n1.getDistance() + 1);
                    bfsNode.setParent(n1);
                    queue.add(bfsNode);
                }
            }
        }
    }
}
