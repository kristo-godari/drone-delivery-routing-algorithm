package com.kristogodari.dronerouting;

import org.apache.commons.lang.time.StopWatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class App {

    public static void main(String[] args) {

        int clientNo = 100;
        int wareHouseNo = 7;
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
                if(i != j && !pairQueue.contains(new NodePair(i,j))){

                    int weight = (int) (Math.random() * 15 + 1);
                    warehouses.get(i).addNeighbor(new Edge(weight, warehouses.get(i), warehouses.get(j)));
                    warehouses.get(j).addNeighbor(new Edge(weight, warehouses.get(j), warehouses.get(i)));
                    pairQueue.add(new NodePair(i,j));
                }
            }
        }

        // connect the new client to warehouses.
        Node newClient = new Node("C0", Node.TYPE_CLIENT);

        for (Node warehouse : warehouses) {
            int weight = (int) (Math.random() * 15 + 1);
            newClient.addNeighbor(new Edge(weight, newClient, warehouse));
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


        // print best route and running time
        for (Node node : bestRoute) {
            System.out.print(node + "->");
        }

        System.out.println(bestRoute.peek().getDistance());
        System.out.println((timeTaken / 1000) + "");
    }
}
