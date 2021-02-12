package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderFlatDTO {
    private final long orderId;
    private final String name;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;
    
    private final String itemName;
    private final int price;
    private final int count;

    public OrderFlatDTO(long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int price, int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }
}
