package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

//비즈니스 로직이 돌아가는 Service
public class MemberService {

    //회원서비스를 만들려면 회원repository 가 필요함
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    //회원가입입
   public Long join(Member member){
       //같은이름의 중복회원 막기
       //새로 들어온 member 의 이름이 repository 에 이미 있는지 검색해서 result 에.
       Optional<Member> result = memberRepository.findByName(member.getName());
        //result 가 null 값이 아니라면 이 로직을 수행하라. Optional 이기 때문에 가능한것. Optional 로 한번 감쌌기 때문에
       //Optional 안에 member 객체가 있는것. 그래서 요즘은 null 일 가능성이 있으면 Optional 로 한번 감싸서 반환하게 함.
       result.ifPresent(m -> {
           throw new IllegalStateException("이미 존재하는 회원입니다.");
       });

       //참고!! 이렇게 해도 가능한것임.
       memberRepository.findByName(member.getName()).ifPresent(m -> {
           throw new IllegalStateException("이미 존재하는 회원입니다.");
       });



       //저장
       memberRepository.save(member);
       //임의로 회원가입시에 아이디를 반환해주기로 함
       return member.getId();
   }


   //전체회원 조회
    public List<Member> findMembers(){
       return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
       return memberRepository.findById(memberId);
    }



}
