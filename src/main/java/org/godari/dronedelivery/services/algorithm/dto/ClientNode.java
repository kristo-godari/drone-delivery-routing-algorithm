package org.godari.dronedelivery.services.algorithm.dto;

import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;
import org.godari.dronedelivery.services.algorithm.dto.graph.NodeType;

public class ClientNode extends GraphNode {

    public ClientNode(Object data) {
        super(NodeType.CLIENT, data);
    }

    public ClientNode(GraphNode graphNode){
        super(graphNode.getType(), graphNode.getData(), graphNode.getDistanceFromStartingNode());
    }
}
