package jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemQueryDto {
    @JsonIgnore
    private final long orderId;
    private final String itemName;
    private final int price;
    private final int count;

    public OrderItemQueryDto(long orderId, String itemName, int price, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }
}
