package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    
    @Test
    @Transactional
    @Rollback(value = false)
    void testMember() {
        // given
        Member member = new Member();
        member.setName("memberA");
        
        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);
        
        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(savedId);
    }
}