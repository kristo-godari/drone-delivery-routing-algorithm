// Here a class named NodePair is being defined in package 'com.kristogodari'
package com.kristogodari;

class NodePair {

    private int startNode;  // Starting node in the pair
    private int endNode;  // Ending node in the pair

    // Constructor to initialize a NodePair with start and end nodes
    NodePair(int startNode, int endNode) {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the start and end nodes match in either order
        return ((this.startNode == ((NodePair) obj).startNode) && (this.endNode == ((NodePair) obj).endNode)) ||
                ((this.startNode == ((NodePair) obj).endNode) && (this.endNode == ((NodePair) obj).startNode));
    }
}