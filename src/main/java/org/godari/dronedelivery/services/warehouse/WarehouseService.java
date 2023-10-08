package org.godari.dronedelivery.services.warehouse;

import org.godari.dronedelivery.services.order.Order;

import java.util.List;

public interface WarehouseService {

    List<Warehouse> getWarehousesForOrder(Order order);
}
