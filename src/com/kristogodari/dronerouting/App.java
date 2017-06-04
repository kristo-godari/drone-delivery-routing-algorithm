package com.kristogodari.dronerouting;

public class App {

    public static void main(String[] args) {

        Node c1 = new Node("C1", Node.TYPE_CLIENT);
        Node c2 = new Node("C2", Node.TYPE_CLIENT);
        Node c3 = new Node("C3", Node.TYPE_CLIENT);
        Node c4 = new Node("C4", Node.TYPE_CLIENT);

        Node w1 = new Node("W1", Node.TYPE_WAREHOUSE);
        Node w2 = new Node("W2", Node.TYPE_WAREHOUSE);
        Node w3 = new Node("W3", Node.TYPE_WAREHOUSE);

        Node d1 = new Node("D1", Node.TYPE_DRONE);
        Node d2 = new Node("D2", Node.TYPE_DRONE);
        Node d3 = new Node("D3", Node.TYPE_DRONE);

        c4.addNeighbor(new Edge(1, c4, w1));
        c4.addNeighbor(new Edge(2, c4, w2));
        c4.addNeighbor(new Edge(4, c4, w3));

        w1.addNeighbor(new Edge(4, w1, w2));
        w1.addNeighbor(new Edge(6, w1, w3));

        w2.addNeighbor(new Edge(4, w2, w1));
        w2.addNeighbor(new Edge(5, w2, w3));

        w3.addNeighbor(new Edge(3, w3, w1));
        w3.addNeighbor(new Edge(5, w3, w2));

        w1.addNeighbor(new Edge(7, w1, c1));
        w1.addNeighbor(new Edge(3, w1, c2));
        w1.addNeighbor(new Edge(4, w1, c3));

        w2.addNeighbor(new Edge(5, w2, c1));
        w2.addNeighbor(new Edge(2, w2, c2));
        w2.addNeighbor(new Edge(6, w2, c3));

        w3.addNeighbor(new Edge(7, w3, c1));
        w3.addNeighbor(new Edge(4, w3, c2));
        w3.addNeighbor(new Edge(3, w3, c3));

        c1.addNeighbor(new Edge(2, c1,d1));
        c2.addNeighbor(new Edge(1, c2,d2));
        c3.addNeighbor(new Edge(3, c3,d3));

        DroneRoutingAlgorithm droneRoutingAlgorithm = new DroneRoutingAlgorithm();
        droneRoutingAlgorithm.route(c4);
    }
}
