package org.godari.dronedelivery.services.client;

import lombok.Data;
import org.godari.dronedelivery.services.location.Location;

@Data
public class Client {

    private Integer clientId;
    private String firstName;
    private String lastName;
    private Location location;

}
