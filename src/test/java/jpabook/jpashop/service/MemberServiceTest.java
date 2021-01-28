package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService service;

    @Autowired
    private MemberRepository repository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hong");

        // when
        Long savedId = service.join(member);

        // then
        assertEquals(member, repository.findOne(savedId));
    }

    @Test
    void 종복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("hong");
        Member member2 = new Member();
        member2.setName("hong");

        // when
        service.join(member1);
        final var thrown = assertThatThrownBy(() -> service.join(member2));

        // then
        thrown.isExactlyInstanceOf(IllegalStateException.class)
                .hasMessage("이미 존재하는 회원입니다.");
    }
}