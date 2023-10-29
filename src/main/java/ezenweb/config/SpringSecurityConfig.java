package ezenweb.config;

import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 0. implementation 'org.springframework.boot:spring-boot-starter-security' // 스프링 시큐리티[인증/인가]

@Configuration // 설정 컴포넌트 주입
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        // 1-1.CSRF 해지
        http.csrf().disable();
        // 1-2 특정 CSRF 만 해지
        // http.csrf().ignoringAntMatchers("주소");

        // 2-1 로그인 설정
        http.formLogin()
                .loginPage("/login") // 로그인 으로 사용될 페이지의 매핑 URL
                .loginProcessingUrl("/member/login") // 로그인을 처리할 매핑 URL
                .defaultSuccessUrl("/") // 로그인 성공했을때 이동할 매핑 URL
                .failureUrl("/login")// 로그인 실패했을때 이동할 매핑 URL
                .usernameParameter("memail") // 로그인시 사용될 계정 아이디 의 필드명
                .passwordParameter("mpassword");// 로그인시 사용될 계정 패스워드 의 필드명
        // 2-1 로그아웃  설정
        http.logout()
                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout") ) // 로그아웃 처리 를 요청할 매핑 URL
                .logoutSuccessUrl("/")//로그아웃 성공했을때 이동할 매핑 URL
                .invalidateHttpSession( true ); // 세션 초기화x
        // 3-1
        http.authorizeHttpRequests() // 1. 인증 http 요청들 [ 인증-로그인된 ] http 조건들
                .antMatchers("/info")
                .hasRole("USER")// 게시물쓰기는 회원[MEMBER]만 가능
            .antMatchers("/**")
                .permitAll(); // 접근 제한 없음 [ 모든 유저가 사용가능 ]

    }

    @Autowired
    private MemberService memberService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.userDetailsService( memberService ).passwordEncoder( new BCryptPasswordEncoder() );
    }

}
