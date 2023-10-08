package org.godari.dronedelivery.services.location;

public class LocationServiceImpl implements LocationService {

    private static final double START_ALTITUDE = 100;
    private static final double END_ALTITUDE = 100;

    @Override
    public double calculateDistance(Location startingPoint, Location destinationPoint) {

        return calculateHarvesineDistance(
                startingPoint.getLatitude(),
                destinationPoint.getLatitude(),
                startingPoint.getLatitude(),
                destinationPoint.getLongitude(),
                START_ALTITUDE,
                END_ALTITUDE
        );
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    private double calculateHarvesineDistance(
            double lat1,
            double lat2,
            double lon1,
            double lon2,
            double el1,
            double el2
    ) {

        final int R = 6371; // Radius of the earth

        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
