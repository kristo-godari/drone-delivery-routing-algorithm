// Here a class named Node is being defined in package 'com.kristogodari'
package com.kristogodari;

// Importing necessary java utilities from util package.
import java.util.ArrayList;
import java.util.List;

// Node class symbolises a node in the graph.
// It also implements the comparable interface.
// This interface compares nodes based on distance.
public class Node implements Comparable<Node> {

    // Constants representing different types of nodes: "WAREHOUSE", "CLIENT", and "DRONE".
    public static final String TYPE_WAREHOUSE = "WAREHOUSE";
    public static final String TYPE_CLIENT = "CLIENT";
    public static final String TYPE_DRONE = "DRONE";


    // Making some member variable declerations.

    // The type of the node (one of the constants defined above).
    private String type;

    // The distance of the node (used for comparisons).
    private double distance;

    // The data associated with the node.
    private String data;

    // A list of edges representing neighboring nodes.
    // This is a reference to all of our current node's neighbouring nodes.
    private List<Edge> neighbors;

    // Constructor to initialize a Node with data, type and neighbours.
    public Node(String data, String type) {
        this.type = type;
        this.data = data;
        this.neighbors = new ArrayList<>();
    }


    // Member function or methods:

    // Adds a neighbor (an edge) to the node.
    public void addNeighbor(Edge neighbor) {
        this.neighbors.add(neighbor);
    }

    // Returns the distance of the node.
    public double getDistance() {
        return distance;
    }

    // Returns the data associated with the node.
    public String getData() {
        return data;
    }

    //  Sets the data associated with the node.
    public void setData(String data) {
        this.data = data;
    }

    // Returns the list of neighboring edges.
    public List<Edge> getNeighbors() {
        return neighbors;
    }

    // Sets the list of neighboring edges.
    public void setNeighbors(List<Edge> neighbors) {
        this.neighbors = neighbors;
    }

    // Returns the type of the node.
    public String getType() {
        return type;
    }

    // Sets the type of the node.
    public void setType(String type) {
        this.type = type;
    }

    // Sets the distance of the node.
    public void setDistance(double distance) {
        this.distance = distance;
    }


    // Overrides the toString() method to return the node's data.
    @Override
    public String toString() {
        return this.data + "";
    }

    // Overrides the compareTo() method to compare nodes based on distance.
    @Override
    public int compareTo(Node otherNode) {
        return Double.compare(this.distance, otherNode.getDistance());
    }

    // Overrides the equals() method to compare nodes based on data.
    @Override
    public boolean equals(Object obj) {
        return this.data == ((Node) obj).data;
    }
}
