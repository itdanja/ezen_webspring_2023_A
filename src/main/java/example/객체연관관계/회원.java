package example.객체연관관계;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Setter@Getter@Builder @ToString
// @ToString : 객체 호출시 기본값으로 객체참조값  호출 // @ToString 사용시 객체참조값 아닌 객체참조값 의 필드 호출
public class 회원 {
    private int 회원번호;
    private String 회원아이디;
    private String 회원이름;
    // 리스트 : 1명 회원(객체)는 여러개의 게시물 가질수 있으니까.
    @Builder.Default // 빌더패턴 사용시 해당 필드는 기본값으로 사용.
    private List<게시물> 내가쓴글 = new ArrayList<>();
}
