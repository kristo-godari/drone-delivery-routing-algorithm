package org.godari.dronedelivery.services.algorithm.dto;

import org.godari.dronedelivery.services.algorithm.dto.graph.GraphNode;
import org.godari.dronedelivery.services.algorithm.dto.graph.NodeType;

public class WarehouseNode extends GraphNode {

    public WarehouseNode(Object data) {
        super(NodeType.WAREHOUSE, data);
    }

    public WarehouseNode(GraphNode graphNode){
        super(graphNode.getType(), graphNode.getData(), graphNode.getDistanceFromStartingNode());
    }
}
