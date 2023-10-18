package ezenweb.service;

import ezenweb.model.dto.MemberDto;
import ezenweb.model.entity.MemberEntity;
import ezenweb.model.repository.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service//서비스(@Component포함)
public class MemberService {
    // Controller -> Service -> Repository 요청
    // Controller <- Service <- Repository 응답
    @Autowired
    private MemberEntityRepository memberEntityRepository ;

    // 1. [C] 회원가입
    @Transactional // 트랜잭션 : 여러개 SQL를 하나의 최소 단위 처리 [ 성공 , 실패  !! 함수내 일부 SQL만 성공x]
    public boolean postMember( MemberDto memberDto){
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

    // 3. [U] 회원정보 수정
    @Transactional
    public boolean updateMember(  MemberDto memberDto ){
        // 1. 수정할 엔티티 찾기 [ mno ]
        Optional< MemberEntity > optionalMemberEntity =
            memberEntityRepository.findById( memberDto.getMno() );
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
        Optional<MemberEntity> optionalMemberEntity
                =memberEntityRepository.findById(mno);
        // 2. 만약에 삭제할 엔티티가 반환/검색 존재하면
        if( optionalMemberEntity.isPresent() ){
            memberEntityRepository.deleteById( mno ); // 3.엔티티 삭제
            // 4. 삭제 성공시
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
    // 6.
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
}
















