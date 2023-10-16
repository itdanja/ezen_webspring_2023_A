package ezenweb.model.dto;

import ezenweb.model.entity.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class MemberDto {

    private int mno;            // 1.회원번호
    private String memail;      // 2.이메일[회원아이디 대체 ]
    private String mpassword;   // 3.비밀번호
    private String mname;       // 4.이름
    private String mphone;      // 5.연락처
    private String mrole;       // 6.회원등급( 일반회원=user , 관리자회원=admin )

    // dto --> entity 변환 함수
        // service 에서 dto정보 를 db테이블 매핑에 저장하기 위해서
    public MemberEntity toEntity(){
        return MemberEntity.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mpassword( this.mpassword )
                .mname( this.mname )
                .mphone( this.mphone )
                .mrole( this.mrole )
                .build();
    }
}
