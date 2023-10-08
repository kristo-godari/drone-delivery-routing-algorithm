package org.godari.dronedelivery.services.algorithm.dto.graph;

public class Edge {

    private double weight;

    private GraphNode sourceGraphNode;

    private GraphNode destinationGraphNode;

    public Edge(double weight, GraphNode sourceGraphNode, GraphNode destinationGraphNode) {
        this.weight = weight;
        this.sourceGraphNode = sourceGraphNode;
        this.destinationGraphNode = destinationGraphNode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public GraphNode getSourceNode() {
        return sourceGraphNode;
    }

    public void setSourceNode(GraphNode sourceGraphNode) {
        this.sourceGraphNode = sourceGraphNode;
    }

    public GraphNode getDestinationNode() {
        return destinationGraphNode;
    }

    public void setDestinationNode(GraphNode destinationGraphNode) {
        this.destinationGraphNode = destinationGraphNode;
    }
}
