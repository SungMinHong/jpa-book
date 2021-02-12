package jpabook.jpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders();

        result.forEach(o ->
        {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllV5() {
        List<OrderQueryDto> result = findOrders();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "SELECT new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " FROM OrderItem AS oi" +
                        " JOIN oi.item AS i" +
                        " WHERE oi.order.id IN :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());
    }

    private List<OrderItemQueryDto> findOrderItems(long orderId) {
        return em.createQuery(
                "SELECT new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " FROM OrderItem AS oi" +
                        " JOIN oi.item AS i" +
                        " WHERE oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "SELECT new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) FROM Order AS o" +
                        " JOIN o.member AS m" +
                        " JOIN o.delivery AS d", OrderQueryDto.class
        ).getResultList();
    }

    public List<OrderFlatDTO> findAllV6_flat() {
        return em.createQuery(
                "SELECT new jpabook.jpashop.repository.order.query.OrderFlatDTO(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count) " +
                        " FROM Order AS o" +
                        " JOIN o.member AS m" +
                        " JOIN o.delivery AS d" +
                        " JOIN o.orderItems AS oi" +
                        " JOIN oi.item AS i", OrderFlatDTO.class).getResultList();
    }
}
