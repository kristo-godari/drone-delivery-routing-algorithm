package org.godari.dronedelivery.services.warehouse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.godari.dronedelivery.services.location.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    private Integer warehouseId;
    private String warehouseName;
    private Location location;
}
