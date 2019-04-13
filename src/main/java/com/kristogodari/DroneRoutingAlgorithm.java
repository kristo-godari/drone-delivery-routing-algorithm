package com.kristogodari;

import java.util.*;

public class DroneRoutingAlgorithm {

    private Stack<Node> bestRoute;

    public DroneRoutingAlgorithm() {
        bestRoute = new Stack<>();
    }

    public Stack<Node> getBestRoute(Node startingNode) {

        PriorityQueue<Node> nodesToVisit = new PriorityQueue<>();
        Stack<Node> nodesVisited = new Stack<>();

        startingNode.setDistance(0);
        nodesVisited.push(startingNode);

        for (Edge edge : startingNode.getNeighbors()) {
            if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE) {

                Node wareHouseNode = edge.getDestinationNode();
                wareHouseNode.setDistance(edge.getSourceNode().getDistance() + edge.getWeight());

                nodesToVisit.add(wareHouseNode);
            }
        }

        visitRecursivelyAllNodes(nodesToVisit, nodesVisited);

        return this.bestRoute;

    }

    private void visitRecursivelyAllNodes(PriorityQueue<Node> nodesToVisit, Stack<Node> nodesVisited) {

        while (!nodesToVisit.isEmpty()) {

            PriorityQueue<Node> nodesToVisitRecursively = new PriorityQueue<>();

            Node wareHouseNode = nodesToVisit.poll();

            for (Edge edge : wareHouseNode.getNeighbors()) {
                if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE && !nodesVisited.contains(edge.getDestinationNode())) {

                    Node neighborNode = new Node(edge.getDestinationNode().getData(), edge.getDestinationNode().getType());
                    neighborNode.setDistance(wareHouseNode.getDistance() + edge.getWeight());
                    neighborNode.setNeighbors(edge.getDestinationNode().getNeighbors());

                    nodesToVisitRecursively.add(neighborNode);
                }
            }

            nodesVisited.push(wareHouseNode);

            if (nodesToVisitRecursively.isEmpty()) {

                Node lastWareHouseVisited = nodesVisited.peek();

                if (lastWareHouseVisited.getNeighbors().size() == 0) {

                    if(this.bestRoute.empty()){
                        for (Node node : nodesVisited) {
                           this.bestRoute.push(node);
                        }
                    }else{
                        if(nodesVisited.peek().getDistance() < this.bestRoute.peek().getDistance()){
                            this.bestRoute.clear();
                            for (Node node : nodesVisited) {
                                this.bestRoute.push(node);
                            }
                        }
                    }
                }

                for (Edge edge : lastWareHouseVisited.getNeighbors()) {
                    if (edge.getDestinationNode().getType() != Node.TYPE_WAREHOUSE && !nodesVisited.contains(edge.getDestinationNode())) {

                        Node neighborNode = new Node(edge.getDestinationNode().getData(), edge.getDestinationNode().getType());
                        neighborNode.setDistance(lastWareHouseVisited.getDistance() + edge.getWeight());
                        neighborNode.setNeighbors(edge.getDestinationNode().getNeighbors());

                        nodesToVisitRecursively.add(neighborNode);
                    }
                }

            }

            visitRecursivelyAllNodes(nodesToVisitRecursively, nodesVisited);
        }

        nodesVisited.pop();
    }
}
