package seedu.address.model.order;

import seedu.address.model.product.Pastry;
import seedu.address.model.product.Product;

import java.util.List;

public class CustomerOrder extends Order {
    public CustomerOrder(String phoneNumber, List<Product> items, String status) {
        super(phoneNumber, items, status);
    }

    @Override
    public String getOrderType() {
        return "Customer Order";
    }
}
