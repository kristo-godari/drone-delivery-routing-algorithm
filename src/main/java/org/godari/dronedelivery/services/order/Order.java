package org.godari.dronedelivery.services.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.godari.dronedelivery.services.client.Client;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    public static final String ORDER_STATUS_PENDING = "PENDING";
    public static final String ORDER_STATUS_PROGRESS = "IN_PROGRESS";
    public static final String ORDER_STATUS_COMPLETE = "COMPLETE";
    public static final String ORDER_STATUS_CANCELED = "CANCELED";
    public static final String ORDER_STATUS_ERROR = "ERROR";

    private int orderId;

    private int merchantId;

    private int warehouseId;

    private String externalReference;

    private String externalDate;

    private String status;

    private Client client;
    private List<Product> products;


    public List<Integer> getProductIds() {

        List<Integer> productIds = new ArrayList<Integer>();
        for (int i = 0; i < this.products.size(); i++) {
            productIds.add(this.products.get(i).getProductId());
        }

        return productIds;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
