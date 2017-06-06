package com.kristogodari.dronerouting;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    public static final String TYPE_WAREHOUSE = "WAREHOUSE";
    public static final String TYPE_CLIENT = "CLIENT";
    public static final String TYPE_DRONE = "DRONE";

    private String type;

    private double distance;

    private String data;

    private List<Edge> neighbors;

    public Node(String data, String type) {
        this.type = type;
        this.data = data;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(Edge neighbor) {
        this.neighbors.add(neighbor);
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

    @Override
    public boolean equals(Object obj) {
        return this.data == ((Node) obj).data;
    }
}
