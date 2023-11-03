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
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service//서비스(@Component포함)
public class MemberService implements
        UserDetailsService,  // 일반회원 서비스 : loadUserByUsername 메소드 구현 [ 로그인처리하는메소드 ]
        OAuth2UserService<OAuth2UserRequest , OAuth2User > // Oauth2 회원 서비스 : loadUser 메소드 구현 [oauth2로그인된 회원정보를 받는 메소드]
{
    // ========================================================= 2.Oauth2 회원 ============================================== //
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 로그인을 성공한 oauth2 사용자정보(동의항목)의 정보 호출
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser( userRequest );     System.out.println("oAuth2User = " + oAuth2User);
        // 2. 인증결과( 카카오 , 네이버 , 구글 )
        // 2-1 인증한 소셜 서비스 아이디( 각 회사명 ) 찾기
        String registrationId = userRequest.getClientRegistration().getRegistrationId();   System.out.println("registrationId = " + registrationId);
        String memail = null; String mname = null;
        // 2-2 카카오 이면
        if ( registrationId.equals("kakao") ) {
            Map<String , Object > kakao_account = (Map<String , Object >)oAuth2User.getAttributes().get("kakao_account");
            memail = kakao_account.get("email").toString();
            Map<String , Object > profile =  (Map<String , Object >)kakao_account.get("profile");
            mname = profile.get("nickname").toString();
        }
        // 2-2 네이버 이면
        if (registrationId.equals("naver") ) {
            Map<String , Object > response = (Map<String , Object >) oAuth2User.getAttributes().get("response");
            memail = response.get("email").toString();
            mname = response.get("nickname").toString();
        }
        // 2-2 구글 이면
        if (registrationId.equals("google") ) {
            memail = oAuth2User.getAttributes().get("email").toString();
            mname = oAuth2User.getAttributes().get("name").toString();
        }
        // 3 : 일반회원(UserDetails) + OAUTH2(OAuth2User) 통합회원 = DTO 같이 쓰기
            // 2-1 권한 목록에 추가
        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        권한목록.add(
                new SimpleGrantedAuthority("ROLE_"+registrationId )
        );
            // 2-2 DTO 만들기
        MemberDto memberDto = MemberDto.builder()
                // oauth2는 패스워드를 알수없다..
                .memail( memail )
                .mname( mname )
                .권한목록( 권한목록 )
                .소셜회원정보( oAuth2User.getAttributes() )
                .build();
            // 2-3 DB 처리
                // 만약에 처음 접속한 OAUTH2 회원이면 권한을 추가하고 DB처리
        if( !memberEntityRepository.existsByMemail( memail ) ){  // 해당 이메일이 db에 없으면
            memberDto.setMrole("ROLE_USER");
            // 임의 패스워드[ oauth2 패스워드가 필요없다-무조건 , db null 피하기 위해서 / 패스워드를 이름으로 설정   ]
            memberDto.setMpassword( new BCryptPasswordEncoder().encode( mname ) );
            // -------------------- 테스트 ( oauth2 전화번호가 없다-사업자등록 하면 가능 ) -------------------- /
            // - 전화번호 난수
            Random random = new Random();
            int 앞 = random.nextInt(999 ); int 중간 = random.nextInt(9999 ); int 뒤 = random.nextInt(9999 );
            // 임의 전화번호[ oauth2 전화번호가 없다-사업자등록 하면 가능 . db null 피하기 위해 / 번화번호를 임의의로 설정 ]
            memberDto.setMphone( 앞+"-"+중간+"-"+뒤 ); // 추후에 수정페이지 로 이동시켜서 추가정보 입력하게 유도.
            // -------------------- ------------------------------------------------ -------------------- //
            memberEntityRepository.save( memberDto.toEntity() );
        }else{ //만약에 처음 접속이 아니면  기존 권한을 db에서 가져와서 넣어주기.
            memberDto.setMrole( memberEntityRepository.findByMemail( memail).getMrole() );
        }
        // 권한 추가
        memberDto.get권한목록().add( new SimpleGrantedAuthority( memberDto.getMrole() ) );

        return memberDto;
    }
    // ========================================================= 1.일반회원 ============================================== //
        // p. 687
        // 1. UserDetailsService 구현체
        // 2. 시큐리티 인증 처리 해주는 메소드 구현 [ loadUserByUsername ]
        // 3. loadUserByUsername 메소드는 무조건(꼭) UserDetails객체를 반환해야한다.
        // 4. UserDetails객체를 이용한 패스워드 검증과  사용자 권한을 확인 하는 동작(메소드)

    // @Autowired : 사용불가 ( 스프링 컨테이너에 등록 안된 빈(객체) 이므로 불가능 )
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 9. 시큐리티 사용시 인증정보[로그인상태] 호출
    @Transactional // 1. header.js [ axios ] //2. boardService [ write ]
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
        // 2. 인증결과 에 저장된 UserDetails 로 타입 반환
        UserDetails userDetails = (UserDetails)o; // UserDetails : 로그인 결과를 가지고 있는 객체
            // 로그인상태에 필요한 데이터 구성
            MemberEntity memberEntity = memberEntityRepository.findByMemail( userDetails.getUsername() ) ;
        // 3. UserDetails의 정보를 memberDto에 담아서 반환 [ 로그인된 회원의 아이디와 회원번호 반환 ]
        return MemberDto.builder().memail( memberEntity.getMemail() ).mno( memberEntity.getMno() ).build();
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
        if( memberEntity == null ){
            throw new UsernameNotFoundException("없는 아이디입니다");
        }
        // 2. 로딩[불러오기]된 사용자의 정보를 이용해서 패스워드를 검증 // 2-1 있는 아이디 이면

        // 2-1 권한 목록에 추가
        List<GrantedAuthority> 권한목록 = new ArrayList<>();
        권한목록.add( new SimpleGrantedAuthority( memberEntity.getMrole()) ) ;

        // 2-2 DTO 만들기
        MemberDto memberDto = MemberDto.builder()
                .memail( memberEntity.getMemail() )
                .mpassword( memberEntity.getMpassword())
                .mname( memberEntity.getMname() )
                .권한목록( 권한목록 )
                .build();
        return memberDto;
    }
    // ========================================================= ============ ============================================== //


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




            // oAuth2User :
            // Name: [3142747395],
            // Granted Authorities: [ [ROLE_USER, SCOPE_account_email, SCOPE_profile_nickname] ],
            // User Attributes:
            // [
                // {id=3142747395,
                // connected_at=2023-11-01T02:34:00Z,
                // properties={nickname=김현수},
                // kakao_account={
                    //profile_nickname_needs_agreement=false,
                    // profile={nickname=김현수},
                    // has_email=true,
                    // email_needs_agreement=false,
                    // is_email_valid=true,
                    // is_email_verified=true,
                    // email=itdanja@kakao.com}
                // }
            // ]




        // oAuth2User :
            // Name: [{id=Hq9vZhky2c775-RmPtIeB95Rz2dnBbYgKTJPAHSsvDQ, nickname=아이티단자, email=kgs2072@naver.com}],
            // Granted Authorities: [[ROLE_USER]],
            // User Attributes:
                // [
                 // {resultcode=00,
                // message=success,
                // response={id=Hq9vZhky2c775-RmPtIeB95Rz2dnBbYgKTJPAHSsvDQ, nickname=아이티단자, email=kgs2072@naver.com}
                 // }]


        // oAuth2User =
            // Name: [114044778334166488538],
            // Granted Authorities: [[ROLE_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]],
            // User Attributes:
            // [
                // {sub=114044778334166488538,
                // name=아이티단자,
                // given_name=단자,
                // family_name=아이티,
                // picture=https://lh3.googleusercontent.com/a/ACg8ocJnQK5h01N1X-1FmKKp9ltL_8Wf-cY5DOabXivgjPXdbYE=s96-c,
                // email=kgs2072@naver.com,
                // email_verified=true,
                // locale=ko}
            // ]

 */














