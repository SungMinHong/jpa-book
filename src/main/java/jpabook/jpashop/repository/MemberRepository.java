package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(final Member member) {
        em.persist(member);
    }

    public Member findOne(final Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member AS m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(final String name) {
        return em.createQuery("SELECT m FROM Member AS m WHERE m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
