package hello.hellospring.config;

//Spring Security 는 FilterChainProxy 라는 이름으로 내부에 여러 Filter 들이 동작하고 있다.
//그래서 간단한 구현단계에서는 별도의 로직을 작성하지 않아도 설정만으로 로그인/로그아웃 등의 처리가 가능하다.
//설정은 WebSecurityConfigureAdapter 라는 클래스를 상속받은 클래스에서 메서드를 오버라이딩하여 조정할 수 있다.

import hello.hellospring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//전에 xml (security-context.xml 로 처리하던 것 여기에 하는듯)

//@Configuration 클래스에 @EnableWebSecurity 어노테이션을 추가하여 Spring Security 설정할 클래스라고 정의
//WebSecurityConfigurerAdapter 클래스 : WebSecurityConfigurerAdapter 인스턴스를 편리하게 생성하기 위한 클래스
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;


    //BCryptPasswordEncoder 는 Spring Security 에서 제공하는 비밀번호 암호화 객체이다.
    //Service 에서 비밀번호를 암호화 할 수 있도록 Bean 으로 등록한다
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //configure() 메소드를 오버라이딩 하여 Security 설정을 잡아준다.

    //WebSecurity 는 FilterChainProxy 를 생성하는 필터이다.
    @Override
    public void configure(WebSecurity web) throws Exception {
       //여기에 적은 디렉토리의 하위 파일 목록은 인증 무시한다는 뜻(= 항상 통과)
        web.ignoring().antMatchers("");
    }

    //HttpSecurity 를 통해 HTTP 요청에 대한 웹 기반 보안을 구성할 수 있다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //페이지 권한 설정
        http.authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/user/myinfo").hasRole("MEMBER")
                    .antMatchers("/**").permitAll()
                .and()  //로그인 설정
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/user/login/result")
                    .permitAll()
                .and()  //로그아웃 설정
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/user/logout/result")
                    .invalidateHttpSession(true)
                .and()
                    //403예외처리 핸들링
                    .exceptionHandling().accessDeniedPage("/user/denied");
    }

    //Spring Security 에서 모든 인증은 AuthenticationManager 를 통해 이루어 지며 AuthenticationManager 를 생성하기 위해서는
    //AuthenticationManagerBuilder 를 사용한다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //로그인처리, 즉 인증을 위해서는 UserDetailService 를 통해 필요한 정보를 가져오는데, 예제에서는 서비스 클래스에서 처리할 것.
        //서비스 클래스(UserService) 에서 UserDetailsService 인터페이스를 implements 하여 loadUserByUsername() 메서드 구현하면 된다.
        //비밀번호 암호화를 위해 passwordEncoder 를 사용한다.
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
