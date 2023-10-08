package org.godari.dronedelivery.services.algorithm.dto;

public class NodePair {

    private int startNode;
    private int endNode;

    public NodePair(int startNode, int endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    @Override
    public boolean equals(Object obj) {
        return ((this.startNode == ((NodePair) obj).startNode) && (this.endNode == ((NodePair) obj).endNode)) ||
                ((this.startNode == ((NodePair) obj).endNode) && (this.endNode == ((NodePair) obj).startNode));
    }
}