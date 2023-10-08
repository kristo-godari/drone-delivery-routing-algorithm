package org.godari.dronedelivery.services.algorithm;

import org.godari.dronedelivery.services.algorithm.dto.graph.Edge;
import org.godari.dronedelivery.services.algorithm.dto.graph.NodeType;
import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;

import java.util.PriorityQueue;
import java.util.Stack;

public class ShortestPathAlgorithmService {

    private final Stack<GraphNode> shortestPath;

    public ShortestPathAlgorithmService() {
        shortestPath = new Stack<>();
    }

    public Stack<GraphNode> getShortestPathStartingFromNode(GraphNode startingNode) {

        // Initialize data structures
        PriorityQueue<GraphNode> nodesToVisitQueue = new PriorityQueue<>();
        Stack<GraphNode> nodesVisitedSoFar = new Stack<>();

        // Mark starting node as visited
        startingNode.setDistanceFromStartingNode(0);
        nodesVisitedSoFar.push(startingNode);

        // adding all neighbors of the starting node in the queue to be visited
        for (Edge edge : startingNode.getEdges()) {
            addToQueueNodeOnEdge(nodesToVisitQueue, edge);
        }

        visitNodesAndUpdateShortestPath(nodesToVisitQueue, nodesVisitedSoFar);

        return this.shortestPath;
    }

    private static void addToQueueNodeOnEdge(PriorityQueue<GraphNode> nodesToVisitQueue, Edge edge) {
        /**
         * We only add warehouse nodes for two reasons:
         *  - Our graph has a certain structure and the starting node is allways connected only to warehouses nodes.
         *  - For delivering an order a drone will need to go through multiple warehouses, and we need to try all the combination between warehouses
         *    to establish what is the optimal route.
         */
        if (NodeType.WAREHOUSE.equals(edge.getDestinationNode().getType())) {
            GraphNode wareHouseNode = edge.getDestinationNode();
            wareHouseNode.setDistanceFromStartingNode(edge.getSourceNode().getDistanceFromStartingNode() + edge.getWeight());

            nodesToVisitQueue.add(wareHouseNode);
        }
    }

    private void visitNodesAndUpdateShortestPath(PriorityQueue<GraphNode> nodesToVisitQueue, Stack<GraphNode> nodesVisitedSoFar) {

        while (!nodesToVisitQueue.isEmpty()) {
            visitNodes(nodesToVisitQueue, nodesVisitedSoFar);
        }

        /**
         * Once a path has been visited we remove last node, and continue from there to the next path.
         * Ex: C4 -> W1 -> W2 -> W3 -> C1 -> D3 , we remove D3 and continue to evaluate childrens of C1
         */
        nodesVisitedSoFar.pop();
    }

    private void visitNodes(PriorityQueue<GraphNode> nodesToVisitQueue, Stack<GraphNode> nodesVisitedSoFar) {

        PriorityQueue<GraphNode> childNodesToVisit = new PriorityQueue<>();
        GraphNode nodeToVisit = nodesToVisitQueue.poll();

        /**
         * At this point we are at a Warehouse node, ex: W1 and since all warehouses are mandatory to be visited,
         * we will get all Warehouse neighbors of W1, and put it in the to visit list.
         * We do this since we need to find which route is the fastest, we need to try all warehouse combinations:
         *  - C4 -> W1 -> W2 -> W3
         *  - C4 -> W3 -> W2 -> W1
         *  - C4 -> W1 -> W3 -> W2
         *  - etc...
         */
        for (Edge edge : nodeToVisit.getEdges()) {
            addWarehouseNodesInToVisitQueue(nodesVisitedSoFar, childNodesToVisit, nodeToVisit, edge);
        }


        // Mark current node as visited
        nodesVisitedSoFar.push(nodeToVisit);

        /**
         * All warehouse combinations have been visited, now we need to take last warehouse visited and visit other nodes
         * that are not warehouse.
         */
        if (childNodesToVisit.isEmpty()) {
            visitOtherNodes(nodesVisitedSoFar, childNodesToVisit);
        }

        // call the parent function recurrently
        visitNodesAndUpdateShortestPath(childNodesToVisit, nodesVisitedSoFar);
    }

    private static void addWarehouseNodesInToVisitQueue(Stack<GraphNode> nodesToVisitQueue, PriorityQueue<GraphNode> warehouseNodesToVisit, GraphNode nodeToVisit, Edge edge) {

        if (NodeType.WAREHOUSE.equals(edge.getDestinationNode().getType()) && !nodesToVisitQueue.contains(edge.getDestinationNode())) {
            GraphNode warehouseNodeNeighborNode = new GraphNode(edge.getDestinationNode().getType(), edge.getDestinationNode().getData());
            warehouseNodeNeighborNode.setDistanceFromStartingNode(nodeToVisit.getDistanceFromStartingNode() + edge.getWeight());
            warehouseNodeNeighborNode.setEdges(edge.getDestinationNode().getEdges());

            warehouseNodesToVisit.add(warehouseNodeNeighborNode);
        }
    }

    private void visitOtherNodes(Stack<GraphNode> nodesVisitedSoFar, PriorityQueue<GraphNode> nodesToVisitQueue) {
        GraphNode lastNodeVisited = nodesVisitedSoFar.peek();

        /**
         * We have reached the last node for this route, update the shortest route if needed.
         */
        if (lastNodeVisited.getEdges().size() == 0) {
            updateShortestPath(nodesVisitedSoFar);
        }

        /**
         * Visit the rest of nodes that are not warehouse.
         */
        for (Edge edge : lastNodeVisited.getEdges()) {
            addNonWarehouseNodesInToVisitQueue(nodesVisitedSoFar, nodesToVisitQueue, lastNodeVisited, edge);
        }
    }

    private static void addNonWarehouseNodesInToVisitQueue(Stack<GraphNode> nodesVisitedSoFar, PriorityQueue<GraphNode> nodesToVisitQueue, GraphNode nodeToVisit, Edge edge) {
        if (!NodeType.WAREHOUSE.equals(edge.getDestinationNode().getType()) && !nodesVisitedSoFar.contains(edge.getDestinationNode())) {
            GraphNode node = new GraphNode(edge.getDestinationNode().getType(), edge.getDestinationNode().getData());
            node.setDistanceFromStartingNode(nodeToVisit.getDistanceFromStartingNode() + edge.getWeight());
            node.setEdges(edge.getDestinationNode().getEdges());

            nodesToVisitQueue.add(node);
        }
    }

    private void updateShortestPath(Stack<GraphNode> proposedShortestPath) {
        if(this.shortestPath.empty()){
            backtrackAndUpdateShortestPath(proposedShortestPath);
        }else{
            compareAndUpdateShortestPath(proposedShortestPath);
        }
    }

    private void compareAndUpdateShortestPath(Stack<GraphNode> proposedShortestPath) {
        double proposedShortestPathDistance = proposedShortestPath.peek().getDistanceFromStartingNode();
        double currentShortestPathDistance = this.shortestPath.peek().getDistanceFromStartingNode();

        if(proposedShortestPathDistance < currentShortestPathDistance){
            this.shortestPath.clear();
            backtrackAndUpdateShortestPath(proposedShortestPath);
        }
    }

    private void backtrackAndUpdateShortestPath(Stack<GraphNode> newShortestPath) {
        for (GraphNode graphNode : newShortestPath) {
           this.shortestPath.push(graphNode);
        }
    }
}
