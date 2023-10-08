package org.godari.dronedelivery.services.order;

import org.godari.dronedelivery.services.client.Client;

import java.util.List;

public interface OrderService {

    List<Order> getOrdersFor(Client client);
}
