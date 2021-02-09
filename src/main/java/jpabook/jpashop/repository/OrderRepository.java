package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(final Order order) {
        em.persist(order);
    }

    public Order findOne(final Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(final OrderSearch orderSearch) {
        return findAllByString(orderSearch);
    }

    // TODO:: Querydsl 로 해결해야 함. 
    public List<Order> findAllByString(OrderSearch orderSearch) {
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "SELECT o FROM Order AS o" +
                        " JOIN FETCH o.member AS m" +
                        " JOIN FETCH o.delivery AS d"
                , Order.class
        ).getResultList();
    }

    // 굳이 쓰려면 이 곳에 두지말고 DTO용 쿼리 Repository를 만들고 거기에 모아두어야 함.
    public List<SimpleOrderQueryDTO> findOrderDtos() {
        return em.createQuery(
                "SELECT new jpabook.jpashop.repository.SimpleOrderQueryDTO(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " FROM Order AS o" +
                        " JOIN o.member AS m" +
                        " JOIN o.delivery AS d"
                , SimpleOrderQueryDTO.class
        ).getResultList();
    }

    // Order 중복을 막기 위해서는 distinct 키워드를 반드시 써야한다. 
    // sql의 distinct와는 다르다. sql에서는 모든 칼럼의 값이 동일해야 중복으로 판별하고 제거한다.
    // jpa 에서는 distinct가 있었다면 application 에서 id가 중복되는 경우 합쳐주는 작업을 한다.

    // 주의할 점
    // 1. 페이징을 하면 안된다. 쓴다면 WARN이 뜨면서 실제로 페이징 쿼리는 빠지고 애플리케이션에서 페이지네이션을 해버린다. 결국 WAS 메모리 사용양을 올린다. 전체가 100만개라면 그대로 was로 들고와서 정렬을 한다. 
    // 결론적으로 oneToMany 관계를 조회할 때는 페이징 절대 쓰지말자. 시간 폭탄이다.
    // 2. 컬렉션 패치조인은 1개만 사용할 수 있다. 컬렉션 둘 이상에 패치 조인을 사용하면 안된다. 데이터가 부정합하게 조회될 수 있다.
    public List<Order> findALlWithItem() {
        return em.createQuery(
                "SELECT DISTINCT o FROM Order AS o" +
                        " join fetch o.member AS m" +
                        " join fetch o.delivery AS d" +
                        " join fetch o.orderItems AS oi" +
                        " join fetch oi.item AS i",
                Order.class
        ).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(int offset, int limit) {
        return em.createQuery(
                "SELECT o FROM Order AS o" +
                        " JOIN FETCH o.member AS m" +
                        " JOIN FETCH o.delivery AS d"
                , Order.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
