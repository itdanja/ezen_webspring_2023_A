package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service//서비스(@Component포함)
public class MemberService implements UserDetailsService , OAuth2UserService<OAuth2UserRequest , OAuth2User>   {

    // ------------------------------------------------- //
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 인증[로그인] 결과 토큰 확인
        // 2. 전달받은 토큰을 이용한 회원정보 요청
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();   ;
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest );
        // 3. 클라이언트ID 식별 [ 응답된 JSON 구조 다르기 때문에 클라이언트ID별(구글VS카카오VS네이버) 로 처리  ]
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String email = null;
        String name = null;
        // oAuth2User.getAttributes()  map< String , Object >구조
        if( registrationId.equals("kakao") ) { // 만약에 카카오 회원이면
            // 카카오 Attributes = {id=206798674 , kakao_account={profile_nickname_needs_agreement=false, profile={nickname=김현수} , email=itdanja@kakao.com} }
            Map<String , Object >  kakao_account = (Map<String , Object>)oAuth2User.getAttributes().get("kakao_account");
            Map<String , Object > profile = (Map<String , Object>) kakao_account.get("profile");
            email = (String)kakao_account.get("email");
            name = (String)profile.get("nickname");
        }else if( registrationId.equals("naver")){ // 만약에 네이버 회원이면
            // 네이버 Attributes {resultcode=00, message=success, response={id=Hq9vZhky2c775-RmPtIeB95Rz2dnBbYgKTJPAHSsvDQ, nickname=아이티단자, email=kgs2072@naver.com}}
            Map<String , Object> response = (Map<String , Object>)oAuth2User.getAttributes().get("response");
            email = (String) response.get("email");
            name = (String) response.get("nickname");
        }else if( registrationId.equals("google")){ // 만약에 구글 회원이면
            // 구글 Attributes = {sub=114044778334166488538, name=아이티단자, given_name=단자,email=kgs2072@naver.com}
            email =  (String)oAuth2User.getAttributes().get( "email" );
            name =  (String)oAuth2User.getAttributes().get( "name" );
        }

        // 인가 객체 [ OAuth2User----> MemberDto 통합Dto( 일반+oauth) ]
        MemberDto memberDto = new MemberDto();
        memberDto.setMemail( email );
        memberDto.setMname( name );
        // 1. DB 저장하기 전에 해당 이메일로 된 이메일 존재하는지 검사
        MemberEntity entity = memberEntityRepository.findByMemail( email );
        if( entity == null ){ // 첫방문
            // DB 처리 [ 첫 방문시에만 db등록  , 두번째 방문시 부터는 db수정  ]
            memberDto.setMrole("ROLE_USER"); // DB에 저장할 권한명
            entity = memberEntityRepository.save( memberDto.toEntity() );
        }else{// 두번째 방문 이상 수정 처리
            entity.setMname( name );
        }
        memberDto.set소셜회원정보( oAuth2User.getAttributes() );

        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        memberDto.set권한목록( 권한목록 );

        권한목록.add( new SimpleGrantedAuthority( entity.getMrole() ) );
        권한목록.add( new SimpleGrantedAuthority("ROLE_SNS") );

        memberDto.setMno( entity.getMno() ); // 위에 생성된 혹은 검색된 엔티티의 회원번호
        return memberDto;
    }

    // ------------------------------------------------ //
        // p. 687
        // 1. UserDetailsService 구현체
        // 2. 시큐리티 인증 처리 해주는 메소드 구현 [ loadUserByUsername ]
        // 3. loadUserByUsername 메소드는 무조건(꼭) UserDetails객체를 반환해야한다.
        // 4. UserDetails객체를 이용한 패스워드 검증과  사용자 권한을 확인 하는 동작(메소드)

    // @Autowired : 사용불가 ( 스프링 컨테이너에 등록 안된 빈(객체) 이므로 불가능 )
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 9. 시큐리티 사용시 인증정보[로그인상태] 호출
    @Transactional
    public MemberDto getMember(){
        // ! : 시큐리티 사용하기전에는 서블릿 세션을 이용한 로그인상태 저장
        // 시큐리티 사용할때는 일단 서블릿 세션이 아니고 시큐리티 저장소 이용.
        //System.out.println( "시큐리티에 저장된 세션 정보 저장소 : " + SecurityContextHolder.getContext() );
        //System.out.println( "시큐리티에 저장된 세션 정보 저장소에 저장된 인증 : " +SecurityContextHolder.getContext().getAuthentication() );
        //System.out.println( "시큐리티에 저장된 세션 정보 저장소에 저장된 인증 호출 : "+ SecurityContextHolder.getContext().getAuthentication().getPrincipal() ); // 해당 서비스를 호출한 HTTP

        // * 인증에 성공한 정보 호출 [ 세션 호출 ]
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println( o.toString() );
        // 1. 만약에 인증이 실패했을때/없을때  anonymousUser
        if( o.equals("anonymousUser")){ return null; } // 로그인 안했어..
        // 2. 인증결과에 저장된 UserDetails 로 타입 반환
        UserDetails userDetails = (UserDetails)o;
        // 3. UserDetails의 정보를 memberDto에 담아서 반환
        return MemberDto.builder().memail( userDetails.getUsername() ).build();
    }

    // 8.
    @Override
    public UserDetails loadUserByUsername( String memail ) throws UsernameNotFoundException {
        // * login페이지에서 form을 통해 전송된 아이디 받고 (패스워드 없음)
        System.out.println("loadUserByUsername username = " + memail );
        // - . p.684 인증 절차 순서
        // 1. 사용자의 아이디만으로 사용자 정보[엔티티]를 로딩 [ 불러오기 ] - p.728
        MemberEntity memberEntity =  memberEntityRepository.findByMemail( memail );
            // 1-2. 없는 아이디 이면
                //  throw : 예외처리 던지기 //  new UsernameNotFoundException() : username 없을때 사용하는 예외클래스
        if( memberEntity == null ){ throw new UsernameNotFoundException("없는 아이디입니다"); }
        // 2. 로딩[불러오기]된 사용자의 정보를 이용해서 패스워드를 검증
            // 2-1 있는 아이디 이면

        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        권한목록.add( new SimpleGrantedAuthority(memberEntity.getMrole() ) );

        MemberDto memberDto = MemberDto.builder()
                .memail( memberEntity.getMemail() )           // 찾은 사용자 정보의 아이디
                .mpassword( memberEntity.getMpassword() )        // 찾은 사용자 정보의 패스워드
                .권한목록( 권한목록  )         // 찾은 사용자 정보의 권한
                .build();
        return memberDto;
    }
    // ------------------------------------------------ //
    // Controller -> Service -> Repository 요청
    // Controller <- Service <- Repository 응답
    @Autowired
    private MemberEntityRepository memberEntityRepository ;

    // 1. [C] 회원가입
    @Transactional // 트랜잭션 : 여러개 SQL를 하나의 최소 단위 처리 [ 성공 , 실패  !! 함수내 일부 SQL만 성공x]
    public boolean postMember( MemberDto memberDto){
        // ----------- 암호화 -------------- //
            // - 입력받은 비밀번호[ memberDto.getMpassword() ]를 암호화 해서 다시 memberDto에 저장
        memberDto.setMpassword( passwordEncoder.encode( memberDto.getMpassword() ) );
            // memberDto.getMpassword() -> passwordEncoder.encode() -> memberDto.setMpassword()
        // -------------------------------- //
        // 1. dto -> entity 변경후 repository 통한 insert 후 결과 entity 받기
        MemberEntity memberEntity = memberEntityRepository.save( memberDto.toEntity() );
        // 2. insert 된 엔티티 확인후 성공/실패 유무
            // 3. 만약에 회원번호가 0보다 크면 ( auto_increment 적용 됨. )
        if( memberEntity.getMno() >= 1 ){ return  true ; }
        return false;
    }
    /*
    // 2. [R] 회원정보 호출 [ 1명 ]
    @Transactional
    public MemberDto getMember(  int mno ){
        // 1. mno[회원번호pk]를 이용한 회원 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findById(mno);
        // 2. optional클래스로 검색한 반환값 확인
        if( optionalMemberEntity.isPresent() ){ // 3. 만약에 optional 클래스 안에 엔티티가 들어있으면
            // 4. optional 클래스에서 엔티티 꺼내기
            MemberEntity memberEntity = optionalMemberEntity.get();
            // 5. entity -> dto 변환 해서 반환
            return memberEntity.toDto();
        }
        return null;
    }
     */

    // 3. [U] 회원정보 수정 [ mno , mname , mpassword ,  mphone   ]
    @Transactional
    public boolean updateMember(  MemberDto memberDto ){
        // 1. 수정할 엔티티 찾기 [ mno ]
        Optional< MemberEntity > optionalMemberEntity = memberEntityRepository.findById( memberDto.getMno() );
        // 2. optional클래스로 검색한 반환값 확인
        if( optionalMemberEntity.isPresent() ){
            // 3. 엔티티 꺼내기
            MemberEntity memberEntity = optionalMemberEntity.get();
            // 4. 엔티티 수정 [ 엔티티 객체를 수정하면 엔티티는 테이블과 매핑된 상태 이므로  DB의 정보도 같이 수정 ]
            memberEntity.setMname( memberDto.getMname() );
            memberEntity.setMpassword( memberDto.getMpassword() );
            memberEntity.setMphone( memberDto.getMphone() );
            //5. 수정 성공시
            return  true;
        }
        return false;
    }
    // 4. [D] 회원탈퇴
    @Transactional
    public boolean deleteMember( int mno ){
        // 1. 삭제할 엔티티 찾기
        Optional<MemberEntity> optionalMemberEntity = memberEntityRepository.findById(mno);
        // 2. 만약에 삭제할 엔티티가 반환/검색 존재하면
        if( optionalMemberEntity.isPresent() ){
            memberEntityRepository.deleteById( mno ); // 3.엔티티 삭제
            // 4. 삭제 성공시
            //
            // logout();  // 로그아웃 함수 재사용
            return true;
        }
        return false;
    }

    // 로그인했고 안했고 상태 저장하는곳 => request객체도 스프링 컨테이너 등록 상태.
    @Autowired
    private HttpServletRequest request;

    // 5.
    @Transactional
    public boolean login( MemberDto memberDto  ) {
        // 1. 입력받은 데이터[아이디/패스워드] 검증하기
        List<MemberEntity> memberEntities = memberEntityRepository.findAll();
            // 2. 동일한 아이디 / 비밀번호 찾기
            for( int i = 0 ; i < memberEntities.size() ; i++ ) {
                MemberEntity m = memberEntities.get(i);
                // 3. 동일한 데이터 엔티티 찾았다.
                if (m.getMemail().equals(memberDto.getMemail()) &&
                        m.getMpassword().equals(memberDto.getMpassword())) {
                    // 4. 세션 부여      // 세션 저장
                    request.getSession().setAttribute("loginDto", m.toDto());
                    return true;
                }
            }
        return false;
    }
    /*
    // 6. 로그아웃
    public boolean logout() {
        request.getSession().setAttribute( "loginDto" , null );
        return true;
    }
    // 2. [R] 회원정보 호출 [ 1명 ]
    public MemberDto getMember(){
        // 1. 세션 호출
        Object session = request.getSession().getAttribute("loginDto");
        // 2. 세션 검증
        if( session != null ){
            return (MemberDto) session;
        }
        return null;
    }
    */

    // 7. [R] [ 이메일 중복검사 ]
    public boolean getFindMemail(String memail ){
        // 1. 이메일을 이용한 엔티티 찾기...
        // [ memberEntityRepository 추상메소드 정의  ]
        boolean result = memberEntityRepository.existsByMemail( memail );
        System.out.println("memberEntity = " + result);
        return result;
    }
}


/*
    // - [예시] 임의의 아이디 와 패스워드 넣고 UserDetails객체 만들기
    UserDetails userDetails = User.builder()
            .username("qweqwe") // 아이디
            //.password("1234") // [암호화 없음] 패스워드
            .password( passwordEncoder.encode("1234") )  // [암호화 있음] 패스워드
            .authorities("ROLE_USER") // 인가(허가나 권한) 정보
            .build();

 */














