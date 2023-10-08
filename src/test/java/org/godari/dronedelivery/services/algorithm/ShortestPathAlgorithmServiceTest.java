package org.godari.dronedelivery.services.algorithm;

import org.godari.dronedelivery.services.algorithm.dto.ClientNode;
import org.godari.dronedelivery.services.algorithm.dto.DroneNode;
import org.godari.dronedelivery.services.algorithm.dto.NodePair;
import org.godari.dronedelivery.services.algorithm.dto.WarehouseNode;
import org.godari.dronedelivery.services.algorithm.dto.graph.Edge;
import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShortestPathAlgorithmServiceTest {

    @Test
    public void testAlgorithmCorrectness() {

        // Given

        // create clients
        ClientNode clientNode1 = new ClientNode("C1");
        ClientNode clientNode2 = new ClientNode("C2");
        ClientNode clientNode3 = new ClientNode("C3");

        // create warehouses
        WarehouseNode warehouseNode1 = new WarehouseNode("W1");
        WarehouseNode warehouseNode2 = new WarehouseNode("W2");
        WarehouseNode warehouseNode3 = new WarehouseNode("W3");

        // create drones
        DroneNode droneNode1 = new DroneNode("D1");
        DroneNode droneNode2 = new DroneNode("D2");
        DroneNode droneNode3 = new DroneNode("D3");


        // connect clients to drones
        clientNode1.addEdge(new Edge(2, clientNode1, droneNode1));
        clientNode2.addEdge(new Edge(1, clientNode2, droneNode2));
        clientNode3.addEdge(new Edge(1, clientNode3, droneNode3));

        // connect warehouses to clients
        warehouseNode1.addEdge(new Edge(5, warehouseNode1, clientNode1));
        warehouseNode1.addEdge(new Edge(9, warehouseNode1, clientNode2));
        warehouseNode1.addEdge(new Edge(11, warehouseNode1, clientNode3));

        warehouseNode2.addEdge(new Edge(7, warehouseNode2, clientNode1));
        warehouseNode2.addEdge(new Edge(3, warehouseNode2, clientNode2));
        warehouseNode2.addEdge(new Edge(4, warehouseNode2, clientNode3));

        warehouseNode3.addEdge(new Edge(9, warehouseNode3, clientNode1));
        warehouseNode3.addEdge(new Edge(8, warehouseNode3, clientNode2));
        warehouseNode3.addEdge(new Edge(6, warehouseNode3, clientNode3));

        // connect warehouses with each other
        warehouseNode1.addEdge(new Edge(2, warehouseNode1, warehouseNode2));
        warehouseNode1.addEdge(new Edge(4, warehouseNode1, warehouseNode3));

        warehouseNode2.addEdge(new Edge(2, warehouseNode2, warehouseNode1));
        warehouseNode2.addEdge(new Edge(2, warehouseNode2, warehouseNode3));

        warehouseNode3.addEdge(new Edge(4, warehouseNode3, warehouseNode1));
        warehouseNode3.addEdge(new Edge(2, warehouseNode3, warehouseNode2));


        // connect the new client to warehouses.
        ClientNode clientNode4 = new ClientNode("C4");

        clientNode4.addEdge(new Edge(4, clientNode4, warehouseNode1));
        clientNode4.addEdge(new Edge(3, clientNode4, warehouseNode2));
        clientNode4.addEdge(new Edge(6, clientNode4, warehouseNode3));

        // When we run algorithm and find best route
        ShortestPathAlgorithmService algorithm = new ShortestPathAlgorithmService();
        Stack<GraphNode> shortestPath = algorithm.getShortestPathStartingFromNode(clientNode4);

        // Then assert
        assertEquals(14.0, shortestPath.peek().getDistanceFromStartingNode());
        assertEquals("D2", shortestPath.peek().getData());
    }

    @Test
    public void testAlgorithmPerformance() {

        int clientNo = 100;
        int wareHouseNo = 1;
        int dronesNo = 100;

        // create clients objects
        ArrayList<ClientNode> clientNodes = new ArrayList<>();
        for (int i = 0; i < clientNo; i++) {
            clientNodes.add(new ClientNode("C" + (i + 1)));
        }

        // create warehouses objects
        ArrayList<WarehouseNode> warehouseNodes = new ArrayList<>();
        for (int i = 0; i < wareHouseNo; i++) {
            warehouseNodes.add(new WarehouseNode("W" + (i + 1)));
        }

        // create drones objects
        ArrayList<DroneNode> droneNodes = new ArrayList<>();
        for (int i = 0; i < dronesNo; i++) {
            droneNodes.add(new DroneNode("D" + (i + 1)));
        }

        // connect clients to drones
        for (int i = 0; i < clientNodes.size(); i++) {
            int weight = (int) (Math.random() * 15 + 1);
            clientNodes.get(i).addEdge(new Edge(weight, clientNodes.get(i), droneNodes.get(i)));
        }

        // connect warehouses to clients
        for (int i = 0; i < warehouseNodes.size(); i++) {
            for (int j = 0; j < clientNodes.size(); j++) {
                int weight = (int) (Math.random() * 15 + 1);
                warehouseNodes.get(i).addEdge(new Edge(weight, warehouseNodes.get(i), clientNodes.get(j)));
            }
        }

        // connect warehouses with each other
        Queue<NodePair> pairQueue = new LinkedList<>();

        for (int i = 0; i < warehouseNodes.size(); i++) {
            for (int j = 0; j < warehouseNodes.size(); j++) {
                if (i != j && !pairQueue.contains(new NodePair(i, j))) {

                    int weight = (int) (Math.random() * 15 + 1);
                    warehouseNodes.get(i).addEdge(new Edge(weight, warehouseNodes.get(i), warehouseNodes.get(j)));
                    warehouseNodes.get(j).addEdge(new Edge(weight, warehouseNodes.get(j), warehouseNodes.get(i)));
                    pairQueue.add(new NodePair(i, j));
                }
            }
        }

        // connect the new client to warehouses.
        ClientNode newClientNode = new ClientNode("C0");

        for (GraphNode warehouse : warehouseNodes) {
            int weight = (int) (Math.random() * 15 + 1);
            newClientNode.addEdge(new Edge(weight, newClientNode, warehouse));
        }

        // start profiling
        long startTimeMillis = System.currentTimeMillis();

        // run algorithm and find best route
        ShortestPathAlgorithmService algorithm = new ShortestPathAlgorithmService();
        algorithm.getShortestPathStartingFromNode(newClientNode);

        // strop profiling
        long endTimeMillis = System.currentTimeMillis();
        long timeTaken = endTimeMillis - startTimeMillis;
        System.out.println("Execution time:" + timeTaken);

        // Assert that is less than 80 s
        assertTrue(timeTaken < 80000);
    }
}
