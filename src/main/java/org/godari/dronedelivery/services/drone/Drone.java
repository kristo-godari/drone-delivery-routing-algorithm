package org.godari.dronedelivery.services.drone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.godari.dronedelivery.services.client.Client;
import org.godari.dronedelivery.services.location.Location;

import java.util.ArrayList;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Drone {


    /**
     * Drone unique id
     */
    private int droneId = 0;

    /**
     * Drone name
     */
    private String droneName = "Drone 1";

    /**
     * Drone Home. Warehouse adress
     */
    private Location warehouseLocation = new Location(0, 0);

    /**
     * Battery status. Represented in percent
     * How percent battery is left.
     */
    private double batteryStatus = 100;

    /**
     * Nr. of seconds that takes for the battery to discharge from 100% to 0
     */
    private double batteryTime = 30;

    /**
     * Nr. of seconds that takes for the battery to charge from 0 to 100%
     */
    private double timeToCharge = 30;

    /**
     * Drone current position
     */
    private Location currentPosition = new Location(0, 0);

    /**
     * Current drone Altitude
     */
    private double currentAltitude;

    /**
     * Drone current client, where the drone is heading to
     * at this moment of time.
     */
    private Client currentClient;

    /**
     * A queue with all destinations that drone drones plans to go.
     */
    private ArrayList<Location> droneDestinationsQueue = new ArrayList<Location>();

    /**
     * Drone status
     * 1 = Active
     * 0 = Inactive (defected, not working)
     */
    private int droneStatus = 0;

    /**
     * Drone landing time in seconds
     */
    private double landingTime = 10;

    /**
     * Drone take off time in seconds
     */
    private double takeOffTime = 10;

    /**
     * Avarage distance speed when drone is flaying from point A to point B
     * Not included landing and take off speed. This from drone full take off, until
     * the drone is ready for landing.
     *
     * Speed represented in meters/s
     */
    private double averageSpeed = 10;

    /**
     * Total distance from current position to next destination.
     */
    private double timeToNextDestination = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return droneId == drone.droneId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(droneId);
    }
}
