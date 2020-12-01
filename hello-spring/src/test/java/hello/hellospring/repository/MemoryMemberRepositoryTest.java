package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //한꺼번에 테스트시에는 순서대로 되는게 아니라 동시에 돌아갈수도 있기때문에 에러발생 가능성 좀 있음
    //테스트 메서드 하나가 끝날때 마다 repository 를 깔끔하게 clear 해주는 작업 필요함.
    //@AfterEach 는 메서드가 끝날때마다 동작을 하게 하는것. 콜백메서드라고 보면됨..
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }


    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        //가져온 result 랑 member 랑 같으면 true 가 뜬다.
        System.out.println("(result == member) = " + (result == member));
        System.out.println("result = " + (result == member));

        //글자로 계속 볼수는 없으니 제공되는 assert 기능 () 안의 것들이 같은 것인지 확인하는 것.
        //( A, B) A : 기대하는것 expected , B : actual
        //딱히 콘솔에 출력되는 것 없이 정상적으로 실행된다면 둘이 같다고 봄. 다르다면 AssertionFailedError 남.
        //Assertions.assertEquals(member, result);

        //요즘 쓰는 것
        Assertions.assertThat(member).isEqualTo(result);
    }


    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
