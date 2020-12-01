package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();

    @Test
    void 회원가입() {
        //given(이런 상황이 주어졌는데)
        Member member = new Member();
        member.setName("hello");

        //when(이거를 실행했을때)
        Long saveId = memberService.join(member);

        //then(결과가 이게 나와야해)
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void dMembers() {
    }

    @Test
    void findOne() {
    }
}