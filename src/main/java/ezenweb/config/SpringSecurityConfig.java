package ezenweb.config;

import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    // p. 685 : configure(HttpSecurity http) : HTTP 관련된 보안 담당하는 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.formLogin()                      // 1. 시큐리티 로그인 사용 [ form 전송 ]
                .loginPage("/login")          // 2. 시큐리티 로그인으로 사용할 VIEW페이지의 HTTP 주소 정의
                .loginProcessingUrl("/member/login") // 3.  시큐리티 로그인(인증)처리 요청시 사용할 HTTP 주소 정의
                // 시큐리티 사용하기전에 MemberController 해서 정의한 로그인/로그아웃 함수를 없애기
                // HTTP '/member/login' POST 요청시 ---> MemberService의 loadUserByUsername 로 이동.
                .defaultSuccessUrl("/")         // 4. 만약에 로그인 성공시 이동할 HTTP 주소
                .failureUrl("/login") // 5. 만약에 로그인 실패시 이동할 HTTP 주소
                .usernameParameter("memail")        // 6. 로그인시 입력받은 아이디의 변수명 정의
                .passwordParameter("mpassword");    // 7. 로그인시 입력받은 비밀번호의 변수명 정의
        http.csrf().disable(); // ---- 모든 HTTP POST/PUT 에서 csrf 사용안함.
        // 특정 HTTP에서만 CSRF 사용안함 [ POST,PUT ]
        //http.csrf().ignoringAntMatchers("/member/post"); // controller 매핑 주소
        //http.csrf().ignoringAntMatchers("/member/login"); //  매핑 주소
    }
    // p.689 : configure( AuthenticationManagerBuilder auth) : 웹 시큐리티 인증 담당하는 메소드
    @Autowired
    private MemberService memberService;

    @Override
    protected void configure( AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
        // auth.userDetailsService( userDetailsService 구현체  ).passwordEncoder( 사용할 암호화 객체 )
    }
}

// -- 시큐리티 관련 메소드 커스텀 하기
    // 1. 해당 클래스에 상속받기 extends WebSecurityConfigurerAdapter
    // 2. 커스텀 할 메소드 오버라이딩 하기
        // 1. configure(HttpSecurity http)

