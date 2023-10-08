package org.godari.dronedelivery.services.location;

public interface LocationService {

    /**
     * Calculates the distance in meters between two location points.
     */
    public double calculateDistance(Location startingPoint, Location destinationPoint);
}
