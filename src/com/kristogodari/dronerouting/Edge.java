package com.kristogodari.dronerouting;

public class Edge {

    private double weight;

    private Node sourceNode;

    private Node destinationNode;

    public Edge(double weight, Node sourceNode, Node destinationNode) {
        this.weight = weight;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(Node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }
}
