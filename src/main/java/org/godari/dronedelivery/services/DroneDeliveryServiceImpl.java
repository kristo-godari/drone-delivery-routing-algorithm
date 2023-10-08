package org.godari.dronedelivery.services;

import lombok.AllArgsConstructor;
import org.godari.dronedelivery.services.client.Client;
import org.godari.dronedelivery.services.algorithm.ShortestPathAlgorithmService;
import org.godari.dronedelivery.services.location.Location;
import org.godari.dronedelivery.services.location.LocationService;
import org.godari.dronedelivery.services.drone.Drone;
import org.godari.dronedelivery.services.drone.DroneService;
import org.godari.dronedelivery.services.algorithm.dto.ClientNode;
import org.godari.dronedelivery.services.algorithm.dto.DroneNode;
import org.godari.dronedelivery.services.order.Order;
import org.godari.dronedelivery.services.algorithm.dto.NodePair;
import org.godari.dronedelivery.services.algorithm.dto.WarehouseNode;
import org.godari.dronedelivery.services.algorithm.dto.graph.Edge;
import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;
import org.godari.dronedelivery.services.warehouse.Warehouse;
import org.godari.dronedelivery.services.warehouse.WarehouseService;

import java.util.*;

@AllArgsConstructor
public class DroneDeliveryServiceImpl implements DroneDeliveryService {

    private final ShortestPathAlgorithmService algorithm;
    private final WarehouseService warehouseService;
    private final DroneService dronesService;
    private final LocationService locationService;

    /**
     * In order to find the fastest drone, we first need to build a graph and then find the shortest path in the graph.
     * This method will build a weighted graph, with a particular structure.
     * The structure can be found here: https://github.com/kristo-godari/drone-delivery-routing-algorithm/blob/master/docs/graph.png
     */
    @Override
    public Drone findFastestDroneFor(Order order) {

        /**
         * Get all warehouses for the current order and convert them to warehouse graph nodes.
         */
        List<Warehouse> warehouses = warehouseService.getWarehousesForOrder(order);
        List<WarehouseNode> warehouseNodes = getWarehouseNodes(warehouses);

        /**
         * Get all drones that are available for delivery, including drones that are currently delivering.
         * Convert drones to drones node, and connect them to client nodes, and return the client nodes.
         */
        List<Drone> drones = dronesService.getOperatingDrones();
        List<ClientNode> clientNodes = getClientNodes(drones);


        /**
         * Connect warehouses to client nodes, and to each other
         */
        connectWarehousesToClientNodes(warehouseNodes, clientNodes);
        connectWarehousesToEachOther(warehouseNodes);

        /**
         * Create the top level client node, for which we need to calculate the shortest path
         * and connect it to warehouses
         */
        ClientNode clientNode = new ClientNode(order.getClient());
        connectClientToWarehouses(warehouseNodes, clientNode);

        /**
         * Run the algorithm
         */
        Stack<GraphNode> shortestPath = algorithm.getShortestPathStartingFromNode(clientNode);

        /**
         * Convert the result to a drone object and return it.
         */
        return (Drone) shortestPath.pop().getData();
    }

    private static List<WarehouseNode> getWarehouseNodes(List<Warehouse> warehouses) {
        List<WarehouseNode> warehouseNodes = new ArrayList<>();
        warehouses.forEach(warehouse -> {
            warehouseNodes.add(new WarehouseNode(warehouse));
        });

        return warehouseNodes;
    }

    private List<ClientNode> getClientNodes(List<Drone> drones) {
        List<ClientNode> clientNodes = new ArrayList<>();
        drones.forEach(drone -> {
            Client currentClient = drone.getCurrentClient();

            ClientNode clientNode = new ClientNode(currentClient);
            DroneNode droneNode = new DroneNode(drone);

            double distance = getDistanceBetween(drone.getCurrentPosition(), currentClient.getLocation());
            clientNode.addEdge(new Edge(distance, clientNode, droneNode));

            clientNodes.add(clientNode);
        });

        return clientNodes;
    }

    private void connectWarehousesToClientNodes(List<WarehouseNode> warehouseNodes, List<ClientNode> clientNodes) {
        warehouseNodes.forEach(warehouseNode -> {
            clientNodes.forEach(clientNode -> {
                Warehouse warehouse = (Warehouse) warehouseNode.getData();
                Client client = (Client) clientNode.getData();

                double distance = getDistanceBetween(warehouse.getLocation(), client.getLocation());

                warehouseNode.addEdge(new Edge(distance, warehouseNode, clientNode));
            });
        });
    }

    private void connectWarehousesToEachOther(List<WarehouseNode> warehouseNodes) {
        Queue<NodePair> pairQueue = new LinkedList<>();

        for (int i = 0; i < warehouseNodes.size(); i++) {
            for (int j = 0; j < warehouseNodes.size(); j++) {
                if (i != j && !pairQueue.contains(new NodePair(i, j))) {

                    Warehouse warehouse1 = (Warehouse) warehouseNodes.get(i).getData();
                    Warehouse warehouse2 = (Warehouse) warehouseNodes.get(j).getData();

                    double weight = getDistanceBetween(warehouse1.getLocation(), warehouse2.getLocation());

                    warehouseNodes.get(i).addEdge(new Edge(weight, warehouseNodes.get(i), warehouseNodes.get(j)));
                    warehouseNodes.get(j).addEdge(new Edge(weight, warehouseNodes.get(j), warehouseNodes.get(i)));

                    pairQueue.add(new NodePair(i, j));
                }
            }
        }
    }

    private void connectClientToWarehouses(List<WarehouseNode> warehouseNodes, ClientNode clientNode) {
        for (GraphNode warehouseNode : warehouseNodes) {
            Warehouse warehouse = (Warehouse) warehouseNode.getData();
            Client client = (Client) clientNode.getData();

            double distance = getDistanceBetween(warehouse.getLocation(), client.getLocation());

            clientNode.addEdge(new Edge(distance, clientNode, warehouseNode));
        }
    }

    private double getDistanceBetween(Location location1, Location location2) {
        return locationService.calculateDistance(location1, location2);
    }
}
