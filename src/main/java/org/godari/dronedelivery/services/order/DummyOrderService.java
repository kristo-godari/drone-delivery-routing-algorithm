package org.godari.dronedelivery.services.order;

import org.godari.dronedelivery.services.client.Client;

import java.util.List;

/**
 * This is a dummy implementation only for example and testing purpose.
 * Replace this with your own implementation.
 */
public class DummyOrderService implements OrderService{

    @Override
    public List<Order> getOrdersFor(Client client) {
        return null;
    }
}
