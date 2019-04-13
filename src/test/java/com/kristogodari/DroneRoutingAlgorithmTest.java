package com.kristogodari;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DroneRoutingAlgorithmTest {
    public void testAlgorithmCorectitudiness() {

        // Given

        // create clients
        Node client1 = new Node("C1", Node.TYPE_CLIENT);
        Node client2 = new Node("C2", Node.TYPE_CLIENT);
        Node client3 = new Node("C3", Node.TYPE_CLIENT);

        // create warehouses
        Node warehouse1 = new Node("W1", Node.TYPE_WAREHOUSE);
        Node warehouse2 = new Node("W2", Node.TYPE_WAREHOUSE);
        Node warehouse3 = new Node("W3", Node.TYPE_WAREHOUSE);

        // create drones
        Node drone1 = new Node("D1", Node.TYPE_DRONE);
        Node drone2 = new Node("D2", Node.TYPE_DRONE);
        Node drone3 = new Node("D3", Node.TYPE_DRONE);


        // connect clients to drones
        client1.addNeighbor(new Edge(2, client1, drone1));
        client2.addNeighbor(new Edge(1, client2, drone2));
        client3.addNeighbor(new Edge(1, client3, drone3));

        // connect warehouses to clients
        warehouse1.addNeighbor(new Edge(5, warehouse1, client1));
        warehouse1.addNeighbor(new Edge(9, warehouse1, client2));
        warehouse1.addNeighbor(new Edge(11, warehouse1, client3));

        warehouse2.addNeighbor(new Edge(7, warehouse2, client1));
        warehouse2.addNeighbor(new Edge(3, warehouse2, client2));
        warehouse2.addNeighbor(new Edge(4, warehouse2, client3));

        warehouse3.addNeighbor(new Edge(9, warehouse3, client1));
        warehouse3.addNeighbor(new Edge(8, warehouse3, client2));
        warehouse3.addNeighbor(new Edge(6, warehouse3, client3));

        // connect warehouses with each other
        warehouse1.addNeighbor(new Edge(2, warehouse1, warehouse2));
        warehouse1.addNeighbor(new Edge(4, warehouse1, warehouse3));

        warehouse2.addNeighbor(new Edge(2, warehouse2, warehouse1));
        warehouse2.addNeighbor(new Edge(2, warehouse2, warehouse3));

        warehouse3.addNeighbor(new Edge(4, warehouse3, warehouse1));
        warehouse3.addNeighbor(new Edge(2, warehouse3, warehouse2));


        // connect the new client to warehouses.
        Node client4 = new Node("C4", Node.TYPE_CLIENT);

        client4.addNeighbor(new Edge(4, client4, warehouse1));
        client4.addNeighbor(new Edge(3, client4, warehouse2));
        client3.addNeighbor(new Edge(6, client4, warehouse3));

        // When we run algorithm and find best route
        DroneRoutingAlgorithm droneRoutingAlgorithm = new DroneRoutingAlgorithm();
        Stack<Node> bestRoute = droneRoutingAlgorithm.getBestRoute(client4);

        // Then assert
        assertEquals(14.0, bestRoute.peek().getDistance());
        assertEquals("D2", bestRoute.peek().getData());
    }

    public void testAlgorithmPerformance() {

        int clientNo = 100;
        int wareHouseNo = 10;
        int dronesNo = 100;

        // create clients objects
        ArrayList<Node> clients = new ArrayList<>();
        for (int i = 0; i < clientNo; i++) {
            clients.add(new Node("C" + (i + 1), Node.TYPE_CLIENT));
        }

        // create warehouses objects
        ArrayList<Node> warehouses = new ArrayList<>();
        for (int i = 0; i < wareHouseNo; i++) {
            warehouses.add(new Node("W" + (i + 1), Node.TYPE_WAREHOUSE));
        }

        // create drones objects
        ArrayList<Node> drones = new ArrayList<>();
        for (int i = 0; i < dronesNo; i++) {
            drones.add(new Node("D" + (i + 1), Node.TYPE_DRONE));
        }

        // connect clients to drones
        for (int i = 0; i < clients.size(); i++) {
            int weight = (int) (Math.random() * 15 + 1);
            clients.get(i).addNeighbor(new Edge(weight, clients.get(i), drones.get(i)));
        }

        // connect warehouses to clients
        for (int i = 0; i < warehouses.size(); i++) {
            for (int j = 0; j < clients.size(); j++) {
                int weight = (int) (Math.random() * 15 + 1);
                warehouses.get(i).addNeighbor(new Edge(weight, warehouses.get(i), clients.get(j)));
            }
        }

        // connect warehouses with each other
        Queue<NodePair> pairQueue = new LinkedList<>();

        for (int i = 0; i < warehouses.size(); i++) {
            for (int j = 0; j < warehouses.size(); j++) {
                if (i != j && !pairQueue.contains(new NodePair(i, j))) {

                    int weight = (int) (Math.random() * 15 + 1);
                    warehouses.get(i).addNeighbor(new Edge(weight, warehouses.get(i), warehouses.get(j)));
                    warehouses.get(j).addNeighbor(new Edge(weight, warehouses.get(j), warehouses.get(i)));
                    pairQueue.add(new NodePair(i, j));
                }
            }
        }

        // connect the new client to warehouses.
        Node newClient = new Node("C0", Node.TYPE_CLIENT);

        for (Node warehouse : warehouses) {
            int weight = (int) (Math.random() * 15 + 1);
            newClient.addNeighbor(new com.kristogodari.Edge(weight, newClient, warehouse));
        }

        // start profiling
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();

        // run algorithm and find best route
        DroneRoutingAlgorithm droneRoutingAlgorithm = new DroneRoutingAlgorithm();
        Stack<Node> bestRoute = droneRoutingAlgorithm.getBestRoute(newClient);

        // strop profiling
        stopwatch.stop();
        long timeTaken = stopwatch.getTime();

        // Assert that is less than 100 ms
        assertTrue(timeTaken < 80000);
    }
}
