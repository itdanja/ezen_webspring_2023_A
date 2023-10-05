package day04;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter@Setter@ToString
@Builder
public class TodoDto {
    private int tno;            // 번호
    private String tcontent;    //내용
    private boolean tstate;      // 상태
}
