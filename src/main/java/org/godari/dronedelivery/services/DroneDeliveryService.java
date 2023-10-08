package org.godari.dronedelivery.services;

import org.godari.dronedelivery.services.drone.Drone;
import org.godari.dronedelivery.services.order.Order;

public interface DroneDeliveryService {

    Drone findFastestDroneFor(Order order);
}
