package ezenweb.config;

import ezenweb.controller.AuthSuccessFailHandler;
import ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthSuccessFailHandler authSuccessFailHandler;


    // p. 685 : configure(HttpSecurity http) : HTTP 관련된 보안 담당하는 메소드
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        // 0. 인증(로그인)된 인가(권한/허가) 통해 페이지 접근 제한 [ p.693 ] UserDetails 내 authorities 권한명 일치
        http.authorizeHttpRequests() // 1. 인증된 권한에 따른 HTTP 요청 제한.
                .antMatchers("/info").hasRole("USER") // 인증된 권한중에 ROLE_USER 이면 해당 HTTP 허용
                .antMatchers("/**").permitAll(); // 모든 페이지는 권한 모두 허용
        // 1. 인증(로그인) 커스텀
        http.formLogin()                      // 1. 시큐리티 로그인 사용 [ form 전송 ]
                .loginPage("/login")          // 2. 시큐리티 로그인으로 사용할 VIEW페이지의 HTTP 주소 정의
                .loginProcessingUrl("/member/login") // 3.  시큐리티 로그인(인증)처리 요청시 사용할 HTTP 주소 정의
                // 시큐리티 사용하기전에 MemberController 해서 정의한 로그인/로그아웃 함수를 없애기
                // HTTP '/member/login' POST 요청시 ---> MemberService의 loadUserByUsername 로 이동.
                //.defaultSuccessUrl("/")         // 4. 만약에 로그인 성공시 이동할 HTTP 주소
                //.failureUrl("/login") // 5. 만약에 로그인 실패시 이동할 HTTP 주소
                .usernameParameter("memail")        // 6. 로그인시 입력받은 아이디의 변수명 정의
                .passwordParameter("mpassword")    // 7. 로그인시 입력받은 비밀번호의 변수명 정의
                .failureHandler( authSuccessFailHandler )
                .successHandler( authSuccessFailHandler );

        // 2. 로그아웃 커스텀 [ 시큐리티 사용전에 CONTROLLER , SERVICE 에 구현한 logout 관련 메소드 제거 ]
        http.logout()                    // 1. 로그인(인증) 로그아웃 처리
                // 2. 로그아웃 처리할 HTTP 주소 정의
                .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout") )
                // 3. 로그아웃 성공했을때 이동할 HTTP 주소 [ "/" : 메인페이지로 이동 ]
                .logoutSuccessUrl("/")
                // 4. 로그아웃 할때 http세션 모두 초기화  [ true : 초기화O / false : 초기화X ]
                .invalidateHttpSession( true );

        // 3. CSRF 커스텀
        http.csrf().disable(); // ---- 모든 HTTP POST/PUT 에서 csrf 사용안함.
        // 특정 HTTP에서만 CSRF 사용안함 [ POST,PUT ]
        //http.csrf().ignoringAntMatchers("/member/post"); // controller 매핑 주소
        //http.csrf().ignoringAntMatchers("/member/login"); //  매핑 주소

        http.oauth2Login() // 소셜 로그인 설정     /oauth2/authorization/클라이언트이름
                .defaultSuccessUrl("/") // 로그인 성공시 이동할 매핑 URL
                .loginPage("/login")
                .userInfoEndpoint()
                .userService( memberService ); //  oauth2 서비스를 처리할 서비스 구현
    }
    // p.689 : configure( AuthenticationManagerBuilder auth) : 웹 시큐리티 인증 담당하는 메소드

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

