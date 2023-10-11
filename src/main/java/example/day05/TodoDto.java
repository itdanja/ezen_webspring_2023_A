package example.day05;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter@Setter@ToString
@Builder
public class TodoDto {
    private int tno;            // 번호
    private String tcontent;    //내용
    private boolean tstate;      // 상태

    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .tno( this.tno )
                .tcontent( this.tcontent )
                .tstate( this.tstate )
                .build();
    }
}
