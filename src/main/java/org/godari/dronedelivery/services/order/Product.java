package org.godari.dronedelivery.services.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private int productId = 0;

    private int merchantId = 0;

    private int wareHouseId = 0;

    private int orderId = 0;

    private String externalIdentifierCode = null;

    private String name = null;

    private double weight = 0;

    private int quantity = 0;
}
