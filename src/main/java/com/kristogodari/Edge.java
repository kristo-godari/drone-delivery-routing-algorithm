// Here a class named Edge is being defined in package 'com.kristogodari'
package com.kristogodari;

public class Edge {

    private double weight;  // Weight of the edge
    private Node sourceNode;  // Source node of the edge
    private Node destinationNode;  // Destination node of the edge

    // Constructor:
    public Edge(double weight, Node sourceNode, Node destinationNode) {
        this.weight = weight;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
    }

    // Get the weight of the edge
    public double getWeight() {
        return weight;  
    }

    // Set the weight of the edge
    public void setWeight(double weight) {
        this.weight = weight;  
    }

    // Get the source node of the edge
    public Node getSourceNode() {
        return sourceNode;  
    }

    // Set the source node of the edge
    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode; 
    }

    // Get the destination node of the edge
    public Node getDestinationNode() {
        return destinationNode;  
    }


    // Set the destination node of the edge
    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;  
    }
}