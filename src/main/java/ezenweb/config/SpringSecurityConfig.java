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
        http.formLogin();
        http.csrf().disable(); // ---- csrf 사용안함.
    }
    // p.689 : configure( AuthenticationManagerBuilder auth) : 웹 시큐리티 인증 담당하는 메소드
    @Autowired
    private MemberService memberService;

    @Override
    protected void configure( AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.userDetailsService( memberService )
                .passwordEncoder( new BCryptPasswordEncoder() );
        // auth.userDetailsService( userDetailsService 구현체  ).passwordEncoder( 사용할 암호화 객체 )
    }
}

// -- 시큐리티 관련 메소드 커스텀 하기
    // 1. 해당 클래스에 상속받기 extends WebSecurityConfigurerAdapter
    // 2. 커스텀 할 메소드 오버라이딩 하기
        // 1. configure(HttpSecurity http)

