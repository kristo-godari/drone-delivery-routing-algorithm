package org.godari.dronedelivery.services.algorithm.dto.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphNode implements Comparable<GraphNode> {

    private NodeType type;

    private double distanceFromStartingNode;

    private Object data;

    private List<Edge> edges;

    public GraphNode(NodeType type, Object data) {
        this.type = type;
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public GraphNode(NodeType type, Object data, double distanceFromStartingNode) {
        this.type = type;
        this.data = data;
        this.distanceFromStartingNode = distanceFromStartingNode;
        this.edges = new ArrayList<>();
    }

    public GraphNode() {
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public double getDistanceFromStartingNode() {
        return distanceFromStartingNode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public void setDistanceFromStartingNode(double distanceFromStartingNode) {
        this.distanceFromStartingNode = distanceFromStartingNode;
    }

    @Override
    public String toString() {
        return this.data + "";
    }

    @Override
    public int compareTo(GraphNode otherGraphNode) {
        return Double.compare(this.distanceFromStartingNode, otherGraphNode.getDistanceFromStartingNode());
    }

    @Override
    public boolean equals(Object obj) {
        return this.data == ((GraphNode) obj).data;
    }
}
