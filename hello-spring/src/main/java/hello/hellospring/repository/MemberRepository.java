package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

//회원을 저장하는것은 인터페이스로. 이유 : 아직 데이터 저장소가 선정되지않았기 때문에
//나중에 구현 클래스를 바꿔끼우기 위해서 이건 인터페이스로
public interface MemberRepository {
    //회원저장하면 저장된 회원 반환
    Member save(Member member);
    //아이디로 회원찾기
    // Optional : 자바 8에 있는 기능.찾았는데 없으면 null 이 반환되니까. 요즘은 Optional 로 감싸서 어쩌고함.
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
