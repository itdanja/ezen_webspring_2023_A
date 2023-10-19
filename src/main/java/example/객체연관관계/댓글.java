package example.객체연관관계;

import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter @ToString @Builder
public class 댓글 {
    private int 댓글번호;
    private String 댓글내용;
    private 게시물 댓글게시물; // 게시물 객체의 주소값 가지는 필드 [ 참조필드 FK ]
}
