package com.kristogodari;

import java.util.*;

public class DroneRoutingAlgorithm {

    // This variable stores the best route found by the algorithm, represented as a stack of nodes.
    private Stack<Node> bestRoute;

    // Constructor to initialize the bestRoute stack.
    public DroneRoutingAlgorithm() {
        bestRoute = new Stack<>();
    }


    // Routing Algorithm
    // This method finds the best route for a drone starting from the given startingNode.
    public Stack<Node> getBestRoute(Node startingNode) {

        // A priority Queue to store the nodes to be visited
        PriorityQueue<Node> nodesToVisit = new PriorityQueue<>();

        // This stack stores all the nodes that have been visited.
        Stack<Node> nodesVisited = new Stack<>();

        // Since the starting node stands at a zero distance:
        startingNode.setDistance(0); 
        // Since the starting node has been visited:
        nodesVisited.push(startingNode);

        // Traversal:
        // The method iterates through neighboring edges of the starting node, identifies warehouse nodes,
        // and recursively visits nodes, considering the best route based on distances
        for (Edge edge : startingNode.getNeighbors()) {

            // Check if destination of each edge is a warehouse
            if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE) {


                // Visiting Warehouses:
                /*
                 * If the destination node is a warehouse, the algorithm calculates the distance 
                 * to the warehouse node from the starting node and adds it to the nodesToVisit 
                 * priority queue to be explored later.
                 */
                Node wareHouseNode = edge.getDestinationNode();
                // Dist to warehouse = dist of current source node + dist from currentsource to warehouse
                wareHouseNode.setDistance(edge.getSourceNode().getDistance() + edge.getWeight());

                // add this node to 'nodesToVisit'
                nodesToVisit.add(wareHouseNode);
            }
        }

        // Recursively visit all nodes considering all nodes to visit and nodes already visited.
        visitRecursivelyAllNodes(nodesToVisit, nodesVisited);

        // Return best route
        return this.bestRoute;

    }



    // Recursively visit all nodes considering all nodes to visit and nodes already visited.
    private void visitRecursivelyAllNodes(PriorityQueue<Node> nodesToVisit, Stack<Node> nodesVisited) {

        // While Loop for Traversal:
        // The traversal occurs within a while loop,-
        // -which continues until there are no more nodes to visit.
        while (!nodesToVisit.isEmpty()) {

            // Creates a new priority queue for nodes to be visited recursively.
            PriorityQueue<Node> nodesToVisitRecursively = new PriorityQueue<>();

            // Polls a node (likely a warehouse node) from the nodesToVisit priority queue to process.
            Node wareHouseNode = nodesToVisit.poll();

            // This loop iterates through the neighbors (edges) of the current node (likely a warehouse node) to determine the next nodes to visit.
            for (Edge edge : wareHouseNode.getNeighbors()) 
            {
                // Checks if the destination node of the edge is a warehouse
                // Also checks if the destination node has not been visited already 
                if (edge.getDestinationNode().getType() == Node.TYPE_WAREHOUSE && !nodesVisited.contains(edge.getDestinationNode())) {

                    // Creates a new node (neighborNode) based on the destination node of the edge.
                    // Sets the data and type of the destination node to the new node.
                    Node neighborNode = new Node(edge.getDestinationNode().getData(), edge.getDestinationNode().getType());
                    
                    // Calculates and sets the distance of the new node by adding the edge weight to the distance of the current warehouse node.
                    neighborNode.setDistance(wareHouseNode.getDistance() + edge.getWeight());

                    // Copies the neighbors of the destination node to the new node.
                    neighborNode.setNeighbors(edge.getDestinationNode().getNeighbors());

                    // Adds the new node (neighborNode) to the nodesToVisitRecursively priority queue, so it will be visited in subsequent recursive traversal steps.
                    nodesToVisitRecursively.add(neighborNode);
                }
            }


            nodesVisited.push(wareHouseNode);


            // Checks if the recursive visit queue is empty, indicating that all reachable nodes have been visited.
            if (nodesToVisitRecursively.isEmpty()) 
            {

                // Obtains the last visited warehouse node by peeking at the top of the nodesVisited stack.
                Node lastWareHouseVisited = nodesVisited.peek();

                // Checks if the last visited warehouse node has no neighbors (edges), meaning it's a leaf node.
                if (lastWareHouseVisited.getNeighbors().size() == 0) 
                {

                    // If the best route stack is empty, it adds the nodes visited so far to the best route stack
                    if(this.bestRoute.empty())
                    {
                        for (Node node : nodesVisited) {
                           this.bestRoute.push(node);
                        }
                    }
                    
                    // Else, it compares the distance of the last visited warehouse node with the distance of the top node in the best route stack, updating the best route if needed.
                    else
                    {
                        if(nodesVisited.peek().getDistance() < this.bestRoute.peek().getDistance()){
                            this.bestRoute.clear();
                            for (Node node : nodesVisited) {
                                this.bestRoute.push(node);
                            }
                        }
                    }
                }

                // last visited warehouse node is not a leaf node
                // If the last visited warehouse node has neighbors (edges), it iterates through its neighbors.

                for (Edge edge : lastWareHouseVisited.getNeighbors()) 
                {
                    
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
