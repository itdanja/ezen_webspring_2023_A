package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service//서비스(@Component포함)
public class MemberService implements UserDetailsService {
    // Controller -> Service -> Repository 요청
    // Controller <- Service <- Repository 응답
    @Autowired
    private MemberEntityRepository memberEntityRepository ;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String memail ) throws UsernameNotFoundException {
        // 1. UserDetailsService 인터페이스 구현
        // 2. loadUserByUsername() 메소드 : 아이디 검증
        // 패스워드 검증 [ 시큐리티 자동 ]
        MemberEntity entity = memberEntityRepository.findByMemail( memail );
        if( entity == null ){ throw new UsernameNotFoundException("해당 계정의회원이 없습니다."); }
        // 3. 검증후 세션에 저장할 DTO 반환
        MemberDto dto = entity.toDto();
        // Dto 권한(여러개) 넣어주기
        // 1. 권한목록 만들기
        Set<GrantedAuthority> 권한목록 = new HashSet<>();
        // 2. 권한객체 만들기 [ DB 존재하는 권한명( ROLE_!!!! )으로  ]
        // 권한 없을경우 : ROLE_ANONYMOUS  / 권한 있을경우 ROLE_권한명 : ROLE_admin , ROLE_user
        SimpleGrantedAuthority 권한명1 = new SimpleGrantedAuthority( "ROLE_"+entity.getMrole() );
        // 3. 만든 권한객체를 권한목록[컬렉션]에  추가
        권한목록.add( 권한명1 );
        // 4. UserDetails 에 권한목록 대입
        dto.set권한목록( 권한목록 );

        return dto; // UserDetails : 원본데이터의 검증할 계정 , 패스워드 포함

    }

    @Transactional
    public MemberDto getMember(  ){
        // 1. 시큐리티 인증[로그인] 된 UserDetails객체[세션]로 관리 했을때 [ Spring ]
        // SecurityContextHolder : 시큐리티 정보 저장소
        // SecurityContextHolder.getContext()  : 시큐리티 저장된 정보 호출
        // SecurityContextHolder.getContext().getAuthentication();  // 인증 전체 정보 호출
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 인증된 회원의 정보 호출
        if( o.equals("anonymousUser") ){ return null; }
        // [ Principal ]  // 인증  실패시 : anonymousUser   // 인증  성공시 : 회원정보[Dto]
        // 2. 인증된 객체내 회원정보[ Principal ] 타입 변환
        return (MemberDto)o; //  Object ---> dto
        /*
        // 2. 일반 세션으로 로그인 정보를 관리 했을때 [ JSP ]
        String memail = (String)request.getSession().getAttribute("login");
        if( memail != null ){
            MemberEntity entity = memberEntityRepository.findByMemail( memail );
            return entity.todto();
        }
        return null;
         */
    }



    // 1. [C] 회원가입
    @Transactional // 트랜잭션 : 여러개 SQL를 하나의 최소 단위 처리 [ 성공 , 실패  !! 함수내 일부 SQL만 성공x]
    public boolean postMember( MemberDto memberDto){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 인코더 : 특정 형식으로변경  // 디코더 : 원본으로 되돌리기
        // 입력받은[DTO] 패스워드 암호화 해서 다시 DTO에 저장하자.
        memberDto.setMpassword( passwordEncoder.encode( memberDto.getMpassword() ) );

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
            //logout();  // 로그아웃 함수 재사용
            return true;
        }
        return false;
    }


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
    // 6.
    public boolean logout() {
        request.getSession().setAttribute( "loginDto" , null );
        return true;
    }

    /*
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
















