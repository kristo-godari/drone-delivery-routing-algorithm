package org.godari.dronedelivery.services.warehouse;

import org.godari.dronedelivery.services.order.Order;

import java.util.List;

/**
 * This is a dummy implementation only for example and testing purpose.
 * Replace this with your own implementation.
 */
public class DummyWarehouseService implements WarehouseService{
    @Override
    public List<Warehouse> getWarehousesForOrder(Order order) {
        return null;
    }
}
