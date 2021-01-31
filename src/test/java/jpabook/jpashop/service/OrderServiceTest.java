package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 상품주문() {
        // given
        final Member member = createMember();

        final int stock = 10;
        final int orderCount = 2;
        final Book item = createBook("JPA", 10000, stock);

        // when
        final Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // then
        final Order order = orderRepository.findOne(orderId);
        assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, order.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, order.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", stock - orderCount, item.getStockQuantity());
    }

    @Test
    void 상품주문_재고수량초과() {
        // given
        final Member member = createMember();
        final Book item = createBook("JPA", 10000, 10);

        final int orderCount = 11;
        
        // when
        final var thrown = assertThatThrownBy(() -> orderService.order(member.getId(), item.getId(), orderCount));

        // then
        thrown.isExactlyInstanceOf(NotEnoughStockException.class)
                .hasMessage("need more stock");
    }

    @Test
    void 주문취소() {
        // given
        final Member member = createMember();
        final Book item = createBook("JPA", 10000, 10);

        final int orderCount = 2;
        final Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        
        // when
        orderService.cancelOrder(orderId);
        
        // then
        final Order order = orderRepository.findOne(orderId);
        assertEquals("주문 취소시 상태는 CANCEL 이다.", OrderStatus.CANCEL, order.getStatus());
        assertEquals("주문이 취소된 상품은 재고가 취소한 수 만큼 증가해야 한다.", 10, item.getStockQuantity());
    }

    private Member createMember() {
        final Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "길동길", "101-122"));
        em.persist(member);
        return member;
    }

    private Book createBook(final String name, final int price, final int stock) {
        final Book book = new Book();
        book.setName(name + " 도서");
        book.setPrice(price);
        book.setStockQuantity(stock);
        em.persist(book);
        return book;
    }

}