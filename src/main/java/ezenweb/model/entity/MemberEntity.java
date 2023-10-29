package ezenweb.model.entity;

import ezenweb.model.dto.MemberDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // 해당 클래스를 db테이블과 매핑 [ 엔티티클래스 <----> db테이블 ( 엔티티 객체 1개 <---> db테이블내 레코드 1개  ) ]
@Table( name = "member") // db테이블명 정의 [ 생략시 해당 클래스명이 db테이블 명으로 자동 생성 ]
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString @Builder @DynamicInsert
public class MemberEntity extends BaseTime {
    @Id // 해당 필드를 pk로 선정
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int mno;            // 1.회원번호
    @Column( name = "memail" , length = 50 , nullable = false , unique = true ) // 해당 필드 선정 [ 최대50글자 , not null , unique ]
    // name="필드명정의" , length=최대글자수 , nullable=false -> not null null불가 ,  unique = true 중복불가 ]
    private String memail;      // 2.이메일[회원아이디 대체 ]
    @Column( length = 255 , nullable = false ) // 해당 필드 선정 [ 최대30글자 , not null ]
    private String mpassword;   // 3.비밀번호
    @Column( length = 20 , nullable = false ) // 해당 필드 선정 [ 최대20글자 , not null ]
    private String mname;       // 4.이름
    @Column( length = 13 , nullable = false , unique = true ) // 해당 필드 선정 [ 최대13글자 , not null , unique ]
    private String mphone;      // 5.연락처
    @Column // 해당 필드 선정
    @ColumnDefault( "'USER'" ) // ColumnDefault("초기값") ColumnDefault("'문자열경우'")
    private String mrole;       // 6.회원등급( 일반회원=user , 관리자회원=admin )
    // [ BaseTime 클래스가 상속해주는 필드 : 1.회원가입일자 2.회원정보수정일 ]

    // 게시물목록 = 내가 쓴 게시물
    @Builder.Default // 빌더패턴 사용시 해당 필드를 값을 기본값으로 사용
    @OneToMany( mappedBy = "memberEntity" ) // 하나가 다수에게 [ PK ] // 실제 DB 영향 X
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    // entity --> dto 변환 함수
        // service 에서 entity 정보 를 controller 로 이동하기 위해
    public MemberDto toDto(){
        return MemberDto.builder()
                .mno(this.mno)
                .memail(this.memail)
                .mpassword(this.mpassword)
                .mname(this.mname)
                .mphone(this.mphone)
                .mrole(this.mrole)
                .cdate(this.getCdate() )
                .udate(this.getUdate() )
                .build();
    }
}

