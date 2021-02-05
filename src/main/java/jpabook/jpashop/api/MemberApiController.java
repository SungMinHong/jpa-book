package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("api/v1/members")
    public Result<List<MemberDTO>> getMembers() {
        final var members = memberService.findMembers().stream()
                .map(member -> new MemberDTO(member.getName()))
                .collect(Collectors.toList());
        return new Result<>(members);
    }

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

    @Getter
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Getter
    @RequiredArgsConstructor
    public static class MemberDTO {
        private final String name;
    }

}
