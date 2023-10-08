package org.godari.dronedelivery.services.algorithm.dto;

import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;
import org.godari.dronedelivery.services.algorithm.dto.graph.NodeType;

public class DroneNode extends GraphNode {

    public DroneNode(Object data) {
        super(NodeType.DRONE, data);
    }

    public DroneNode(GraphNode graphNode){
        super(graphNode.getType(), graphNode.getData(), graphNode.getDistanceFromStartingNode());
    }
}
