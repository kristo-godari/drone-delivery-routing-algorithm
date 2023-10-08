package org.godari.dronedelivery.services;

import org.godari.dronedelivery.services.algorithm.ShortestPathAlgorithmService;
import org.godari.dronedelivery.services.client.Client;
import org.godari.dronedelivery.services.drone.Drone;
import org.godari.dronedelivery.services.drone.DroneService;
import org.godari.dronedelivery.services.location.Location;
import org.godari.dronedelivery.services.location.LocationService;
import org.godari.dronedelivery.services.order.Order;
import org.godari.dronedelivery.services.warehouse.Warehouse;
import org.godari.dronedelivery.services.warehouse.WarehouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class DroneDeliveryServiceTest {

    DroneDeliveryServiceImpl droneDeliveryService;
    ShortestPathAlgorithmService shortestPathAlgorithmService = new ShortestPathAlgorithmService();
    WarehouseService warehouseService = Mockito.mock(WarehouseService.class);
    DroneService droneService = Mockito.mock(DroneService.class);
    LocationService locationService = Mockito.mock(LocationService.class);


    @BeforeEach
    public void setup(){
        droneDeliveryService = new DroneDeliveryServiceImpl(
                shortestPathAlgorithmService, warehouseService, droneService, locationService);
    }

    @Test
    public void testAlgorithmCorrectness2() {

        // given
        Drone expectedFastestDrone = getDrone(2);
        Client client4 = getClient(4);
        Order order4 = getOrder(client4);

        Mockito.when(warehouseService.getWarehousesForOrder(eq(order4)))
                .thenReturn(getWarehousesList());

        Mockito.when(droneService.getOperatingDrones())
                .thenReturn(getDronesList());

        // distances are based on this example: https://github.com/kristo-godari/drone-delivery-routing-algorithm/blob/master/docs/graph.png
        Mockito.when(locationService.calculateDistance(any(), any()))

                // distance between drone1 -> client1
                .thenReturn(2.0)
                // distance between drone2 -> client2
                .thenReturn(1.0)
                // distance between drone3 -> client3
                .thenReturn(1.0)

                // distance between warehouse1 -> client1
                .thenReturn(5.0)
                // distance between warehouse1 -> client2
                .thenReturn(9.0)
                // distance between warehouse1 -> client3
                .thenReturn(11.0)

                // distance between warehouse2 -> client1
                .thenReturn(7.0)
                // distance between warehouse2 -> client2
                .thenReturn(3.0)
                // distance between warehouse2 -> client3
                .thenReturn(4.0)

                // distance between warehouse3 -> client1
                .thenReturn(9.0)
                // distance between warehouse3 -> client2
                .thenReturn(8.0)
                // distance between warehouse3 -> client3
                .thenReturn(6.0)

                // distance between warehouse1 -> warehouse2
                .thenReturn(2.0)
                // distance between warehouse1 -> warehouse3
                .thenReturn(4.0)

                // distance between warehouse2 -> warehouse1
                .thenReturn(2.0)
                // distance between warehouse2 -> warehouse3
                .thenReturn(2.0)

                // distance between warehouse3 -> warehouse1
                .thenReturn(4.0)
                // distance between warehouse3 -> warehouse2
                .thenReturn(2.0)

                // distance between client4 -> warehouse1
                .thenReturn(4.0)
                // distance between client4 -> warehouse2
                .thenReturn(3.0)
                // distance between client4 -> warehouse3
                .thenReturn(6.0);


        // when
        Drone fastestDrone = droneDeliveryService.findFastestDroneFor(order4);

        // then
        assertEquals(expectedFastestDrone, fastestDrone);
    }

    private static List<Warehouse> getWarehousesList(){

        Warehouse warehouse1 = getWarehouse(1);
        Warehouse warehouse2 = getWarehouse(1);
        Warehouse warehouse3 = getWarehouse(1);

        return List.of(warehouse1, warehouse2, warehouse3);
    }

    private static List<Drone> getDronesList(){
        Drone drone1 = getDrone(1);
        drone1.setCurrentClient(getClient(1));

        Drone drone2 = getDrone(2);
        drone2.setCurrentClient(getClient(2));

        Drone drone3 = getDrone(3);
        drone3.setCurrentClient(getClient(3));

        return List.of(drone1, drone2, drone3);
    }

    private static Warehouse getWarehouse(Integer warehouseId){
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName("W" + warehouseId);
        warehouse.setWarehouseId(warehouseId);

        return warehouse;
    }

    private static Drone getDrone(Integer droneId){
        Drone drone = new Drone();
        drone.setDroneId(droneId);
        return drone;
    }

    private static Order getOrder(Client client){
        Order order = new Order();
        order.setClient(client);
        return order;
    }

    private static Client getClient(Integer clientId) {
        Client client4 = new Client();
        client4.setClientId(clientId);
        client4.setLocation(new Location(0,0));

        return client4;
    }
}
