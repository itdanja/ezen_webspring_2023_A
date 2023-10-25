package ezenweb.model.repository;

import ezenweb.model.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository// 리포지토리( @Component포함 )
public interface MemberEntityRepository extends JpaRepository<MemberEntity , Integer >  {
    // 1. 해당 이메일 로 엔티티 찾기
    // 인수로 들어온 email과 동일한 엔티티[레코드] 찾아서 반환
    // sql :  select * from member where memail = ? ;
    MemberEntity findByMemail(String memail);
    // 2. 해당 이메일과 비밀번호가 일치한 엔티티 반환
    // 인수로 들어온 이메일 과 패스워드가 모두 일치한 엔티티[레코드] 찾아서 존재 여부 반환
    // sql : select * from member where memail = ? and mpassword = ? ;
    Optional<MemberEntity> findByMemailAndMpassword( String memail , String mpassword);
    // 3. [ 중복체크 활용 ] 만약에 동일한 이메일 이 존재하면 true , 아니면 false
    boolean existsByMemail(String memail);
    // 4. [로그인 활용 ] 만약에 동일한 이메일과 패스워드가 존재하면  true 아니면 false
    boolean existsByMemailAndMpassword( String memail , String mpassword);
    // 아이디찾기 [ 이름 과 전화번호 ]
    Optional<MemberEntity> findByMnameAndMphone( String mname , String mphone );
    // 비밀번호찾기 [ 아이디 와 전화번호 ]
    boolean existsByMemailAndMphone( String memail , String mphone );

    // * query 예시
    //@Query("select * from MemberEntity m where m.memail = ?1")
    //MemberEntity 아이디로엔티티찾기( String memail );
}
