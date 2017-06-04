package com.kristogodari.dronerouting;

import java.awt.*;
import java.util.*;

public class DroneRoutingAlgorithm {


    public void route(Node startingNode) {

        PriorityQueue<Node> nodesToVisit = new PriorityQueue<>();
        Stack<Node> nodesVisited = new Stack<>();

        startingNode.setDistance(0);
        startingNode.setVisited(true);
        nodesVisited.push(startingNode);

        for (Edge edge : startingNode.getNeighbors()) {
            if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE) {

                Node wareHouseNode = edge.getDestinationNode();
                wareHouseNode.setDistance(edge.getSourceNode().getDistance() + edge.getWeight());
                wareHouseNode.setParent(startingNode);

                nodesToVisit.add(wareHouseNode);
            }
        }

        visitRecursiveAllWarehouseNodes(nodesToVisit, nodesVisited);

    }

    private void visitRecursiveAllWarehouseNodes(PriorityQueue<Node> nodesToVisit, Stack<Node> nodesVisited) {

        while (!nodesToVisit.isEmpty()) {

            Node wareHouseNode = nodesToVisit.poll();

            wareHouseNode.setVisited(true);

            nodesVisited.push(wareHouseNode);

            PriorityQueue<Node> nodesToVisitRecursively = new PriorityQueue<>();

            for (Edge edge : wareHouseNode.getNeighbors()) {
                if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE && !nodesVisited.contains(edge.getDestinationNode())) {

                    Node neighborNode = new Node(edge.getDestinationNode().getData(), edge.getDestinationNode().getType());
                    neighborNode.setDistance(edge.getSourceNode().getDistance() + edge.getWeight());
                    neighborNode.setParent(wareHouseNode);
                    neighborNode.setNeighbors(edge.getDestinationNode().getNeighbors());
                    neighborNode.setVisited(false);

                    nodesToVisitRecursively.add(neighborNode);
                }
            }

            if(nodesToVisitRecursively.isEmpty()){

                Node lastWareHouse = nodesVisited.peek();
                if(lastWareHouse.getNeighbors().size() == 0){
                    for(Node node: nodesVisited){
                        System.out.print(node + "->");
                    }
                    System.out.println(nodesVisited.peek().getDistance());
                }

                for (Edge edge : lastWareHouse.getNeighbors()) {
                    if (edge.getDestinationNode().getType() != Node.TYPE_WAREHOUSE && !nodesVisited.contains(edge.getDestinationNode())) {

                        Node neighborNode = new Node(edge.getDestinationNode().getData(), edge.getDestinationNode().getType());
                        neighborNode.setDistance(edge.getSourceNode().getDistance() + edge.getWeight());
                        neighborNode.setParent(lastWareHouse);
                        neighborNode.setNeighbors(edge.getDestinationNode().getNeighbors());
                        neighborNode.setVisited(edge.getDestinationNode().isVisited());

                        nodesToVisitRecursively.add(neighborNode);
                    }
                }

            }

            visitRecursiveAllWarehouseNodes(nodesToVisitRecursively, nodesVisited);
        }

        nodesVisited.pop();
    }

}
