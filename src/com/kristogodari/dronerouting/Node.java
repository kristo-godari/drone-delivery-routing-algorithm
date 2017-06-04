package com.kristogodari.dronerouting;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    public static final String TYPE_WAREHOUSE = "WAREHOUSE";
    public static final String TYPE_CLIENT = "CLIENT";
    public static final String TYPE_DRONE = "DRONE";


    private Node parent;

    private String type;

    private double distance; //= Double.MAX_VALUE;

    private String data;

    private List<Edge> neighbors;

    private boolean visited;


    public Node(String data, String type) {
        this.type = type;
        this.data = data;
        this.neighbors = new ArrayList<>();
    }


    public void addNeighbor(Edge neighbor) {
        this.neighbors.add(neighbor);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getDistance() {
        return distance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Edge> neighbors) {
        this.neighbors = neighbors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return this.data + "";
    }

    @Override
    public int compareTo(Node otherNode) {
        return Double.compare(this.distance, otherNode.getDistance());
    }
}
