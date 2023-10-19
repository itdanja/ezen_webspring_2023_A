package example.객체연관관계;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter  @Builder @ToString
public class 게시물 {
    private int 게시물번호;
    private String 게시물제목;
    private String 게시물내용;
    // 게시물은 1명의 회원 작성 하므로 리스트 아니다.
    @ToString.Exclude // 주로 참조객체에 @ToString 제외 권장
    private 회원 작성한회원; // 회원객체의 주소값 가지는 필드 [ 참조필드 FK ]
}
