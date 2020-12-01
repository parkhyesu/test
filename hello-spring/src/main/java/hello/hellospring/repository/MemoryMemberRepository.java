package hello.hellospring.repository;

import com.sun.org.apache.bcel.internal.generic.ARETURN;
import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    //save 메소드 실행시 어디에 저장을 해야하니까 일단 Map 으로
    //key 는 회원 아이디로 할거니까 Long 으로 값은 member
    private static Map<Long, Member> store = new HashMap<>();

    //
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        //(save 될때 sequence 값을 하나 올려주고)
        //store 에 넣기 전에 ID 값 세팅(name 은 회원이 직접 입력한 정보가 넘어오고
        // ID 는 저장시에 시스템상으로 넣어줄거니까 여기서 처리
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //store 에서 꺼내면 됨. 해당 회원이 없을 경우에 Null 값이 넘어오는 것 방지하기 위해서
        //Optional.ofNullable 로 한번 감싼다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
