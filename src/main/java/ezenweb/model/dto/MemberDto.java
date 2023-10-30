package ezenweb.model.dto;

import ezenweb.model.entity.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class MemberDto implements UserDetails, OAuth2User  {


    // ----------------------  UserDetails ---------------------
    @Override // 인증된 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.권한목록;
    }

    @Override // 패스워드 반환
    public String getPassword() {
        return this.mpassword;
    }

    @Override // 계정 반환
    public String getUsername() {
        return this.memail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { return true;   }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // --------------------------------- OAuth2User --------------------//ㅌ
    @Override // Oauth2 회원정보
    public Map<String, Object> getAttributes() {  return this.소셜회원정보; }

    @Override // Oauth2 아이디
    public String getName() { return this.memail; }

    private List<GrantedAuthority> 권한목록; //7.[ 인증용 ]
    private Map<String, Object> 소셜회원정보; // 8. oauth2 인증 회원정보


    private int mno;            // 1.회원번호
    private String memail;      // 2.이메일[회원아이디 대체 ]
    private String mpassword;   // 3.비밀번호
    private String mname;       // 4.이름
    private String mphone;      // 5.연락처
    private String mrole;       // 6.회원등급( 일반회원=user , 관리자회원=admin )

    // + baseTime
    private LocalDateTime cdate;
    private LocalDateTime udate;

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
