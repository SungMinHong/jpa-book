package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/api/v1/members/{memberId}")
    public UpdateMemberResponse updateMember(@PathVariable("memberId") Long id,
                                             @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        final var member = memberService.findOne(id);
        return new UpdateMemberResponse(member.getId(), member.getName());
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

    @Getter
    private static class UpdateMemberRequest {
        private String name;
    }

    @Getter
    @RequiredArgsConstructor
    private static class UpdateMemberResponse {
        private final Long id;
        private final String name;
    }
}
