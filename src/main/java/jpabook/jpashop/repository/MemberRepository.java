package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

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
