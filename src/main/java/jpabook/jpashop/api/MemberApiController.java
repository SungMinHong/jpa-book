package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreatedMemberResponse saveMember(@RequestBody @Valid final CreateMemberRequest request) {
        final var member = new Member();
        member.setName(request.getName());
        final var id = memberService.join(member);
        return new CreatedMemberResponse(id);
    }

    @Getter
    private static class CreateMemberRequest {
        @NonNull
        private String name;
    }
    
    @Getter
    @RequiredArgsConstructor
    private static class CreatedMemberResponse {
        private final long id;
    }
}
