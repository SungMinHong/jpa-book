package jpabook.jpashop.transaction_test;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class OuterService {
    private final InnerService innerService;
    private final MemberRepository memberRepository;
    
    public void call1() {
        Member member = new Member();
        member.setName("OuterService");
        memberRepository.save(member);

        try {
            innerService.call2();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
