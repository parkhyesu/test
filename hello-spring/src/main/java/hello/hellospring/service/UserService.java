package hello.hellospring.service;

import hello.hellospring.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//로그인처리, 즉 인증을 위해서는 UserDetailService 를 통해 필요한 정보를 가져오는데, 예제에서는 서비스 클래스에서 처리할 것.
//서비스 클래스(UserService) 에서 UserDetailsService 인터페이스를 implements 하여 loadUserByUsername() 메서드 구현하면 된다.

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;



    //loadUserByUsername : 상세 정보를 조회하는 메서드이다. 사용자의 계정정보와 권한을 갖는 UserDetails 인터페이스를 반환한다.
    //매개변수는 로그인 시 입력한 아이디인데, 엔티티의 PK 를 뜻하는게 아니고 유저를 식별할 수 있는 어떤 값을 의미한다.
    //Spring Security 에서는 username 라는 이름으로 사용한다.
    //form 에서 name = "username"  으로 요청해야한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntityWrapper = userRepository.findByEmail(username);
        UserEntity userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if(("admin.example.com").equals(username)){
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }else{
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue));
        }

        return new User(userEntity.getEmail, userEntity.getPassword(), authorities);





    }
}
